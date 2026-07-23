package src.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Bus {

    public final Integer MAX_PASSENGER_CAPACITY = 5;

    private List<Passenger> passengers = new LinkedList<>();

    public Bus() {
        //
    }

    public Bus(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return passengers.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return Objects.equals(passengers, bus.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(passengers);
    }
}
