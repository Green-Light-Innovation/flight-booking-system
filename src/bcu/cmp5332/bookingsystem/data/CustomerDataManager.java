package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

/** 
 * Handles loading {@link Customer}s into and out of the system from bookings.txt
 * @author Daniel Jukes
 * @author Jack Atkins
 *
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
     * @param resource as resource location
     */
    public CustomerDataManager(String resource) {
    	this.RESOURCE = resource;
    }
    
    /**
     * Loads customer data from the data file
     * and store it in a {@link FlightBookingSystem} object
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
        		// Properties has length of 6 due to trailing ::
        		if (properties.length != 6) {
        			throw new FlightBookingSystemException("Unable to parse customer ID " + properties[0] + " on line: " + line_idx + "\nMalformed Data");
        		}
        		
        		try {
        			int id = Integer.parseInt(properties[0]);
        			String name = properties[1];
        			String phone = properties[2];
        			String email = properties[3];
        			boolean isRemoved = Boolean.parseBoolean(properties[4]);
        			
        			Customer customer = new Customer(id, name, phone, email);
        			customer.setRemoved(isRemoved);
        			fbs.addCustomer(customer);
        		} catch (NumberFormatException ex) {
        			throw new FlightBookingSystemException("Unable to parse customer ID " + properties[0] + " on line: " + line_idx + "\nError: " + ex);
        		} catch (IndexOutOfBoundsException ex) {
        			throw new FlightBookingSystemException("Unable to parse customer ID " + properties[0] + " on line: " + line_idx + "\nError: " + ex);
        		}
        		line_idx++;
        	}
        } catch (FileNotFoundException ex) {
        	
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
        	// Must Access ALL customers
        	for (Customer customer : fbs.getCustomers()) {
        		out.print(customer.getID() + SEPARATOR);
        		out.print(customer.getName() + SEPARATOR);
        		out.print(customer.getPhone() + SEPARATOR);
        		out.print(customer.getEmail() + SEPARATOR);
        		out.print(customer.isRemoved() + SEPARATOR);
        		out.println();
        	}
        }
    }
}
