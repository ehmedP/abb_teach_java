package service;

import enums.FareTypeEnum;
import enums.TransportTypeEnum;
import model.*;

public class TransportManager {

    private TransportVehicle transportVehicle;

    private FareTypeEnum fareType;
    private double distance;
    private int passengers;

    public TransportManager(TransportVehicle transportVehicle, double distance, int passengers, FareTypeEnum fareType) {
        this.transportVehicle = transportVehicle;
        this.distance = distance;
        this.passengers = passengers;
        this.fareType = fareType;
    }

    public void startTransportProcess() {

    }

    public void printTransportInfo() {
        System.out.println();
        System.out.println("-------------------------------------- Travel Summary -------------------------------------");
        System.out.println("Transport : \n");
        System.out.println(this.transportVehicle.getTransportInfo());
        System.out.println("Distance  : " + distance + " km");
        System.out.println("Passengers: " + passengers);
        System.out.println("Fare      : " + this.transportVehicle.calculateFare(distance, passengers, fareType));
        System.out.println("Time      : " + this.transportVehicle.calculateTime(distance) + " hours");
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println();
    }

    public static TransportVehicle getTransportByType(TransportTypeEnum type) {

        return switch (type) {
            case TransportTypeEnum.TAXI -> new Taxi();
            case TransportTypeEnum.BUS -> new Bus();
            case TransportTypeEnum.BICYCLE -> new Bicycle();
            case TransportTypeEnum.SCOOTER -> new Scooter();
        };

    }

    public TransportVehicle getTransportVehicle() {
        return transportVehicle;
    }

    public FareTypeEnum getFareType() {
        return fareType;
    }

    public double getDistance() {
        return distance;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setTransportVehicle(TransportVehicle transportVehicle) {
        this.transportVehicle = transportVehicle;
    }

    public void setFareType(FareTypeEnum fareType) {
        this.fareType = fareType;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }
}
