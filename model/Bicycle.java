package model;

import enums.FareTypeEnum;


public class Bicycle extends TransportVehicle {

    private static final double FREE_DISTANCE_KM = 1.0;

    public Bicycle() {
        super(50, 40, 2, 30);
    }

    @Override
    public double calculateFare(double distance, FareTypeEnum fareTypeEnum) {
        double billableDistance = Math.max(0, distance - FREE_DISTANCE_KM);

        return billableDistance * getRatePerKm();
    }

    @Override
    public String getTransportInfo() {
        return super.getTransportInfo() +
                String.format("Free Initial Distance: %.1f km\n", FREE_DISTANCE_KM);
    }

}
