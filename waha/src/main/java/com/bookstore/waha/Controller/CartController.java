package com.bookstore.waha.Controller;

import com.bookstore.waha.Model.Book;
import com.bookstore.waha.Model.CartItem;
import com.bookstore.waha.Repository.BookRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final BookRepository bookRepository;

    public CartController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/Add")
    public String addToCart(@RequestParam Long bookID,
                            @RequestParam int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        if (quantity < 1) {
            redirectAttributes.addFlashAttribute("error", "Quantity must be at least 1");
            return "redirect:/cart/view";
        }

        Optional<Book> bookOptional = bookRepository.findById(bookID);
        if (bookOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Book not found");
            return "redirect:/cart/view";
        }

        Book book = bookOptional.get();

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cartItems");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        for (CartItem item : cart) {
            if (item.getBook().getBookID().equals(bookID)) {
                item.setQuantity(item.getQuantity() + quantity);
                session.setAttribute("cartItems", cart);
                redirectAttributes.addFlashAttribute("success", "Added to cart");
                return "redirect:/cart/view";
            }
        }

        cart.add(new CartItem(book, quantity));
        session.setAttribute("cartItems", cart);
        redirectAttributes.addFlashAttribute("success", "Added to cart");

        return "redirect:/cart/view";
    }

    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cartItems");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        model.addAttribute("cartItems", cart);

        return "orders/cart";
    }

    @GetMapping("/Clear")
    public String clearCart(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("cartItems");
        redirectAttributes.addFlashAttribute("success", "Cart cleared");
        return "redirect:/home";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam Long bookId,
                             @RequestParam int quantity,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cartItems");

        if (cart != null) {
            cart.removeIf(item -> item.getBook().getBookID().equals(bookId) && quantity <= 0);
            for (CartItem item : cart) {
                if (item.getBook().getBookID().equals(bookId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
            session.setAttribute("cartItems", cart);
        }

        redirectAttributes.addFlashAttribute("success", "Cart updated");
        return "redirect:/cart/view";
    }

    @GetMapping("/remove")
    public String removeFromCart(@RequestParam Long bookId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cartItems");
        if (cart != null) {
            cart.removeIf(item -> item.getBook().getBookID().equals(bookId));
            session.setAttribute("cartItems", cart);
            redirectAttributes.addFlashAttribute("success", "Item removed from cart");
        }
        return "redirect:/cart/view";
    }
}
