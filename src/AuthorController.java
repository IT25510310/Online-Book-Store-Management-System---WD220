package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.model.Author;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.GuestAuthor;
import com.example.onlinebookstore.model.PermanentAuthor;
import com.example.onlinebookstore.service.AdminService;
import com.example.onlinebookstore.service.AuthorService;
import com.example.onlinebookstore.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final AdminService adminService;

    public AuthorController(AuthorService authorService, BookService bookService, AdminService adminService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.adminService = adminService;
    }

    @GetMapping
    public String listAuthors(@RequestParam(required = false) String search, Model model) {
        List<Author> authors;
        if (search != null && !search.trim().isEmpty()) {
            authors = authorService.searchAuthors(search);
            model.addAttribute("searchQuery", search);
        } else {
            authors = authorService.getAllAuthors();
        }
        model.addAttribute("authors", authors);
        return "author-list";
    }

    @GetMapping("/admin")
    public String viewAuthorInventory(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (!"SUPER_ADMIN".equals(role) && !"MODERATOR".equals(role)) {
            return "redirect:/login-choice";
        }
        model.addAttribute("authors", authorService.getAllAuthors());
        return "author-inventory";
    }

    @GetMapping("/{id}")
    public String showAuthorDetails(@PathVariable String id, Model model) {
        authorService.getAuthorById(id).ifPresent(author -> {
            model.addAttribute("author", author);
            // List books by author name
            List<Book> authorBooks = bookService.getAllBooks().stream()
                    .filter(b -> b.getAuthor().equalsIgnoreCase(author.getName()))
                    .toList();
            model.addAttribute("books", authorBooks);
        });
        return "author-details";
    }

    @PostMapping("/{id}/rate")
    public String rateAuthor(@PathVariable String id, @RequestParam int rating) {
        authorService.rateAuthor(id, rating);
        return "redirect:/authors/" + id;
    }

    @GetMapping("/new")
    public String showAddAuthorForm(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (!"SUPER_ADMIN".equals(role) && !"MODERATOR".equals(role)) {
            return "redirect:/login-choice";
        }
        return "author-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditAuthorForm(@PathVariable String id, HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (!"SUPER_ADMIN".equals(role) && !"MODERATOR".equals(role)) {
            return "redirect:/login-choice";
        }
        authorService.getAuthorById(id).ifPresent(author -> model.addAttribute("author", author));
        return "author-form";
    }

    @PostMapping("/save")
    public String saveAuthor(@RequestParam(required = false) String id,
                             @RequestParam String name,
                             @RequestParam String bio,
                             @RequestParam String imageUrl,
                             @RequestParam(defaultValue = "PERMANENT") String type,
                             HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (!"SUPER_ADMIN".equals(role) && !"MODERATOR".equals(role)) {
            return "redirect:/login-choice";
        }

        Author existing = (id != null && !id.isEmpty()) ? authorService.getAuthorById(id).orElse(null) : null;
        Author author;
        
        if ("GUEST".equalsIgnoreCase(type)) {
            author = new GuestAuthor();
        } else {
            author = new PermanentAuthor();
        }

        author.setId(id);
        author.setName(name);
        author.setBio(bio);
        author.setImageUrl(imageUrl);
        
        if (existing != null) {
            author.setAverageRating(existing.getAverageRating());
            author.setRatingCount(existing.getRatingCount());
        }

        authorService.saveAuthor(author);

        adminService.logActivity((String) session.getAttribute("username"), 
                (id == null || id.isEmpty() ? "Added" : "Updated") + " author: " + name);
        
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable String id, HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (!"SUPER_ADMIN".equals(role)) {
            return "redirect:/login-choice";
        }

        authorService.getAuthorById(id).ifPresent(author -> {
            authorService.deleteAuthor(id);
            adminService.logActivity((String) session.getAttribute("username"), "Deleted author: " + author.getName());
        });

        return "redirect:/authors";
    }
}
