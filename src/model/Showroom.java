package src.model;

public class Showroom {

    private Vehicle[] vehicles;
    private Integer carCount = 0;

    public Showroom() {
    }

    public void addVehicle(Vehicle vehicle) {
    }

    public void showAllVehicles() {

        if (vehicles.length == 0) {
            System.out.println("Show room is empty");
        }

        for (Vehicle vehicle : vehicles) {
            System.out.println("-----------------------------------");
            vehicle.displayInfo();
            System.out.println("-----------------------------------");
        }

        System.out.println("Show process ended");
    }

    public void sellVehicle(int id) {

        Vehicle found = findById(id);

        if (found == null) {
            System.out.println("Vehicle not found in show room.");
            return;
        }

        found.sell();
    }

    public void returnVehicle(int id) {

        Vehicle found = findById(id);

        if (found == null) {
            System.out.println("Vehicle not found in show room.");
            return;
        }

        found.returnToStock();
    }

    public Vehicle searchByModel(String keyword) {

        System.out.println();
    }

    public Vehicle findById(Integer id) {

        for (Vehicle vehicle : vehicles) {

            if (vehicle.getId().equals(id)) {
                return vehicle;
            }
        }

        return null;
    }

    public Integer getCarCount() {
        return carCount;
    }
}
