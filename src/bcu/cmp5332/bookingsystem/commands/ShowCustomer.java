package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;

/**
 * {@link Command} class that outputs {@link Customer}'s details and {@link Booking}
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class ShowCustomer implements Command{
	
	private final int id;
	
	/**
	 * Takes in a customer ID to fetch customer data from
	 * @param id as {@link Integer}
	 */
	public ShowCustomer(int id) {
		this.id = id;
	}
	/**
	 * Prints given {@link Customer}s details 
     * @param fbs as {@link FlightBookingSystem} to execute command on 
     * @throws FlightBookingSystemException when an error occurs while printing {@link Customer}s details
     */
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		System.out.println(flightBookingSystem.getCustomerByID(id).getDetailsLong());
	}
	
}
