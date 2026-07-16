package src.model;

public class Showroom {

    private final Integer MAX_CAR_COUNT = 50;

    private Vehicle[] vehicles = new Vehicle[MAX_CAR_COUNT];
    private Integer carCount = 0;

    public Showroom() {
    }

    public void addVehicle(Vehicle vehicle) {

        if (carCount.equals(MAX_CAR_COUNT)) {
            System.out.println("Show room is full.");
            return;
        }

        vehicles[carCount++] = vehicle;

        System.out.println("Vehicle added process successful");
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

        for (Vehicle vehicle : vehicles) {

            if (vehicle.getName().equals(keyword)) {

                return vehicle;
            }
        }

        System.out.println("Vehicle is not found in show room.");

        return null;
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
