package com.example.onlinebookstore.service;

import com.example.onlinebookstore.model.Author;
import com.example.onlinebookstore.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> searchAuthors(String query) {
        return authorRepository.findAll().stream()
                .filter(a -> a.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Optional<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    public Optional<Author> getAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    public void rateAuthor(String id, int rating) {
        authorRepository.findById(id).ifPresent(author -> {
            author.addRating(rating);
            authorRepository.save(author);
        });
    }

    public void deleteAuthor(String id) {
        authorRepository.delete(id);
    }
}
