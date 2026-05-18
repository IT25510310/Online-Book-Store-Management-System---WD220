package com.example.onlinebookstore.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Model class representing a Book Review and Rating.
 */
public abstract class Review implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String bookId;
    private String userId;
    private String userName;
    private int rating; // 1-5
    private String comment;
    private LocalDateTime date;

    public Review() {}

    public Review(String id, String bookId, String userId, String userName, int rating, String comment, LocalDateTime date) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public int getRating() { return rating; }
    public void setRating(int rating) { 
        if (rating < 1) this.rating = 1;
        else if (rating > 5) this.rating = 5;
        else this.rating = rating; 
    }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    /**
     * Polymorphic method to get a formatted display string for the review.
     * @param isAdmin Whether the viewer has administrative privileges.
     */
    public abstract String displayReview(boolean isAdmin);

    /**
     * Get the descriptive review type.
     */
    public abstract String getReviewType();
}
