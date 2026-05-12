package com.bookstore.waha.Controller;

import com.bookstore.waha.Model.Book;
import com.bookstore.waha.Model.Inventory;
import com.bookstore.waha.Service.BookService;
import com.bookstore.waha.Service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final BookService bookService;

    public InventoryController(InventoryService inventoryService, BookService bookService) {
        this.inventoryService = inventoryService;
        this.bookService = bookService;
    }

    @GetMapping("/manage")
    public String manageInventory(Model model) {
        List<Inventory> inventoryItems = inventoryService.getAllInventory();

        List<Book> allBooks = bookService.getAllBooks();
        List<Book> booksWithoutInventory = allBooks.stream()
                .filter(book -> !inventoryService.hasInventory(book))
                .collect(Collectors.toList());

        model.addAttribute("inventoryItems", inventoryItems);
        model.addAttribute("booksWithoutInventory", booksWithoutInventory);
        model.addAttribute("totalStock", inventoryService.getTotalStockQuantity());
        model.addAttribute("inventoryCount", inventoryService.getInventoryCount());
        model.addAttribute("inventory", new Inventory());

        return "inventory/manage";
    }

    @PostMapping("/create")
    public String createInventory(@RequestParam Long bookId,
                                  @RequestParam int quantity,
                                  @RequestParam int minStockLevel,
                                  RedirectAttributes redirectAttributes) {
        try {
            Inventory inventory = inventoryService.createInventory(bookId, quantity, minStockLevel);
            redirectAttributes.addFlashAttribute("success",
                    "Inventory created for book: " + inventory.getBook().getTitle());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create inventory");
        }
        return "redirect:/inventory/manage";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);
        if (inventory != null) {
            List<Inventory> inventoryItems = inventoryService.getAllInventory();
            List<Book> booksWithoutInventory = bookService.getAllBooks().stream()
                    .filter(book -> !inventoryService.hasInventory(book))
                    .collect(Collectors.toList());

            model.addAttribute("editInventory", inventory);
            model.addAttribute("inventoryItems", inventoryItems);
            model.addAttribute("booksWithoutInventory", booksWithoutInventory);
            model.addAttribute("totalStock", inventoryService.getTotalStockQuantity());
            model.addAttribute("inventoryCount", inventoryService.getInventoryCount());
            model.addAttribute("inventory", new Inventory());
        }
        return "inventory/manage";
    }

    @PostMapping("/update")
    public String updateInventory(@ModelAttribute Inventory inventory,
                                  RedirectAttributes redirectAttributes) {
        try {
            inventoryService.updateInventory(inventory);
            redirectAttributes.addFlashAttribute("success", "Inventory updated");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update");
        }
        return "redirect:/inventory/manage";
    }

    @PostMapping("/delete/{id}")
    public String deleteInventory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            inventoryService.deleteInventory(id);
            redirectAttributes.addFlashAttribute("success", "Inventory deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete");
        }
        return "redirect:/inventory/manage";
    }

    @PostMapping("/stock/update")
    public String updateStock(@RequestParam Long inventoryId,
                              @RequestParam int quantity,
                              RedirectAttributes redirectAttributes) {
        Inventory inventory = inventoryService.updateStockQuantity(inventoryId, quantity);
        if (inventory != null) {
            redirectAttributes.addFlashAttribute("success", "Stock updated to: " + quantity);
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update stock");
        }
        return "redirect:/inventory/manage";
    }

    @PostMapping("/stock/add")
    public String addStock(@RequestParam Long inventoryId,
                           @RequestParam int additionalQuantity,
                           RedirectAttributes redirectAttributes) {
        Inventory inventory = inventoryService.addStock(inventoryId, additionalQuantity);
        if (inventory != null) {
            redirectAttributes.addFlashAttribute("success",
                    "Added " + additionalQuantity + " units. New stock: " + inventory.getQuantity());
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to add stock");
        }
        return "redirect:/inventory/manage";
    }

    @GetMapping("/lowstock")
    public String viewLowStock(Model model) {
        List<Inventory> lowStockItems = inventoryService.getLowStockItems();
        List<Book> booksWithoutInventory = bookService.getAllBooks().stream()
                .filter(book -> !inventoryService.hasInventory(book))
                .collect(Collectors.toList());

        model.addAttribute("inventoryItems", lowStockItems);
        model.addAttribute("booksWithoutInventory", booksWithoutInventory);
        model.addAttribute("totalStock", inventoryService.getTotalStockQuantity());
        model.addAttribute("inventoryCount", inventoryService.getInventoryCount());
        model.addAttribute("lowStockWarning", true);
        model.addAttribute("inventory", new Inventory());

        return "inventory/manage";
    }

    @GetMapping("/search")
    public String searchInventory(@RequestParam(required = false) String keyword, Model model) {
        List<Inventory> allInventory = inventoryService.getAllInventory();
        List<Inventory> filteredInventory;

        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchKeyword = keyword.toLowerCase().trim();
            filteredInventory = allInventory.stream()
                    .filter(item -> item.getBook().getTitle().toLowerCase().contains(searchKeyword))
                    .collect(Collectors.toList());
            model.addAttribute("searchKeyword", keyword);
        } else {
            filteredInventory = allInventory;
        }

        List<Book> booksWithoutInventory = bookService.getAllBooks().stream()
                .filter(book -> !inventoryService.hasInventory(book))
                .collect(Collectors.toList());

        model.addAttribute("inventoryItems", filteredInventory);
        model.addAttribute("booksWithoutInventory", booksWithoutInventory);
        model.addAttribute("totalStock", inventoryService.getTotalStockQuantity());
        model.addAttribute("inventoryCount", inventoryService.getInventoryCount());
        model.addAttribute("inventory", new Inventory());

        return "inventory/manage";
    }
}