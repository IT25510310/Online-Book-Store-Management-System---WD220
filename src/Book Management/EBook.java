package com.example.onlinebookstore.model;

/**
 * Concrete subclass representing a Digital E-Book.
 */
public class EBook extends Book {
    private double fileSizeMB;
    private String downloadUrl;

    public EBook() {
        super();
    }

    public EBook(String id, String title, String author, double price, String description, String language, String genre, String imageUrl, double fileSizeMB, String downloadUrl) {
        super(id, title, author, price, description, language, genre, imageUrl);
        this.fileSizeMB = fileSizeMB;
        this.downloadUrl = downloadUrl;
    }

    public double getFileSizeMB() { return fileSizeMB; }
    public void setFileSizeMB(double fileSizeMB) { this.fileSizeMB = fileSizeMB; }

    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }

    @Override
    public String getFormatDetails() {
        return "Digital E-Book (" + fileSizeMB + " MB)";
    }

    @Override
    public String getFormatName() {
        return "E-Book";
    }

    @Override
    public String getDisplayInfo() {
        return "Digital E-Book (" + fileSizeMB + " MB)";
    }
}
