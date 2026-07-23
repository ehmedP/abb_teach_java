package src.model;

import java.util.ArrayList;
import java.util.Objects;

public class BusStop {

    public static final Integer MAX_PASSENGER_COUNT = 10;

    private Integer id;
    private String name;
    private ArrayList<Passenger> passengers;

    public void arriveStop(Bus bus) {

        System.out.println("----------------------------------------------------------------------------------------");

        System.out.printf("Stop \"%s\" reached.%n", getName());

        System.out.println("Bus now: "+ bus);

        System.out.printf("Stop %d remaining: ", getId());
        System.out.println(getPassengers());

        System.out.println("----------------------------------------------------------------------------------------");

    }

    public BusStop(Integer id, String name, ArrayList<Passenger> passengers) {
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

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
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
