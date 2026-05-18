package com.example.onlinebookstore.model;

/**
 * Concrete subclass representing a Permanent Author.
 * Regular authors with multiple books and a long-standing relationship with the store.
 */
public class PermanentAuthor extends Author {

    public PermanentAuthor() {
        super();
    }

    public PermanentAuthor(String id, String name, String bio, String imageUrl, double averageRating, int ratingCount) {
        super(id, name, bio, imageUrl, averageRating, ratingCount);
    }

    @Override
    public String getAuthorType() {
        return "Permanent";
    }

    @Override
    public String displayProfile(boolean isAdmin) {
        if (isAdmin) {
            return "[PERMANENT] [" + getId() + "] " + getName();
        }
        return getName(); // Standard display for permanent authors
    }
}
