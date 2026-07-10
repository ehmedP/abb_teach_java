package model;

import interfaces.ElectricPowered;

public abstract class ElectricVehicle extends TransportVehicle
        implements ElectricPowered {

    private final double maxBatteryKWh;
    private double currentBatteryKWh;

    protected ElectricVehicle(double ratePerKm, double speed, int maxPassengerCount, double maxDistanceKm, double maxBatteryKWh) {
        super(ratePerKm, speed, maxPassengerCount, maxDistanceKm);
        this.maxBatteryKWh = maxBatteryKWh;
        this.currentBatteryKWh = maxBatteryKWh;
    }

    public double getMaxBatteryKWh() {
        return maxBatteryKWh;
    }

    public double getCurrentBatteryKWh() {
        return currentBatteryKWh;
    }

    @Override
    public void charge(double kWh) {
        if (kWh <= 0) {
            return;
        }
        currentBatteryKWh = Math.min(maxBatteryKWh, currentBatteryKWh + kWh);
    }

    @Override
    public boolean hasEnoughBattery(double distanceKm) {
        return currentBatteryKWh > 0;
    }

    @Override
    public String getTransportInfo() {
        return super.getTransportInfo()
                + String.format("Max Battery Capacity : %.1f kWh\n", maxBatteryKWh);
    }
}
