package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.EBook;
import com.example.onlinebookstore.model.PrintedBook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository class handling Polymorphic File-based Persistence for Books.
 * Handles both EBooks and PrintedBooks.
 */
@Repository
public class BookRepository {

    private final String filePath;

    public BookRepository(@Value("${data.file.path:data/books.txt}") String filePath) {
        this.filePath = filePath;
        ensureFileExists();
        migrateIfNecessary();
    }

    private void migrateIfNecessary() {
        List<Book> books = findAll();
        // findAll() logic already handles the legacy-to-object mapping.
        // writeAll() will commit the new prefixed format back to the file.
        writeAll(books);
    }

    private void ensureFileExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book book = parseBook(line);
                if (book != null) {
                    books.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public Optional<Book> findById(String id) {
        return findAll().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }

    public Book save(Book book) {
        List<Book> books = findAll();
        if (book.getId() == null || book.getId().isEmpty()) {
            book.setId(UUID.randomUUID().toString());
            books.add(book);
        } else {
            return update(book);
        }
        writeAll(books);
        return book;
    }

    public Book update(Book book) {
        if (book.getId() == null || book.getId().isEmpty()) {
            throw new IllegalArgumentException("Cannot update a book without an ID");
        }
        List<Book> books = findAll();
        boolean found = false;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(book.getId())) {
                books.set(i, book);
                found = true;
                break;
            }
        }
        if (!found) throw new RuntimeException("Book not found for update.");
        writeAll(books);
        return book;
    }

    public void deleteById(String id) {
        List<Book> books = findAll();
        books.removeIf(b -> b.getId().equals(id));
        writeAll(books);
    }

    private void writeAll(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : books) {
                writer.write(serializeBook(book));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =========================================================================
    // POLYMORPHIC HELPER METHODS
    // =========================================================================

    private String serializeBook(Book b) {
        StringBuilder sb = new StringBuilder();
        if (b instanceof EBook ebook) {
            sb.append("EBOOK|").append(b.getId()).append("|").append(b.getTitle()).append("|")
              .append(b.getAuthor()).append("|").append(b.getPrice()).append("|")
              .append(b.getDescription()).append("|").append(b.getLanguage()).append("|")
              .append(b.getGenre()).append("|").append(b.getImageUrl()).append("|")
              .append(ebook.getFileSizeMB()).append("|").append(ebook.getDownloadUrl());
        } else if (b instanceof PrintedBook printed) {
            sb.append("PRINTED|").append(b.getId()).append("|").append(b.getTitle()).append("|")
              .append(b.getAuthor()).append("|").append(b.getPrice()).append("|")
              .append(b.getDescription()).append("|").append(b.getLanguage()).append("|")
              .append(b.getGenre()).append("|").append(b.getImageUrl()).append("|")
              .append(printed.getWeightKG()).append("|").append(printed.getStockQuantity());
        }
        return sb.toString();
    }

    private Book parseBook(String line) {
        String[] parts = line.split("\\|");
        
        // Handle Migration (Old format had no prefix)
        if (!parts[0].equals("EBOOK") && !parts[0].equals("PRINTED")) {
            if (parts.length >= 8) {
                // Legacy: id|title|author|price|desc|lang|genre|url
                return new PrintedBook(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4], parts[5], parts[6], parts[7], 0.5, 10);
            }
            return null;
        }

        try {
            String id = parts[1];
            String title = parts[2];
            String author = parts[3];
            double price = Double.parseDouble(parts[4]);
            String desc = parts[5];
            String lang = parts[6];
            String genre = parts[7];
            String url = parts[8];

            if (parts[0].equals("EBOOK")) {
                double size = parts.length > 9 ? Double.parseDouble(parts[9]) : 0.0;
                String download = parts.length > 10 ? parts[10] : "N/A";
                return new EBook(id, title, author, price, desc, lang, genre, url, size, download);
            } else {
                double weight = parts.length > 9 ? Double.parseDouble(parts[9]) : 0.5;
                int stock = parts.length > 10 ? Integer.parseInt(parts[10]) : 0;
                return new PrintedBook(id, title, author, price, desc, lang, genre, url, weight, stock);
            }
        } catch (Exception e) {
            return null;
        }
    }
}
