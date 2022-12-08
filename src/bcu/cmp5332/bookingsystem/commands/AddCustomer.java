package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * {@link Command} class that adds a {@link Customer} to the flight booking system.
 * @author Jack Atkins
 * @author Jack Jukes
 */
public class AddCustomer implements Command {

    private final String name;
    private final String phone;
    private final String email;
    
    /**
     * 
     * @param name as {@link String}
     * @param phone as {@link String}
     * @param email as {@link String}
     */
    public AddCustomer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        int maxId = 0;
        if(flightBookingSystem.getCustomers().size() > 0) {
        	int lastIndex = flightBookingSystem.getCustomers().size() -1;
        	maxId = flightBookingSystem.getCustomers().get(lastIndex).getID();
        }
        
        Customer customer = new Customer(++maxId, name, phone, email);
        flightBookingSystem.addCustomer(customer);
        System.out.println("Customer #" + customer.getID() + " added.");
    }
}
