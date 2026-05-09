package com.bookstore.waha.Model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

        private List<CartItem> items;

        public Cart() {
            this.items = new ArrayList<>();
        }

        public Cart(List<CartItem> items) {
            this.items = items;
        }

        public List<CartItem> getItems() {
            return items;
        }

        public void setItems(List<CartItem> items) {
            this.items = items;
        }

        public void addItem(CartItem newItem) {
            for (CartItem item : items) {
                if (item.getBook().getBookID().equals(newItem.getBook().getBookID())) {
                    item.setQuantity(item.getQuantity() + newItem.getQuantity());
                    return;
                }
            }
            items.add(newItem);
        }

        public void removeItem(Long bookID) {
            items.removeIf(item -> item.getBook().getBookID().equals(bookID));
        }

        public void clear() {
            items.clear();
        }

        public double getTotal() {
            double total = 0;
            for (CartItem item : items) {
                total += item.getBook().getPrice() * item.getQuantity();
            }
            return total;
        }

        public int getItemCount() {
            return items.size();
        }

        public boolean isEmpty() {
            return items.isEmpty();
        }

        public void updateQuantity(Long bookID, int quantity) {
            for (CartItem item : items) {
                if (item.getBook().getBookID().equals(bookID)) {
                    if (quantity <= 0) {
                        items.remove(item);
                    } else {
                        item.setQuantity(quantity);
                    }
                    return;
                }
            }
        }

    }


