package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Customer;

public class ShowCustomer implements Command{
	
	private final int id;
	
	public ShowCustomer(int id) {
		this.id = id;
	}
	
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		System.out.println(flightBookingSystem.getCustomerByID(id).getDetailsLong());
	}
	
}
