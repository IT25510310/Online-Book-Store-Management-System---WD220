package com.example.onlinebookstore.service;

import com.example.onlinebookstore.model.Review;
import com.example.onlinebookstore.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void addReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getReviewsByBook(String bookId) {
        return reviewRepository.findAll().stream()
                .filter(r -> r.getBookId().equals(bookId))
                .sorted(Comparator.comparing(Review::getDate).reversed())
                .collect(Collectors.toList());
    }

    public java.util.Optional<Review> getReviewById(String id) {
        return reviewRepository.findAll().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    public void deleteReview(String id) {
        reviewRepository.delete(id);
    }

    public double getAverageRating(String bookId) {
        List<Review> reviews = getReviewsByBook(bookId);
        if (reviews.isEmpty()) return 0.0;
        
        double sum = reviews.stream().mapToInt(Review::getRating).sum();
        return sum / reviews.size();
    }
}
