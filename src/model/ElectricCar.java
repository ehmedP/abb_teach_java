package src.model;

public class ElectricCar extends Vehicle {

    private Integer batteryCapacity;

    public ElectricCar(Integer id, String name, Boolean isAvailable, Integer batteryCapacity) {
        super(id, name, isAvailable);

        this.batteryCapacity = batteryCapacity;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public void displayInfo() {
        System.out.println(
                super.getBaseInfo()
        );

        System.out.println("Battery Capacity: " + batteryCapacity + " kWh");
    }
}
