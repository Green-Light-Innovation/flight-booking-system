package bcu.cmp5332.bookingsystem.commands;

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
	 * @param customerID as {@link Integer}
	 * @param flightID as {@link Integer}
	 */
	public AddBooking(int customerID, int flightID) {
		this.FLIGHT_ID = flightID;
		this.CUSTOMER_ID = customerID;
	}
	
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		try {
		Customer customer = flightBookingSystem.getCustomerByID(CUSTOMER_ID);
		Flight flight = flightBookingSystem.getFlightByID(FLIGHT_ID);
		//TODO Add booking status?
		Booking booking = new Booking(customer, flight, flightBookingSystem.getSystemDate());
		
		customer.addBooking(booking);
		flight.addPassenger(customer);
		
		System.out.println("Booking for " + customer.getName() + " for flight: " + flight.getFlightNumber() + " has been successfully made");
		} catch (FlightBookingSystemException ex) {
			throw new FlightBookingSystemException("Booking could be made.\nError: " + ex);
		}
	}

}
