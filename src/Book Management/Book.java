package com.example.onlinebookstore.model;

import java.io.Serializable;

/**
 * Abstract Base Class representing a Book in the bookstore.
 * Shared fields for all book types (E-Books and Printed Books).
 */
public abstract class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String author;
    private double price;
    private String description;
    private String language;
    private String genre; // Renamed from 'type' for clarity
    private String imageUrl;

    public Book() {}

    public Book(String id, String title, String author, double price, String description, String language, String genre, String imageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.language = language;
        this.genre = genre;
        this.imageUrl = imageUrl;
    }

    // --- Common Getters and Setters ---
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    /**
     * Polymorphic method to get specific details about the book format.
     */
    public abstract String getFormatDetails();

    /**
     * Get the simplified format name (e.g., "E-Book", "Printed").
     */
    public abstract String getFormatName();

    /**
     * Polymorphic method to get a concise summary for display in lists.
     */
    public abstract String getDisplayInfo();
}
