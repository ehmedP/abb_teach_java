package model;

import interfaces.FuelPowered;

public class Taxi extends TransportVehicle
        implements FuelPowered {

    public Taxi() {
        super(100, 300, 5, 600);
    }

    @Override
    public void refuel(double liters) {

    }

    @Override
    public boolean hasEnoughFuel(double distanceKm) {
        return false;
    }
}
