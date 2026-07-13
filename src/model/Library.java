package src.model;

import java.util.Arrays;

public class Library {

    public static final int LIBRARY_MAX_CAPACITY = 1000;
    public static final int LIBRARY_MIN_CAPACITY = 50;

    private int itemCount = 0;
    private Item[] items = new Item[LIBRARY_MIN_CAPACITY];

    public void addItem(Item item) {

        if (items.length == itemCount) {
            expandLibrary();
        }

        else if (item.getTitle().isBlank()) {
            System.out.println("Kitab ucun basliq bosh ola bilmez.");
            return;
        }

        items[itemCount++] = item;
    }

    public void paginatedItems() {


    }

    public void checkOutItem(int id) {
        Item found = this.getItemById(id);

        if (found == null) {
            System.out.println("Kitab kitabxanada movcud deyil.");
            return;
        }

        found.checkOut();
    }

    public void returnItem(int id) {

        Item found = this.getItemById(id);

        if (found == null) {
            System.out.println("Kitab kitabxanada movcud deyil.");
            return;
        }

        found.returnItem();
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
            System.out.println("Kitabxana maksimum tutuma çatmışdır.");
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
