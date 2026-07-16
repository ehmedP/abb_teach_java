package src.model;

public abstract class Vehicle {

    // States

    private Integer id;
    private String name;
    private Boolean isAvailable;

    public Vehicle(Integer id, String name, Boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    // Abstract methods

    public abstract void displayInfo();

    // Methods

    public void sell() {



    }

    // Getter & Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
