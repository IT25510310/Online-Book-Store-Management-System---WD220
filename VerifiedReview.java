package com.example.onlinebookstore.model;

import java.time.LocalDateTime;

/**
 * Concrete subclass representing a review from a verified purchaser.
 */
public class VerifiedReview extends Review {

    public VerifiedReview() {
        super();
    }

    public VerifiedReview(String id, String bookId, String userId, String userName, int rating, String comment, LocalDateTime date) {
        super(id, bookId, userId, userName, rating, comment, date);
    }

    @Override
    public String displayReview(boolean isAdmin) {
        if (isAdmin) {
            return "[VERIFIED PURCHASE] [" + getId() + "] By: " + getUserName();
        }
        return getUserName() + " (Verified Purchase)";
    }

    @Override
    public String getReviewType() {
        return "Verified";
    }

    @Override
    public String toString() {
        return "Verified Purchase Review: " + getUserName() + " (" + getRating() + " stars)";
    }
}
