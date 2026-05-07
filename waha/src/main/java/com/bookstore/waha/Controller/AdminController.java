package com.bookstore.waha.controller;

import com.bookstore.waha.Service.adminService;
import com.bookstore.waha.model.Book;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class AdminController {
    private final adminService adminservice;
    public AdminController(adminService adminservice){
        this.adminservice=adminservice;
    }
    @PostMapping("admin/books/add")
    public String insertBook(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model, RedirectAttributes redirectAttributes){
       if(result.hasErrors()){
           model.addAttribute("message", "Error occured.");
           return"admin/managebooks";
       }
        adminservice.addBook(book);
        redirectAttributes.addFlashAttribute("message", "Book Added successfully!");
        return"redirect:/admin/managebooks";
    }
    @PostMapping("admin/books/delete")
    public String removeBook(@RequestParam("bookID") Integer bookID, RedirectAttributes redirectAttributes) {
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


        return "redirect:/admin/books/manage";
    }
}
