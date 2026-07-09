package model;

import interfaces.FuelPowered;

public class Bus extends TransportVehicle
        implements FuelPowered {

    public Bus() {
        super(80, 200, 60, 300);
    }

    @Override
    public void refuel(double liters) {

    }

    @Override
    public boolean hasEnoughFuel(double distanceKm) {
        return false;
    }
}
