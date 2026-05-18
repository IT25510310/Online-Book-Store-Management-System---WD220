package com.example.onlinebookstore.model;

import java.time.LocalDateTime;

/**
 * Concrete subclass representing a review from the general public.
 */
public class PublicReview extends Review {

    public PublicReview() {
        super();
    }

    public PublicReview(String id, String bookId, String userId, String userName, int rating, String comment, LocalDateTime date) {
        super(id, bookId, userId, userName, rating, comment, date);
    }

    @Override
    public String displayReview(boolean isAdmin) {
        if (isAdmin) {
            return "[PUBLIC REVIEW] [" + getId() + "] By: " + getUserName();
        }
        return getUserName();
    }

    @Override
    public String getReviewType() {
        return "Public";
    }

    @Override
    public String toString() {
        return "PublicReview: " + getUserName() + " (" + getRating() + " stars)";
    }
}
