package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class BookingDataManager implements DataManager {
    
    public final String RESOURCE;
    
    /**
     * Create a {@link CustomerDataManager} object with the default resources location
     */
    public BookingDataManager() {
    	this.RESOURCE = "./resources/data/bookings.txt";
    }
    
    /**
     * Create a {@link CustomerDataManager} object with a custom resources location
     * <p> used for testing
     */
    public BookingDataManager(String resource) {
    	this.RESOURCE = resource;
    }

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
    	
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
        	int line_idx = 1;
        	while(sc.hasNextLine()) {
        		String line = sc.nextLine();
        		String[] properties = line.split(SEPARATOR, -1);
        		
        		try {
        			int customerID = Integer.parseInt(properties[0]);
        			int flightID = Integer.parseInt(properties[1]);
        			LocalDate bookingDate = LocalDate.parse(properties[2]);
        			
        			Customer customer = fbs.getCustomerByID(customerID);
        			Flight flight = fbs.getFlightByID(flightID);
        			Booking booking = new Booking(customer, flight, bookingDate);
        			
        			fbs.getCustomerByID(customerID).addBooking(booking);
        			fbs.getFlightByID(flightID).addPassenger(customer);
        			
        		} catch (NumberFormatException ex) {
        			throw new FlightBookingSystemException("Unable to parse data: " + line + "\nError: " + ex);
        		}
        		line_idx++;
        	}
        }
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
    	try(PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
    		for (Customer customer : fbs.getCustomers()) {
    			for (Booking booking : customer.getBookings()) {
    				out.print(booking.getCustomer().getID() + SEPARATOR);
    				out.print(booking.getFlight().getId() + SEPARATOR);
    				out.print(booking.getBookingDate() + SEPARATOR);
    				out.println();
    			}
    		}
    	}
    }
    
}
