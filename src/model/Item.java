package src.model;

import src.interfaces.Paginatable;

public abstract class Item
    implements Paginatable {

    // States

    private Integer id;
    private String title;
    private boolean isAvailable;

    public Item(Integer id, String title, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.isAvailable = isAvailable;
    }

    // Abstract methods

    public abstract void displayInfo();

    // Default methods

    @Override
    public void displaySimpleInfo() {
        System.out.println(getId() + ". " + getTitle() + ", Status: " + getAvailabilityText());
    }

    public void checkOut() {

        if (!isAvailable) {
            System.out.println("This item is already checked out.");
            return;
        }

        isAvailable = false;
    }

    public void returnItem() {

        if (isAvailable) {
            System.out.println("This item is already available.");
            return;
        }

        isAvailable = true;
    }

    // Helpers

    public String getAvailabilityText() {

        if (this.isAvailable) {
            return "Available";
        }

        return "Unavailable";
    }

    // Getters

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    // Setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
