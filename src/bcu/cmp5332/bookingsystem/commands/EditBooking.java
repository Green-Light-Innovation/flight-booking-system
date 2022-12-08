package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;

/**
 * {@link Command} class that changes the {@link Flight} of a {@link Customer}'s 
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class EditBooking implements Command {
	
	private final int CUSTOMER_ID;
	private final int FLIGHT_ID;
	private final int NEW_FLIGHT_ID;
	
	/**
	 * @param customerID as {@link Integer}
	 * @param flightID as {@link Integer}
	 * @param newFlightID as {@link Integer}
	 */
	public EditBooking(int customerID, int flightID, int newFlightID) {
		this.CUSTOMER_ID = customerID;
		this.FLIGHT_ID = flightID;
		this.NEW_FLIGHT_ID = newFlightID;
	}
	
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		try {
			Customer customer = flightBookingSystem.getCustomerByID(CUSTOMER_ID);
			Flight flight = flightBookingSystem.getFlightByID(FLIGHT_ID);
			Flight newFlight = flightBookingSystem.getFlightByID(NEW_FLIGHT_ID);
			
			flight.removePassenger(customer);
			customer.cancelBooking(flight);
			customer.addBooking(new Booking(customer, newFlight, flightBookingSystem.getSystemDate()));
			newFlight.addPassenger(customer);
			
			System.out.println(customer.getName() + "'s booking for flight: " + flight.getFlightNumber() + " has been changed to flight: " + newFlight.getFlightNumber());
		} catch(FlightBookingSystemException ex) {
			throw new FlightBookingSystemException("Could not edit booking\nError: " + ex);
		}
		
	}

}
