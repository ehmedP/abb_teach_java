package interfaces;

public interface FuelPowered {

    void refuel(double liters);

    boolean hasEnoughFuel(double distanceKm);

}