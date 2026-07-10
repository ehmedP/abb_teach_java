package model;

import interfaces.FuelPowered;

public abstract class FuelVehicle extends TransportVehicle
        implements FuelPowered {

    private final double maxFuelLiters;
    private double currentFuelLiters;

    protected FuelVehicle(double ratePerKm, double speed, int maxPassengerCount, double maxDistanceKm, double maxFuelLiters) {
        super(ratePerKm, speed, maxPassengerCount, maxDistanceKm);
        this.maxFuelLiters = maxFuelLiters;
        this.currentFuelLiters = maxFuelLiters;
    }

    public double getMaxFuelLiters() {
        return maxFuelLiters;
    }

    public double getCurrentFuelLiters() {
        return currentFuelLiters;
    }

    @Override
    public void refuel(double liters) {
        if (liters <= 0) {
            return;
        }

        currentFuelLiters = Math.min(maxFuelLiters, currentFuelLiters + liters);
    }

    @Override
    public boolean hasEnoughFuel(double distanceKm) {
        return currentFuelLiters > 0;
    }

    @Override
    public String getTransportInfo() {
        return super.getTransportInfo()
                + String.format("Max Fuel Capacity    : %.1f L\n", maxFuelLiters);
    }
}
