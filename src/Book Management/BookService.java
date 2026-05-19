package com.example.onlinebookstore.service;

import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class acting as a bridge between the Controller and the Repository.
 * Encapsulates business logic for book management.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // =========================================================================
    // CRUD BUSINESS LOGIC
    // =========================================================================

    /**
     * READ: Business logic to fetch all available books.
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * READ: Business logic to search for books by title or author.
     */
    public List<Book> searchBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllBooks();
        }
        String lowerQuery = query.toLowerCase().trim();
        return bookRepository.findAll().stream()
                .filter(b -> (b.getTitle() != null && b.getTitle().toLowerCase().contains(lowerQuery)) || 
                             (b.getAuthor() != null && b.getAuthor().toLowerCase().contains(lowerQuery)) ||
                             (b.getGenre() != null && b.getGenre().toLowerCase().contains(lowerQuery)))
                .toList();
    }

    /**
     * READ: Business logic to find a specific book by ID.
     */
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    /**
     * CREATE / UPDATE: Business logic to save or modify a book.
     */
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * UPDATE: Business logic to modify an existing book.
     */
    public Book updateBook(Book book) {
        return bookRepository.update(book);
    }

    /**
     * DELETE: Business logic to remove a book.
     */
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
