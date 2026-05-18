package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.Author;
import com.example.onlinebookstore.model.GuestAuthor;
import com.example.onlinebookstore.model.PermanentAuthor;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthorRepository {

    private final String filePath = "data/authors.txt";

    public AuthorRepository() {
        ensureFileExists();
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

    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                
                Author author;
                if (parts[0].equals("GUEST") || parts[0].equals("PERMANENT")) {
                    // New Format: TYPE|id|name|bio|imageUrl|avgRating|ratingCount
                    String type = parts[0];
                    String id = parts[1];
                    String name = parts[2];
                    String bio = parts[3].replace("\\n", "\n");
                    String imageUrl = parts[4];
                    double avgRating = Double.parseDouble(parts[5]);
                    int ratingCount = Integer.parseInt(parts[6]);

                    if ("GUEST".equals(type)) {
                        author = new GuestAuthor(id, name, bio, imageUrl, avgRating, ratingCount);
                    } else {
                        author = new PermanentAuthor(id, name, bio, imageUrl, avgRating, ratingCount);
                    }
                } else if (parts.length >= 5) {
                    // Migration / Legacy Formats
                    String id, name, bio, imageUrl;
                    double avgRating;
                    int ratingCount;

                    if (parts.length >= 6) {
                        id = parts[0];
                        name = parts[1];
                        bio = parts[2].replace("\\n", "\n");
                        imageUrl = parts[3];
                        avgRating = Double.parseDouble(parts[4]);
                        ratingCount = Integer.parseInt(parts[5]);
                    } else {
                        id = parts[0];
                        name = parts[1];
                        bio = parts[2].replace("\\n", "\n");
                        imageUrl = "";
                        avgRating = Double.parseDouble(parts[3]);
                        ratingCount = Integer.parseInt(parts[4]);
                    }
                    // Default legacy to Permanent
                    author = new PermanentAuthor(id, name, bio, imageUrl, avgRating, ratingCount);
                } else {
                    continue;
                }
                authors.add(author);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public Optional<Author> findById(String id) {
        return findAll().stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public Optional<Author> findByName(String name) {
        return findAll().stream().filter(a -> a.getName().equalsIgnoreCase(name)).findFirst();
    }

    public void save(Author author) {
        List<Author> authors = findAll();
        if (author.getId() == null || author.getId().isEmpty()) {
            author.setId(UUID.randomUUID().toString());
            authors.add(author);
        } else {
            authors.removeIf(a -> a.getId().equals(author.getId()));
            authors.add(author);
        }
        writeAll(authors);
    }

    public void delete(String id) {
        List<Author> authors = findAll();
        authors.removeIf(a -> a.getId().equals(id));
        writeAll(authors);
    }

    private void writeAll(List<Author> authors) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Author a : authors) {
                String type = a.getAuthorType().toUpperCase();
                writer.write(String.format("%s|%s|%s|%s|%s|%.2f|%d",
                        type,
                        a.getId(),
                        a.getName(),
                        a.getBio().replace("\n", "\\n"),
                        a.getImageUrl() != null ? a.getImageUrl() : "",
                        a.getAverageRating(),
                        a.getRatingCount()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
