package service;

import enums.FareTypeEnum;
import enums.TMSMenuItemEnum;
import enums.TransportTypeEnum;
import model.TransportVehicle;

import java.util.Scanner;

public class TMSManager {

    private static final Scanner globalScanner = new Scanner(System.in);

    public void execute() {

        while (true) {

            switch (takeMainMenuOption()) {

                case TRAVEL:
                    handleTravel();
                    break;

                case EXIT:
                    finish();
                    return;
            }
        }
    }

    private TMSMenuItemEnum takeMainMenuOption() {

        while (true) {

            System.out.println("-------------------------------------------------------------------------------------------");
            System.out.println("------------------------------- Transport Management System -------------------------------");

            for (TMSMenuItemEnum item : TMSMenuItemEnum.values()) {
                System.out.println(item.getValue() + ". " + item.getLabel());
            }

            System.out.print("Select an option: ");

            int option = globalScanner.nextInt();

            TMSMenuItemEnum menuItem = TMSMenuItemEnum.fromValue(option);

            if (menuItem != null) {
                return menuItem;
            }

            System.out.println("Invalid menu option. Please try again.");
        }
    }

    private void handleTravel() {

        TransportTypeEnum transportType = takeTransportTypeViaCli();

        TransportVehicle transport = TransportManager.getTransportByType(transportType);

        TransportManager transportManager = new TransportManager(
                transport,
                takeTransportDistanceViaCli(transport),
                takeTransportPassengersViaCli(transport),
                takeFareTypeViaCli()
        );

        transportManager.printTransportInfo();
    }

    private static TransportTypeEnum takeTransportTypeViaCli() {

        while (true) {
            printTransportTypesMenu();

            System.out.print("Please choose transport type (1, 2 etc.): ");

            int transportValue = globalScanner.nextInt();

            TransportTypeEnum transportType = TransportTypeEnum.fromValue(transportValue);

            if (transportType == null) {
                System.out.println("Invalid transport type. Please select a valid option.");
                continue;
            }

            return transportType;
        }

    }

    private void finish() {
        System.out.println("----------------------------------------- Finish ------------------------------------------");
        globalScanner.close();
    }

    private static FareTypeEnum takeFareTypeViaCli() {

        while (true) {
            printFareTypesMenu();

            System.out.print("Please choose fare type (1, 2 etc.): ");

            int fareValue = globalScanner.nextInt();

            FareTypeEnum fareType = FareTypeEnum.fromValue(fareValue);

            if (fareType == null) {
                System.out.println("Invalid transport type. Please select a valid option.");
                continue;
            }

            return fareType;
        }

    }

    private static double takeTransportDistanceViaCli(TransportVehicle transport) {

        while (true) {
            System.out.print("Please enter the distance (KM): ");

            double distance = globalScanner.nextDouble();

            if (distance < 0) {
                System.out.println("Distance must be greater than 0.");
                continue;
            } else if (!transport.canTravelDistance(distance)) {
                System.out.println("This vehicle cannot travel that far. Maximum allowed distance is " + transport.getMaxDistanceKm() + " km.");
                continue;
            }

            return distance;
        }

    }

    private static int takeTransportPassengersViaCli(TransportVehicle transport) {

        while (true) {
            System.out.print("Please enter passenger count: ");

            int passengers = globalScanner.nextInt();

            if (passengers <= 0) {
                System.out.println("Passenger count must be greater than 0.");
                continue;
            }

            if (!transport.canCarryPassengers(passengers)) {
                System.out.println("Passenger count exceeds the vehicle's maximum capacity of " + transport.getMaxPassengerCount() + ".");
                continue;
            }

            return passengers;
        }

    }

    private static void printTransportTypesMenu() {

        int index = 1;

        for (TransportTypeEnum transportType : TransportTypeEnum.values()) {
            System.out.println(index++ + ". " + transportType.getLabel());
        }

    }

    private static void printFareTypesMenu() {

        int index = 1;

        for (FareTypeEnum fareType : FareTypeEnum.values()) {
            System.out.println(index++ + ". " + fareType.getLabel());
        }

    }

}
