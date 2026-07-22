package src.model;

import src.enums.PassengerPriorityTypeEnum;

import java.util.Objects;

public class Passenger {

    private String name;
    private PassengerPriorityTypeEnum priorityType;

    public Passenger(String name, PassengerPriorityTypeEnum priorityType) {
        this.name = name;
        this.priorityType = priorityType;
    }

    public PassengerPriorityTypeEnum getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(PassengerPriorityTypeEnum priorityType) {
        this.priorityType = priorityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " " + String.format("(%s)", priorityType.getLabel());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(name, passenger.name) && priorityType == passenger.priorityType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, priorityType);
    }
}
