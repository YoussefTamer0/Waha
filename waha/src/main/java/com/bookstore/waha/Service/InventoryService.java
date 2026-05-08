package com.bookstore.waha.Service;

import com.bookstore.waha.model.Book;
import com.bookstore.waha.model.Inventory;
import com.bookstore.waha.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BookService bookService;

    public InventoryService(InventoryRepository inventoryRepository, BookService bookService) {
        this.inventoryRepository = inventoryRepository;
        this.bookService = bookService;
    }

    @Transactional
    public Inventory createInventory(Long bookId, int quantity, int minStockLevel) {
        Book book = bookService.getBookById(bookId);

        Inventory inventory = new Inventory();
        inventory.setBook(book);
        inventory.setQuantity(quantity);
        inventory.setMinStockLevel(minStockLevel);

        return inventoryRepository.save(inventory);
    }

    @Transactional
    public Inventory updateInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public Inventory getInventoryByBookId(Long bookId) {
        List<Inventory> allInventory = inventoryRepository.findAll();
        for (Inventory inv : allInventory) {
            if (inv.getBook().getBookID().equals(bookId)) {
                return inv;
            }
        }
        return null;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public List<Inventory> getLowStockItems() {
        List<Inventory> allInventory = inventoryRepository.findAll();
        return allInventory.stream()
                .filter(inv -> inv.getQuantity() <= inv.getMinStockLevel())
                .collect(Collectors.toList());
    }

    @Transactional
    public Inventory updateStockQuantity(Long inventoryId, int newQuantity) {
        Inventory inventory = getInventoryById(inventoryId);
        if (inventory != null) {
            inventory.setQuantity(newQuantity);
            return inventoryRepository.save(inventory);
        }
        return null;
    }

    @Transactional
    public Inventory addStock(Long inventoryId, int additionalQuantity) {
        Inventory inventory = getInventoryById(inventoryId);
        if (inventory != null) {
            inventory.setQuantity(inventory.getQuantity() + additionalQuantity);
            return inventoryRepository.save(inventory);
        }
        return null;
    }

    public boolean hasInventory(Book book) {
        List<Inventory> allInventory = inventoryRepository.findAll();
        return allInventory.stream().anyMatch(inv -> inv.getBook().getBookID().equals(book.getBookID()));
    }

    public int getTotalStockQuantity() {
        List<Inventory> allInventory = inventoryRepository.findAll();
        return allInventory.stream().mapToInt(Inventory::getQuantity).sum();
    }

    public long getInventoryCount() {
        return inventoryRepository.count();
    }
}