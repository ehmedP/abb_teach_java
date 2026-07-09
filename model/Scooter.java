package model;

import enums.FareTypeEnum;
import interfaces.ElectricPowered;

public class Scooter extends TransportVehicle
        implements ElectricPowered {

    private static final double UNLOCK_FEE = 1.0;

    public Scooter() {
        super(20, 30, 2, 40);
    }

    @Override
    public double calculateFare(double distance, FareTypeEnum fareTypeEnum) {
        return UNLOCK_FEE + distance * getRatePerKm();
    }

    @Override
    public String getTransportInfo() {
        return super.getTransportInfo() +
                String.format("Unlock Base Fee      : $%.2f\n", UNLOCK_FEE) +
                              "Power Source         : Electric (EV)\n";
    }

    @Override
    public void charge(double kWh) {

    }

    @Override
    public boolean hasEnoughBattery(double distanceKm) {
        return false;
    }
}
