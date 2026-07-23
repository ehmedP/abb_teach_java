package src.model;

import java.util.*;

public class BusStop {

    public static final Integer MAX_PASSENGER_COUNT = 10;

    private static final Random random = new Random();

    private Integer id;
    private String name;
    private List<Passenger> passengers;

    public void arriveStop(Bus bus) {

        System.out.println("----------------------------------------------------------------------------------------");

        System.out.printf("Stop \"%s\" reached.%n", getName());

        leftPassengers(bus);
        boardedPassengers(bus);

        System.out.println("Bus now: " + bus);

        System.out.printf("Stop %d remaining: %s%n", getId(), getPassengers());

        System.out.println("----------------------------------------------------------------------------------------");

    }

    private void leftPassengers(Bus bus) {
        List<Passenger> busPassengers = bus.getPassengers();

        if (busPassengers.isEmpty()) {
            return;
        }

        int leaveCount = random.nextInt(busPassengers.size() + 1);
        List<Passenger> leftPassengers = new LinkedList<>();

        for (int i = 0; i < leaveCount; i++) {
            Passenger passenger = busPassengers.remove(random.nextInt(busPassengers.size()));

            if (passenger.isPriorityPassenger()) {
                leftPassengers.addFirst(passenger);
            } else {
                leftPassengers.add(passenger);
            }

            if (busPassengers.isEmpty()) {
                break;
            }
        }

        for (Passenger passenger : leftPassengers) {
            System.out.println(passenger + " left the bus.");
        }

        bus.setPassengers(busPassengers);
    }

    private void boardedPassengers(Bus bus) {
        int passengerCount = random.nextInt(MAX_PASSENGER_COUNT + 1);

    }

    public BusStop(Integer id, String name, List<Passenger> passengers) {
        this.id = id;
        this.name = name;
        this.passengers = passengers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BusStop busStop = (BusStop) o;
        return Objects.equals(passengers, busStop.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengers);
    }
}
