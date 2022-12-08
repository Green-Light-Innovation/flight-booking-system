package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.time.LocalDate;

/**
 * {@link Command} class that adds a {@link Flight} to the flight booking system.
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class AddFlight implements  Command {

    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDate departureDate;

    /**
     * 
     * @param flightNumber as {@link Integer}
     * @param origin as {@link Integer}
     * @param destination as {@link Integer}
     * @param departureDate as {@link LocalDate}
     */
    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
    }
    
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        int maxId = 0;
        if (flightBookingSystem.getFlights().size() > 0) {
            int lastIndex = flightBookingSystem.getFlights().size() - 1;
            maxId = flightBookingSystem.getFlights().get(lastIndex).getId();
        }
        
        Flight flight = new Flight(++maxId, flightNumber, origin, destination, departureDate);
        flightBookingSystem.addFlight(flight);
        System.out.println("Flight #" + flight.getId() + " added.");
    }
}
