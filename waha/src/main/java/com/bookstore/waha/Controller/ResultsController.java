package com.bookstore.waha.controller;

import com.bookstore.waha.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class ResultsController {
    private final BookService bookService;

    public ResultsController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/search")
    public String getResults(@RequestParam(required = false) String query,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             Model model) {


        if (query == null || query.trim().isEmpty()) {
            model.addAttribute("books", Collections.emptyList());
            model.addAttribute("query", "");
            return "/books/search";
        }


        String searchQuery = query.trim();
        Page<Book> bookPage = bookService.searchBooks(searchQuery, PageRequest.of(page, size));

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("query", searchQuery);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("totalResults", bookPage.getTotalElements());

        return "/books/search";
    }
}
