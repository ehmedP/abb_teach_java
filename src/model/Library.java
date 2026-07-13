package src.model;

import src.util.Pagination;

import java.util.Arrays;

public class Library {

    public static final int LIBRARY_MAX_CAPACITY = 1000;
    public static final int LIBRARY_MIN_CAPACITY = 50;

    private Integer itemCount = 0;
    private Item[] items = new Item[LIBRARY_MIN_CAPACITY];

    public void addItem(Item item) {

        if (items.length == itemCount) {
            expandLibrary();
        }

        else if (item.getTitle().isBlank()) {
            System.out.println("The title cannot be empty. Please enter a valid title.");
            return;
        }

        items[itemCount++] = item;
    }

    public void paginatedItems() {

        if (itemCount == 0) {
            System.out.println("There are no items in the library yet.");
            return;
        }

        Pagination.paginatedItems(itemCount, items);
    }

    public void checkOutItem(int id) {
        Item found = this.getItemById(id);

        if (found == null) {
            System.out.println("No item was found with the specified ID.");
            return;
        }

        found.checkOut();
        System.out.println("The item has been checked out successfully.");
    }

    public void returnItem(int id) {

        Item found = this.getItemById(id);

        if (found == null) {
            System.out.println("No item was found with the specified ID.");
            return;
        }

        found.returnItem();
        System.out.println("The item has been returned successfully.");
    }

    public Item searchByTitle(String keyword) {

        for (Item item : items) {

            // strict search
            if (item.getTitle().equalsIgnoreCase(keyword.trim())) {
                return item;
            }
        }

        return null;
    }

    private Item getItemById(int id) {

        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    private void expandLibrary() {

        if (itemCount < items.length) {
            return;
        }

        if (items.length >= LIBRARY_MAX_CAPACITY) {
            System.out.println("The library has reached its maximum capacity. No more items can be added.");
            return;
        }

        items = Arrays.copyOf(items, Math.min(items.length * 2, LIBRARY_MAX_CAPACITY));
    }

    private void shrinkLibrary() {

        if (items.length <= LIBRARY_MIN_CAPACITY) {
            return;
        }

        if (itemCount > items.length / 4) {
            return;
        }

        items = Arrays.copyOf(items, Math.max(items.length / 2, LIBRARY_MIN_CAPACITY));
    }

    public int getItemCount() {
        return this.itemCount;
    }

}
