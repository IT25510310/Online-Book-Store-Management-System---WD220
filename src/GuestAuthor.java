package com.example.onlinebookstore.model;

/**
 * Concrete subclass representing a Guest Author.
 * Usually authors who have contributed a single book or are visiting contributors.
 */
public class GuestAuthor extends Author {

    public GuestAuthor() {
        super();
    }

    public GuestAuthor(String id, String name, String bio, String imageUrl, double averageRating, int ratingCount) {
        super(id, name, bio, imageUrl, averageRating, ratingCount);
    }

    @Override
    public String getAuthorType() {
        return "Guest";
    }

    @Override
    public String displayProfile(boolean isAdmin) {
        if (isAdmin) {
            return "[GUEST] [" + getId() + "] " + getName();
        }
        return getName() + " (Guest Contributor)";
    }
}
