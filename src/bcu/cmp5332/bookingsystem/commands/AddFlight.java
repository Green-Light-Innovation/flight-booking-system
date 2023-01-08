package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
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
    private final int passengerCapacity;
    private final double price;

    /**
     * Initialises attributes to construct a {@link Flight} from
     * @param flightNumber as {@link Integer}
     * @param origin as {@link Integer}
     * @param destination as {@link Integer}
     * @param departureDate as {@link LocalDate}
     */
    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate, int passengerCapacity, double price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.passengerCapacity = passengerCapacity;
        this.price = price;
    }
    /**
     * Constructs {@link Flight} object and saves it into a FlightBookingSystem object
     * @param fbs as {@link FlightBookingSystem} to execute command on
     * @throws FlightBookingSystemException when an error occurs while adding new flight
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        int maxId = 0;
        // Need to access ALL flights
        if (flightBookingSystem.getFlights().size() > 0) {
            int lastIndex = flightBookingSystem.getFlights().size() - 1;
            maxId = flightBookingSystem.getFlights().get(lastIndex).getId();
        }
        
        Flight flight = new Flight(++maxId, flightNumber, origin, destination, departureDate, passengerCapacity, price);
        flightBookingSystem.addFlight(flight);
        try {
			FlightBookingSystemData.store(flightBookingSystem);
        	System.out.println("Flight #" + flight.getId() + " added.");
			
		} catch (IOException exc) {
			flightBookingSystem.removeFlight(flight);
			throw new FlightBookingSystemException("Flight could not be created, rolling back changes...\nError: " + exc);

		}
    }
}
