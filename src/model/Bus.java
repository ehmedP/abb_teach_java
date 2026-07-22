package src.model;

import java.util.ArrayList;
import java.util.Objects;

public class Bus {

    public final Integer MAX_PASSENGER_CAPACITY = 5;

    private ArrayList<Passenger> passengers = new ArrayList<>();

    public Bus() {
        //
    }

    public Bus(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
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
