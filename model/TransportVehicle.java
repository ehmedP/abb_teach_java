package model;

import enums.FareTypeEnum;
import interfaces.Transport;

public abstract class TransportVehicle implements Transport {

    // final states
    private final double ratePerKm;
    private final double speed;
    private final int maxPassengerCount;
    private final double maxDistanceKm;

    private FareTypeEnum fareType;

    protected TransportVehicle(double ratePerKm, double speed, FareTypeEnum fareType, int maxPassengerCount, double maxDistanceKm) {
        this.ratePerKm = ratePerKm;
        this.speed = speed;
        this.fareType = fareType;
        this.maxPassengerCount = maxPassengerCount;
        this.maxDistanceKm = maxDistanceKm;
    }

    protected TransportVehicle(double ratePerKm, double speed, int maxPassengerCount, double maxDistanceKm) {
        this(ratePerKm, speed, null, maxPassengerCount, maxDistanceKm);
    }

    public void setFareType(FareTypeEnum fareType) {
        this.fareType = fareType;
    }

    public FareTypeEnum getFareType() {
        return fareType;
    }

    public double getRatePerKm() {
        return ratePerKm;
    }

    public double getSpeed() {
        return speed;
    }

    public int getMaxPassengerCount() {
        return maxPassengerCount;
    }

    public double getMaxDistanceKm() {
        return maxDistanceKm;
    }

    protected double fareExtra(FareTypeEnum fareTypeEnum) {
        return fareTypeEnum == null ? 0 : fareTypeEnum.getExtraFee();
    }

    @Override
    public double calculateFare(double distance, FareTypeEnum fareTypeEnum) {
        return distance * getRatePerKm() + fareExtra(fareTypeEnum);
    }

    @Override
    public double calculateTime(double distance) {
        return Math.ceil((distance / getSpeed()) * 100) / 100;
    }

    @Override
    public double calculateFare(double distance, int passengers, FareTypeEnum fareTypeEnum) {
        return this.calculateFare(distance, fareTypeEnum);
    }

    @Override
    public String getTransportInfo() {
        StringBuilder info = new StringBuilder();

        info.append(String.format("=== %s Specifications ===\n", this.getClass().getSimpleName()))
                .append(String.format("Max Speed            : %.1f km/h\n", speed))
                .append(String.format("Max Range            : %.1f km\n", maxDistanceKm))
                .append(String.format("Passenger Capacity   : %d\n", maxPassengerCount))
                .append(String.format("Rate Per Kilometer   : $%.2f\n", ratePerKm));

        if (fareType != null) {
            info.append(String.format("Fare Type            : %s\n", fareType.name()));
        }
        return info.toString();
    }

    @Override
    public boolean canTravelDistance(double distanceKm) {
        return this.getMaxDistanceKm() >= distanceKm;
    }

    @Override
    public boolean canCarryPassengers(int passengerCount) {
        return this.getMaxPassengerCount() >= passengerCount;
    }

}
