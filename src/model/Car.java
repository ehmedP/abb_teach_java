package src.model;

import src.enums.FuelTypeEnum;

public class Car extends Vehicle {

    private Integer doors;
    private FuelTypeEnum fuelType;

    public Car(Integer id, String name, Boolean isAvailable, Integer doors, FuelTypeEnum fuelType) {
        super(id, name, isAvailable);

        this.fuelType = fuelType;
        this.doors = doors;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public FuelTypeEnum getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelTypeEnum fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public void displayInfo() {
        System.out.println(
            super.getBaseInfo()
        );

        System.out.println("Doors Count: "+ doors);
        System.out.println("Fuel Type: "+ fuelType.getLabel());
    }
}
