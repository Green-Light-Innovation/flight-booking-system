package bcu.cmp5332.bookingsystem.commands;

import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Customer;

/**
 * {@link Command} class that outputs all the {@link Customer}'s in the {@link FlightBookingSystem}
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class ListCustomers implements Command {
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		List<Customer> customers = flightBookingSystem.getCurrentCustomers();
		for (Customer customer : customers) {
			System.out.println(customer.getDetailsShort());
		}
		System.out.println(customers.size() + " customers(s)");
	}

}
