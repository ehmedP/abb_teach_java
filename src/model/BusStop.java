package src.model;

import java.util.*;

public class BusStop {

    public static final int MAX_PASSENGER_COUNT = 10;
    public static final int MAX_BUS_STOP_COUNT = 5;

    private static final Random random = new Random();

    private Integer id;
    private String name;
    private List<Passenger> passengers;

    public void arriveStop(Bus bus) {

        System.out.println("----------------------------------------------------------------------------------------");

        System.out.printf("Stop \"%s\" reached.%n", getName());

        System.out.println();
        System.out.println("Bus (Passengers): " + bus);
        System.out.println("Bus Stop (Passengers): " + passengers);
        System.out.println();


        leftPassengersFromBus(bus);
        boardedPassengers(bus);

        System.out.println();
        System.out.println("Bus (Passengers): " + bus);
        System.out.println("Bus Stop (Passengers): " + passengers);
        System.out.println();

        System.out.printf("Stop %d remaining: %s%n", getId(), getPassengers());

        System.out.println("----------------------------------------------------------------------------------------");

    }

    private void leftPassengersFromBus(Bus bus) {
        List<Passenger> selected = pickRandomPassengers(bus.getPassengers());

        for (Passenger passenger : selected) {
            System.out.println(passenger + " left the bus.");
        }

        bus.getPassengers().removeAll(selected);
    }

    private void boardedPassengers(Bus bus) {
        List<Passenger> selected = pickRandomPassengers(getPassengers());

        for (Passenger passenger : selected) {

            if (bus.getPassengers().size() >= Bus.MAX_PASSENGER_CAPACITY) {
                System.out.println("Bus is full. " + passenger + " cannot board the bus.");
            }

            else {
                System.out.println(passenger + " boarded the bus.");

                getPassengers().remove(passenger);
                bus.getPassengers().add(passenger);
            }
        }
    }

    private List<Passenger> pickRandomPassengers(List<Passenger> passengers) {

        if (passengers.isEmpty()) {
            return new LinkedList<>();
        }

        int leaveCount = random.nextInt(passengers.size() + 1);

        // Array list isletmekde meqsed index esasli islediyi ucun get ve shuffle processlerinin daha suretli olmasidir.
        List<Passenger> clonedPassengers = new ArrayList<>(passengers);
        List<Passenger> selected = new LinkedList<>();

        Collections.shuffle(clonedPassengers);

        for (int i = 0; i < leaveCount; i++) {
            Passenger passenger = clonedPassengers.get(i);

            if (passenger.isPriorityPassenger()) {
                selected.addFirst(passenger);
            }

            else {
                selected.add(passenger);
            }
        }

        return selected;
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
