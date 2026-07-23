package src.model;

import java.util.List;

public record PassengerLeaveResult(
        List<Passenger> remainingPassengers,
        List<Passenger> leftPassengers
) {
}