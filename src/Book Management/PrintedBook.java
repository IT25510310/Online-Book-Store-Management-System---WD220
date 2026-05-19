package com.example.onlinebookstore.model;

/**
 * Concrete subclass representing a Physical Printed Book.
 */
public class PrintedBook extends Book {
    private double weightKG;
    private int stockQuantity;

    public PrintedBook() {
        super();
    }

    public PrintedBook(String id, String title, String author, double price, String description, String language, String genre, String imageUrl, double weightKG, int stockQuantity) {
        super(id, title, author, price, description, language, genre, imageUrl);
        this.weightKG = weightKG;
        this.stockQuantity = stockQuantity;
    }

    public double getWeightKG() { return weightKG; }
    public void setWeightKG(double weightKG) { this.weightKG = weightKG; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    @Override
    public String getFormatDetails() {
        return "Physical Printed Book (" + stockQuantity + " in stock, " + weightKG + " KG)";
    }

    @Override
    public String getFormatName() {
        return "Printed";
    }

    @Override
    public String getDisplayInfo() {
        return "Physical Copy (" + stockQuantity + " in stock)";
    }
}
