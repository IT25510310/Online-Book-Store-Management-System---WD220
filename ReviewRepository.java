package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.PublicReview;
import com.example.onlinebookstore.model.Review;
import com.example.onlinebookstore.model.VerifiedReview;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ReviewRepository {

    private final String filePath = "data/reviews.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ReviewRepository() {
        ensureFileExists();
    }

    private void ensureFileExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                
                Review review;
                // Handle Migration/Format Detection
                if (parts[0].equals("PUBLIC") || parts[0].equals("VERIFIED")) {
                    // New Format: TYPE|id|bookId|userId|userName|rating|comment|date
                    String type = parts[0];
                    String id = parts[1];
                    String bookId = parts[2];
                    String userId = parts[3];
                    String userName = parts[4];
                    int rating = Integer.parseInt(parts[5]);
                    String comment = parts[6];
                    LocalDateTime date = LocalDateTime.parse(parts[7], formatter);

                    if ("VERIFIED".equals(type)) {
                        review = new VerifiedReview(id, bookId, userId, userName, rating, comment, date);
                    } else {
                        review = new PublicReview(id, bookId, userId, userName, rating, comment, date);
                    }
                } else if (parts.length >= 7) {
                    // Legacy Format: id|bookId|userId|userName|rating|comment|date
                    review = new PublicReview(
                        parts[0], parts[1], parts[2], parts[3],
                        Integer.parseInt(parts[4]), parts[5],
                        LocalDateTime.parse(parts[6], formatter)
                    );
                } else {
                    continue;
                }
                reviews.add(review);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void save(Review review) {
        List<Review> reviews = findAll();
        if (review.getId() == null || review.getId().isEmpty()) {
            review.setId(UUID.randomUUID().toString());
            reviews.add(review);
        } else {
            boolean found = false;
            for (int i = 0; i < reviews.size(); i++) {
                if (reviews.get(i).getId().equals(review.getId())) {
                    reviews.set(i, review);
                    found = true;
                    break;
                }
            }
            if (!found) {
                reviews.add(review);
            }
        }
        writeAll(reviews);
    }

    public void delete(String id) {
        List<Review> reviews = findAll();
        reviews.removeIf(r -> r.getId().equals(id));
        writeAll(reviews);
    }

    private void writeAll(List<Review> reviews) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Review r : reviews) {
                String type = r.getReviewType().toUpperCase();
                writer.write(String.format("%s|%s|%s|%s|%s|%d|%s|%s",
                        type,
                        r.getId(),
                        r.getBookId(),
                        r.getUserId(),
                        r.getUserName(),
                        r.getRating(),
                        r.getComment(),
                        r.getDate().format(formatter)));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
