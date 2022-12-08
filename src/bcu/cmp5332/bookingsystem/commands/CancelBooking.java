package bcu.cmp5332.bookingsystem.commands;

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
			
			customer.cancelBooking(flight);
			flight.removePassenger(customer);
			
			System.out.println(customer.getName() + "'s booking for flight: " + flight.getFlightNumber() + " has been successfully canceled");
			
		} catch(FlightBookingSystemException ex) {
			System.out.println("Booking cancelation failed.\nERROR: " + ex);
		}
	}

}
