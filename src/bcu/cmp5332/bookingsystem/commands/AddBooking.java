package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;


/**
 * {@link Command} class that adds a {@link Booking} to the flight booking system
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class AddBooking implements Command {
	private final int FLIGHT_ID;
	private final int CUSTOMER_ID;
	
	/**
     * Initialises attributes to construct a {@link Customer} from
	 * @param customerID as {@link Integer}
	 * @param flightID as {@link Integer}
	 */
	public AddBooking(int customerID, int flightID) {
		this.FLIGHT_ID = flightID;
		this.CUSTOMER_ID = customerID;
	}
	
    /**
     * Fetches {@link Customer} and {@link Flight} from given ID's,
     * constructs a {@link Booking}, assigns this booking to this customer,
     * then assigns customer to flight
     * @param fbs as {@link FlightBookingSystem} to execute command on
     * @throws FlightBookingSystemException when an error occurs while adding new booking
     */
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		try {
			Customer customer = flightBookingSystem.getCustomerByID(CUSTOMER_ID);
			Flight flight = flightBookingSystem.getFlightByID(FLIGHT_ID);
			Booking booking = new Booking(customer, flight, flightBookingSystem.getSystemDate());
			
			customer.addBooking(booking);
			flight.addPassenger(customer);
			try {
				FlightBookingSystemData.store(flightBookingSystem);
				
			} catch (IOException exc) {
				customer.cancelBooking(flight);
				flight.removePassenger(customer);
				throw new FlightBookingSystemException("Booking could not be made, rolling back changes...\nError: " + exc);
			}
		
		//System.out.println("Booking for " + customer.getName() + " for flight: " + flight.getFlightNumber() + " has been successfully made");
		} catch (FlightBookingSystemException ex) {
			throw new FlightBookingSystemException("Booking could not be made.\nError: " + ex);
		}
	}

}
