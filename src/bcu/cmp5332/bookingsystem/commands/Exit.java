package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;

public class Exit implements Command {
	
	private final FlightBookingSystemData dataManager = new FlightBookingSystemData();
	
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		try {
			dataManager.store(flightBookingSystem);
		} catch(IOException ex) {
			throw new FlightBookingSystemException("Error storing flight booking system data\nError: " + ex);
		}
		System.exit(0);
	}

}
