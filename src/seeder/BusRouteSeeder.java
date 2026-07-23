package src.seeder;

import src.enums.PassengerPriorityTypeEnum;
import src.model.BusStop;
import src.model.Passenger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BusRouteSeeder {

    private final Random random = new Random();

    public List<BusStop> execute(int stopCount, int maxPassengersPerStop) {
        List<BusStop> stops = new ArrayList<>();
        PassengerPriorityTypeEnum[] priorityTypes = PassengerPriorityTypeEnum.values();

        int globalPassengerCounter = 1;

        for (int index = 0; index < stopCount; index++) {
            ArrayList<Passenger> passengers = new ArrayList<>();
            int passengerCount = random.nextInt(maxPassengersPerStop) + 1;

            for (int j = 0; j < passengerCount; j++) {
                PassengerPriorityTypeEnum randomPriority = priorityTypes[random.nextInt(priorityTypes.length)];
                String passengerName = "Passenger " + globalPassengerCounter++;

                passengers.add(new Passenger(passengerName, randomPriority));
            }

            stops.add(new BusStop(index+1, "Bus Stop "+ (index+1), passengers));
        }

        return stops;
    }

    public List<BusStop> execute() {
        return execute(BusStop.MAX_BUS_STOP_COUNT, BusStop.MAX_PASSENGER_COUNT);
    }

}
