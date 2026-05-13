package com.bookstore.waha.Controller;

import com.bookstore.waha.Model.Book;
import com.bookstore.waha.Model.Inventory;
import com.bookstore.waha.Service.BookService;
import com.bookstore.waha.Service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class ResultsController {
    private final BookService bookService;
    private final InventoryService inventoryService;

    public ResultsController(BookService bookService, InventoryService inventoryService) {
        this.bookService = bookService;
        this.inventoryService = inventoryService;
    }

    @GetMapping("/books")
    public String bookList(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/BookList";
    }

    @GetMapping("/books/search")
    public String getResults(@RequestParam(required = false) String query,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             Model model) {


        if (query == null || query.trim().isEmpty()) {
            model.addAttribute("books", Collections.emptyList());
            model.addAttribute("query", "");
            return "books/search";
        }


        String searchQuery = query.trim();
        Page<Book> bookPage = bookService.searchBooks(searchQuery, PageRequest.of(page, size));

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("query", searchQuery);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("totalResults", bookPage.getTotalElements());

        return "books/search";
    }
    @GetMapping("/books/{id}")
    public String getBookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        Inventory inventory = inventoryService.getInventoryByBookId(id);
        model.addAttribute("inventory", inventory);
        return "books/BookDetails";
    }
}