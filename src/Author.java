package com.example.onlinebookstore.model;

import java.io.Serializable;

/**
 * Abstract Base Class representing an Author.
 */
public abstract class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String bio;
    private String imageUrl;
    private double averageRating;
    private int ratingCount;

    public Author() {}

    public Author(String id, String name, String bio, String imageUrl, double averageRating, int ratingCount) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.imageUrl = imageUrl;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { 
        if (averageRating < 0) this.averageRating = 0;
        else if (averageRating > 5) this.averageRating = 5;
        else this.averageRating = averageRating; 
    }

    public int getRatingCount() { return ratingCount; }
    public void setRatingCount(int ratingCount) { 
        if (ratingCount < 0) this.ratingCount = 0;
        else this.ratingCount = ratingCount; 
    }

    public void addRating(int rating) {
        double totalRating = averageRating * ratingCount;
        ratingCount++;
        averageRating = (totalRating + rating) / ratingCount;
    }

    /**
     * Get the descriptive author type.
     */
    public abstract String getAuthorType();

    /**
     * Polymorphic method to get a formatted display string for the author.
     * @param isAdmin Whether the viewer has administrative privileges.
     */
    public abstract String displayProfile(boolean isAdmin);
}
