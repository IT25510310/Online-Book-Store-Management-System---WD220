package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.EBook;
import com.example.onlinebookstore.model.PrintedBook;
import com.example.onlinebookstore.model.Review;
import com.example.onlinebookstore.model.PublicReview;
import com.example.onlinebookstore.model.VerifiedReview;
import com.example.onlinebookstore.service.AdminService;
import com.example.onlinebookstore.service.BookService;
import com.example.onlinebookstore.service.ReviewService;
import com.example.onlinebookstore.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Controller handling all Book-related web requests.
 * Manages the UI for listing, viewing, and managing books.
 */
@Controller
public class BookController {

    private final BookService bookService;
    private final AdminService adminService;
    private final ReviewService reviewService;
    private final OrderService orderService;

    public BookController(BookService bookService, AdminService adminService, ReviewService reviewService, OrderService orderService) {
        this.bookService = bookService;
        this.adminService = adminService;
        this.reviewService = reviewService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/books")
    public String listBooks(@RequestParam(required = false) String lang, 
                            @RequestParam(required = false) String type, 
                            @RequestParam(required = false) String search, 
                            Model model) {
        List<Book> books;
        
        if (search != null && !search.trim().isEmpty()) {
            books = bookService.searchBooks(search);
            model.addAttribute("searchQuery", search);
        } else {
            books = bookService.getAllBooks();
        }

        if (lang != null && !lang.isEmpty()) {
            books = books.stream()
                    .filter(b -> b.getLanguage().equalsIgnoreCase(lang))
                    .toList();
            model.addAttribute("currentLanguage", lang);
        }
        if (type != null && !type.isEmpty()) {
            books = books.stream()
                    .filter(b -> b.getGenre().equalsIgnoreCase(type))
                    .toList();
            model.addAttribute("currentType", type);
        }

        // Calculate Average Ratings for each book
        java.util.Map<String, Double> ratings = new java.util.HashMap<>();
        for (Book book : books) {
            ratings.put(book.getId(), reviewService.getAverageRating(book.getId()));
        }

        model.addAttribute("books", books);
        model.addAttribute("bookRatings", ratings);
        return "book-list";
    }

    @GetMapping("/books/{id}")
    public String showDetails(@PathVariable String id, Model model) {
        bookService.getBookById(id).ifPresent(book -> {
            model.addAttribute("book", book);
            model.addAttribute("reviews", reviewService.getReviewsByBook(id));
            model.addAttribute("avgRating", reviewService.getAverageRating(id));
        });
        return "book-details";
    }

    @PostMapping("/books/review/submit")
    public String submitReview(@RequestParam String bookId,
                               @RequestParam(required = false) String reviewId,
                               @RequestParam int rating,
                               @RequestParam String comment,
                               HttpSession session) {
        String username = (String) session.getAttribute("username");
        String userId = (String) session.getAttribute("userId");
        String fullName = (String) session.getAttribute("fullName");

        if (username == null || !"USER".equals(session.getAttribute("userRole"))) {
            return "redirect:/login/user?returnTo=books/" + bookId;
        }

        Review review;
        if (reviewId != null && !reviewId.isEmpty()) {
            // Edit existing review
            review = reviewService.getReviewById(reviewId).orElse(null);
            if (review != null && review.getUserId().equals(userId)) {
                review.setRating(rating);
                review.setComment(comment);
                review.setDate(LocalDateTime.now());
            } else {
                return "redirect:/books/" + bookId;
            }
        } else {
            // Create new review - Check if verified
            boolean isVerified = false;
            if (userId != null) {
                String bookTitle = bookService.getBookById(bookId).map(Book::getTitle).orElse("");
                isVerified = orderService.getOrdersByUser(userId).stream()
                        .anyMatch(o -> o.getItemsSummary().contains(bookTitle));
            }

            if (isVerified) {
                review = new VerifiedReview(null, bookId, userId, fullName, rating, comment, LocalDateTime.now());
            } else {
                review = new PublicReview(null, bookId, userId, fullName, rating, comment, LocalDateTime.now());
            }
        }
        reviewService.addReview(review);
        
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/books/review/delete/{id}")
    public String deleteReview(@PathVariable String id, @RequestParam String bookId, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String role = (String) session.getAttribute("userRole");

        reviewService.getReviewById(id).ifPresent(review -> {
            // Only the author or an admin can delete a review
            if (review.getUserId().equals(userId) || "SUPER_ADMIN".equals(role)) {
                reviewService.deleteReview(id);
            }
        });
        
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/admin/books")
    public String viewInventory(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (!"SUPER_ADMIN".equals(role) && !"MODERATOR".equals(role)) {
            return "redirect:/login-choice";
        }
        model.addAttribute("books", bookService.getAllBooks());
        return "book-inventory";
    }

    @GetMapping("/books/new")
    public String showAddForm(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (!"SUPER_ADMIN".equals(role) && !"MODERATOR".equals(role)) {
            return "redirect:/login-choice";
        }
        return "book-form";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable String id, HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (!"SUPER_ADMIN".equals(role) && !"MODERATOR".equals(role)) {
            return "redirect:/login-choice";
        }
        bookService.getBookById(id).ifPresent(book -> {
            model.addAttribute("book", book);
            model.addAttribute("format", book instanceof EBook ? "EBOOK" : "PRINTED");
        });
        return "book-form";
    }

    @PostMapping("/books/save")
    public String saveBook(@RequestParam(required = false) String id,
                           @RequestParam String title,
                           @RequestParam String author,
                           @RequestParam double price,
                           @RequestParam String description,
                           @RequestParam String language,
                           @RequestParam String genre,
                           @RequestParam String imageUrl,
                           @RequestParam String format,
                           @RequestParam(defaultValue = "0") double fileSizeMB,
                           @RequestParam(required = false) String downloadUrl,
                           @RequestParam(defaultValue = "0") double weightKG,
                           @RequestParam(defaultValue = "0") int stockQuantity,
                           HttpSession session) {
        
        String role = (String) session.getAttribute("userRole");
        if (!"SUPER_ADMIN".equals(role) && !"MODERATOR".equals(role)) {
            return "redirect:/login-choice";
        }
        
        Book book;
        if ("EBOOK".equalsIgnoreCase(format)) {
            book = new EBook(id, title, author, price, description, language, genre, imageUrl, fileSizeMB, downloadUrl);
        } else {
            book = new PrintedBook(id, title, author, price, description, language, genre, imageUrl, weightKG, stockQuantity);
        }
        
        boolean isNew = id == null || id.isEmpty();
        if (isNew) {
            bookService.saveBook(book);
        } else {
            bookService.updateBook(book);
        }
        
        adminService.logActivity((String) session.getAttribute("username"), (isNew ? "Created " : "Updated ") + "book: " + title);
        return "redirect:/admin/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable String id, HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (!"SUPER_ADMIN".equals(role)) {
            return "redirect:/login-choice";
        }
        bookService.getBookById(id).ifPresent(book -> {
            bookService.deleteBook(id);
            adminService.logActivity((String) session.getAttribute("username"), "Deleted book: " + book.getTitle());
        });
        return "redirect:/admin/books";
    }
}
