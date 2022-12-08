package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
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
	 * @param id as {@link Integer}
	 */
	public ShowFlight(int id) {
		this.ID = id;
	}
	
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		System.out.println(flightBookingSystem.getFlightByID(ID).getDetailsLong());
	}

}
