package model;

import enums.FareTypeEnum;

public class Bus extends FuelVehicle {

    public Bus() {
        super(80, 200, 60, 300, 200);
    }

    @Override
    public double calculateFare(double distance, FareTypeEnum fareTypeEnum) {
        return getRatePerKm() + fareExtra(fareTypeEnum);
    }

    @Override
    public double calculateFare(double distance, int passengers, FareTypeEnum fareTypeEnum) {
        return calculateFare(distance, fareTypeEnum) * passengers;
    }

    @Override
    public String getTransportInfo() {
        return super.getTransportInfo() +
                "Power Source         : Fuel (Internal Combustion)\n" +
                "Classification       : Public Transit\n";
    }
}
