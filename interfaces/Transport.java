package interfaces;

import enums.FareTypeEnum;

public interface Transport {

    double calculateFare(double distance, FareTypeEnum fareTypeEnum);

    double calculateFare(double distance, int passengers, FareTypeEnum fareTypeEnum);

    double calculateTime(double distance);

    String getTransportInfo();

    boolean canTravelDistance(double distanceKm);

    boolean canCarryPassengers(int passengerCount);

}
