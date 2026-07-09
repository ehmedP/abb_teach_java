package model;

import enums.FareTypeEnum;
import interfaces.FuelPowered;

public class Bus extends TransportVehicle
        implements FuelPowered {

    public Bus() {
        super(80, 200, 60, 300);
    }

    @Override
    public double calculateFare(double distance, FareTypeEnum fareTypeEnum) {
        return getRatePerKm();
    }

    @Override
    public String getTransportInfo() {
        return super.getTransportInfo() +
                "Power Source         : Fuel (Internal Combustion)\n" +
                "Classification       : Public Transit\n";
    }

    @Override
    public void refuel(double liters) {

    }

    @Override
    public boolean hasEnoughFuel(double distanceKm) {
        return false;
    }
}
