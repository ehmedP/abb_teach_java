package model;

import interfaces.ElectricPowered;

public class Scooter extends TransportVehicle
        implements ElectricPowered {

    public Scooter() {
        super(20, 30, 2, 40);
    }

    @Override
    public void charge(double kWh) {

    }

    @Override
    public boolean hasEnoughBattery(double distanceKm) {
        return false;
    }
}
