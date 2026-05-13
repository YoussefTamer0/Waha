package com.bookstore.waha.Controller;

import com.bookstore.waha.Model.Author;
import com.bookstore.waha.Model.Book;
import com.bookstore.waha.Model.Order;
import com.bookstore.waha.Model.Publisher;
import com.bookstore.waha.Service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {

    private final AdminService adminservice;
    private final BookService bookService;
    private final OrderService orderService;
    private final AuthorService authorService;
    private final publishersService publishersService;

    public AdminController(AdminService adminservice, BookService bookService, OrderService orderService, AuthorService authorService, publishersService publishersService) {
        this.adminservice = adminservice;
        this.orderService = orderService;
        this.bookService = bookService;
        this.authorService = authorService;
        this.publishersService = publishersService;
    }

    @GetMapping("/admin/managebooks")
    public String manageBooks(Model model) {
        Book book = new Book();
        book.setAuthor(new Author());
        book.setPublisher(new Publisher());
        model.addAttribute("book", book);
        model.addAttribute("author", new Author());
        model.addAttribute("publisher", new Publisher());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("publishers", publishersService.getAllPublisher());
        return "admin/managebooks";
    }

    @PostMapping("/admin/authors/add")
    public String addAuthor(@ModelAttribute("author") Author author, RedirectAttributes redirectAttributes) {
        authorService.addAuthor(author);
        redirectAttributes.addFlashAttribute("message", "Author added successfully!");
        return "redirect:/admin/managebooks";
    }

    @PostMapping("/admin/publishers/add")
    public String addPublisher(@ModelAttribute("publisher") Publisher publisher, RedirectAttributes redirectAttributes) {
        publishersService.addPublisher(publisher);
        redirectAttributes.addFlashAttribute("message", "Publisher added successfully!");
        return "redirect:/admin/managebooks";
    }

    @PostMapping("/admin/books/add")
    public String insertBook(@Valid @ModelAttribute("book") Book book,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            book.setAuthor(book.getAuthor() != null ? book.getAuthor() : new Author());
            book.setPublisher(book.getPublisher() != null ? book.getPublisher() : new Publisher());
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("message", "Error occurred.");
            return "admin/managebooks";
        }
        adminservice.addBook(book);
        redirectAttributes.addFlashAttribute("message", "Book added successfully!");
        return "redirect:/admin/managebooks";
    }

    @PostMapping("/admin/books/delete")
    public String removeBook(@RequestParam("bookID") Long bookID,
                             RedirectAttributes redirectAttributes) {
        try {
            Book book = adminservice.findBookByID(bookID);
            if (book != null) {
                adminservice.deleteBook(book);
                redirectAttributes.addFlashAttribute("message", "Book deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Error: Book ID not found.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "An error occurred while trying to delete the book.");
        }

        return "redirect:/admin/managebooks";
    }

    @PostMapping("/admin/orders/delete")
    public String deleteOrder(@RequestParam(required = false) Long orderID,
                              RedirectAttributes redirectAttributes) {
        try {
            if (orderID == null) {
                redirectAttributes.addFlashAttribute("error", "Order ID is required");
                return "redirect:/admin/manageorders";
            }
            adminservice.clearOrder(orderID);
            redirectAttributes.addFlashAttribute("message", "Order #" + orderID + " deleted successfully");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete order: " + e.getMessage());
        }
        return "redirect:/admin/manageorders";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalBooks", bookService.countBooks());
        model.addAttribute("totalOrders", orderService.countOrders());
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());
        return "admin/dashboard";
    }

    @GetMapping("/admin/manageorders")
    public String manageOrders(@RequestParam(required = false) String keyword, Model model) {
        List<Order> orders;
        if (keyword != null && !keyword.trim().isEmpty()) {
            orders = orderService.searchOrders(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            orders = orderService.getAllOrders();
        }
        model.addAttribute("orders", orders);
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());
        return "admin/ManageOrders";
    }
}