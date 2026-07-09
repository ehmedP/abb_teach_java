package model;

import enums.FareTypeEnum;

public class Taxi extends FuelVehicle {

    private static final double STARTING_FARE = 5.0;
    private static final double EXTRA_PASSENGER_FEE = 2.0;

    public Taxi() {
        super(100, 300, 5, 600, 60);
    }

    @Override
    public double calculateFare(double distance, FareTypeEnum fareTypeEnum) {
        return STARTING_FARE + distance * getRatePerKm() + fareExtra(fareTypeEnum);
    }

    @Override
    public double calculateFare(double distance, int passengers, FareTypeEnum fareTypeEnum) {
        return calculateFare(distance, fareTypeEnum) + (passengers - 1) * EXTRA_PASSENGER_FEE;
    }

    @Override
    public String getTransportInfo() {
        return super.getTransportInfo() +
                String.format("Base Starting Fare   : $%.2f\n", STARTING_FARE) +
                              "Power Source         : Fuel (Internal Combustion)\n";
    }
}
