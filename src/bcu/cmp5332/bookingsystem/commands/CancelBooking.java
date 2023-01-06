package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;

/**
 * {@link Command} class that removes a {@link Booking} from the flight booking system.
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class CancelBooking implements Command {
	
	private final int CUSTOMER_ID;
	private final int FLIGHT_ID;
	
	/**
	 * @param customerID as {@link Integer}
	 * @param flightID as {@link Integer}
	 */
	public CancelBooking(int customerID, int flightID) {
		this.CUSTOMER_ID = customerID;
		this.FLIGHT_ID = flightID;
	}
	
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		try {
			Customer customer = flightBookingSystem.getCustomerByID(CUSTOMER_ID);
			Flight flight = flightBookingSystem.getFlightByID(FLIGHT_ID);
			Booking booking = null;
			
			// Find Booking object being referenced
	    	for(Booking customerBooking : customer.getBookings()) {
	    		if (FLIGHT_ID == customerBooking.getFlight().getId()) {
	    			booking = customerBooking;
	    		}
	    	}
	    	// Booking MUST be found 
	    	if (booking == null) {
	    		throw new FlightBookingSystemException("Booking not found");
	    	}
			
			customer.cancelBooking(flight);
			flight.removePassenger(customer);
			try {
				FlightBookingSystemData.store(flightBookingSystem);
				System.out.println(customer.getName() + "'s booking for flight: " + flight.getFlightNumber() + " has been successfully canceled");
				
			} catch (IOException exc) {
				customer.addBooking(booking);
				flight.addPassenger(customer);
				throw new FlightBookingSystemException("Booking could not be cancelled, rolling back changes...\nError: " + exc);
			}
						
		} catch(FlightBookingSystemException ex) {
			System.out.println("Booking cancelation failed.\nERROR: " + ex);
		}
	}

}
