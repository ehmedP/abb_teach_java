package model;

import enums.FareTypeEnum;

public class Scooter extends ElectricVehicle {

    private static final double UNLOCK_FEE = 1.0;

    public Scooter() {
        super(20, 30, 2, 40, 1.5);
    }

    @Override
    public double calculateFare(double distance, FareTypeEnum fareTypeEnum) {
        return UNLOCK_FEE + distance * getRatePerKm() + fareExtra(fareTypeEnum);
    }

    @Override
    public double calculateFare(double distance, int passengers, FareTypeEnum fareTypeEnum) {
        return calculateFare(distance, fareTypeEnum) * passengers;
    }

    @Override
    public String getTransportInfo() {
        return super.getTransportInfo() +
                String.format("Unlock Base Fee      : $%.2f\n", UNLOCK_FEE) +
                              "Power Source         : Electric (EV)\n";
    }
}
