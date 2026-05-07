package com.bookstore.waha.controller;
import com.bookstore.waha.model.Book;
import com.bookstore.waha.model.CartItem;
import com.bookstore.waha.repository.BookRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final BookRepository bookRepository;

    public CartController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Integer bookID,
                            @RequestParam int quantity,
                            HttpSession session) {

        Book book = bookRepository.findById(bookID)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cartItems");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        cart.add(new CartItem(book, quantity));

        session.setAttribute("cartItems", cart);

        return "redirect:/cart/view";
    }


    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cartItems");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        model.addAttribute("cartItems", cart);

        return "cart";
    }


    @GetMapping("/clear")
    public String clearCart(HttpSession session) {

        session.removeAttribute("cartItems");

        return "redirect:/";
    }
}