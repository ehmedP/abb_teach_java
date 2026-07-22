package src.service;

import src.model.Bus;
import src.model.BusStop;
import src.seeder.BusRouteSeeder;

import java.util.List;

public class BusRouteManager {

    private List<BusStop> stops;
    private final Bus bus;

    public void execute() {

        System.out.println("✅ Simulation started.");

        for (int i = 0; i < stops.size(); i++) {
            nextStop();
        }

        System.out.println("✅ Simulation finished.");
    }

    public void nextStop() {

        for (BusStop stop : stops) {

            System.out.println("----------------------------------------------------------------------------------------");

            System.out.printf("Stop %s reached.%n", stop.getName());

            System.out.println("Bus now: "+ bus);

            System.out.printf("Stop %d remaining: ", stop.getId());
            System.out.println(stop.getPassengers());

            System.out.println("----------------------------------------------------------------------------------------");
        }

    }

    public BusRouteManager() {
        this.bus = new Bus();
        this.stops = new BusRouteSeeder().execute();
    }

    public BusRouteManager(List<BusStop> stops, Bus bus) {
        this.stops = stops;
        this.bus = bus;
    }

    public List<BusStop> getStops() {
        return stops;
    }

    public void setStops(List<BusStop> stops) {
        this.stops = stops;
    }

}
