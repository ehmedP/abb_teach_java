package interfaces;

public interface ElectricPowered {

    void charge(double kWh);

    boolean hasEnoughBattery(double distanceKm);

}