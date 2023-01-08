package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * {@link Command} class that outputs {@link Flight} details and passengers
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class ShowFlight implements Command {
	
	private final int ID;
	
	/**
	 * Takes in a flight ID to fetch flight data from
	 * @param id as {@link Integer}
	 */
	public ShowFlight(int id) {
		this.ID = id;
	}
	
	/**
	 * Prints given {@link Flight}s details 
     * @param fbs as {@link FlightBookingSystem} to execute command on 
     * @throws FlightBookingSystemException when an error occurs while printing {@link Flight}s details
     */
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		System.out.println(flightBookingSystem.getFlightByID(ID).getDetailsLong());
	}

}
