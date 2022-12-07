package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Customer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class CustomerDataManager implements DataManager {

    private final String RESOURCE;
    
    /**
     * Create a {@link CustomerDataManager} object with the default resources location
     */
    public CustomerDataManager() {
    	this.RESOURCE = "./resources/data/customers.txt";
    }
    
    /**
     * Create a {@link CustomerDataManager} object with a custom resources location
     * <p> used for testing
     */
    public CustomerDataManager(String resource) {
    	this.RESOURCE = resource;
    }
    
    /**
     * Loads customer data from the data file
     * and stored it in a {@link FlightBookingSystem} object
     * @param fbs as {@link FlightBookingSystem}
     * @throws IOException if there is an error loading the data file
     * @throws FlightBookingSystemException if there is an error parsing the data
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try(Scanner sc = new Scanner(new File(RESOURCE))) {
        	int line_idx = 1;
        	while(sc.hasNextLine()) {
        		String line = sc.nextLine();
        		String[] properties = line.split(SEPARATOR, -1);
        		
        		try {
        			int id = Integer.parseInt(properties[0]);
        			String name = properties[1];
        			String phone = properties[2];
        			String email = properties[3];
        			
        			Customer customer = new Customer(id, name, phone, email);
        			fbs.addCustomer(customer);
        		} catch (NumberFormatException ex) {
        			throw new FlightBookingSystemException("Unable to parse customer ID " + properties[0] + " on line: " + line_idx + "\nError: " + ex);
        		}
        		line_idx++;
        	}
        }
    }
    
    /**
     * Store the customer data from the system into the data file
     * @param fbs as {@link FlightBookingSystem}
     * @throws IOException if there is an error writing to the data file
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
        	for (Customer customer : fbs.getCustomers()) {
        		out.print(customer.getID() + SEPARATOR);
        		out.print(customer.getName() + SEPARATOR);
        		out.print(customer.getPhone() + SEPARATOR);
        		out.print(customer.getEmail() + SEPARATOR);
        		out.println();
        	}
        }
    }
}
