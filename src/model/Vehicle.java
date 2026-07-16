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

    public String getBaseInfo() {

        StringBuilder info = new StringBuilder();

        info
            .append(String.format("Id: %d\n", id))
            .append(String.format("Name: %s\n", name))
            .append(String.format("Availability: %s\n", isAvailable ? "Əlçatandır" : "Əlçatan deyil"));

        return info.toString();
    }

    public void sell() {

        if (!isAvailable) {
            System.out.println("Car is not available.");
        }

        isAvailable = false;
        System.out.println("Car sell successfully.");
    }

    public void returnToStock() {

        if (isAvailable) {
            System.out.println("Car already available.");
        }

        isAvailable = true;
        System.out.println("Car return successfully.");
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
