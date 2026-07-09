package model;

import enums.FareTypeEnum;
import interfaces.FuelPowered;

public class Taxi extends TransportVehicle
        implements FuelPowered {

    private static final double STARTING_FARE = 5.0;

    public Taxi() {
        super(100, 300, 5, 600);
    }

    @Override
    public double calculateFare(double distance, FareTypeEnum fareTypeEnum) {
        return STARTING_FARE + distance * getRatePerKm();
    }

    @Override
    public String getTransportInfo() {
        return super.getTransportInfo() +
                String.format("Base Starting Fare   : $%.2f\n", STARTING_FARE) +
                              "Power Source         : Fuel (Internal Combustion)\n";
    }

    @Override
    public void refuel(double liters) {

    }

    @Override
    public boolean hasEnoughFuel(double distanceKm) {
        return false;
    }
}
