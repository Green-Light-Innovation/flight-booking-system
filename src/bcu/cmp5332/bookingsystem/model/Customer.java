package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class model that represents a customer in the system
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private String email;
    private boolean isRemoved = false;
    private final List<Booking> bookings = new ArrayList<>();
    
    /**
     * @param id
     * @param name
     * @param phone
     */
    public Customer(int id, String name, String phone, String email) {
    	this.id		= id;
    	this.name	= name;
    	this.phone	= phone;
    	this.email 	= email;
    }
    
    // GETTERS
    /**
     * @return the customer's ID as {@link Integer}
     */
    public int getID() { return id; }
    
    /**
     * @return the customer's name as {@link String}
     */
    public String getName() { return name; }
    
    /**
     * @return the removed status as {@link boolean}
     */
	public boolean isRemoved() { return isRemoved; }

    /**
     * @return the customer's phone number as {@link String}
     */
    public String getPhone() { return phone; }
    
    /**
     * @return the customer's email as {@link String}
     */
    public String getEmail() { return email; }
    
    /**
     * @return A list of the customers bookings as {@link List}&lt;{@link Booking}&gt;
     */
    public List<Booking> getBookings() { return bookings; }
    
    /**
     * @return basic customer details as {@link String}
     */
    public String getDetailsShort() {
    	return "Id: " + id + " - " + name + " - " + phone + " - " + email;
    }
    
    /**
     * @return customer's details and bookings as {@link String}
     */
    public String getDetailsLong() {
    	String details = "Customer #" + id + "\n" +
    			"Name: " + name + "\n" +
    			"Phone: " + phone + "\n" +
    			"Email: " + email + "\n" +
    			"--------------------------\n" +
    			"Bookings:\n";
    	
    	for(Booking booking : bookings) {
    		String bookingDate = booking.getBookingDate().toString();
    		Flight flight = booking.getFlight();
    		details += "* Booking date: " +  bookingDate + " for " + flight.getDetailsShort() + "\n";
    	}
    	details += bookings.size() + " booking(s)";
    	
    	return details;
    }
    
    // SETTERS
    /**
     * Set the customer's ID
     * @param id as {@link Integer}
     */
    public void setID(int id) { this.id = id; }
    
    /**
     * Set the customer's name
     * @param name as {@link String}
     */
    public void setName(String name) { this.name = name; }
    
    /**
     * Set the customer's phone number
     * @param phone as {@link String}
     */
    public void setPhone(String phone) { this.phone = phone; }
    
    /**
     * Set the customer's removed status
     * @param isRemoved as {@link boolean}
     */
	public void setRemoved(boolean isRemoved) { this.isRemoved = isRemoved;}
    
    /**
     * Set the customer's email
     * @param email as {@link String}
     */
    public void setEmail(String email) {this.email = email; }
    
    /**
     * Adds a booking object to the customer's list of bookings
     * @throws FlightBookingSystemException if the customer's list of bookings already contains the specified booking}
     * @param booking as {@link Booking}
     */
    public void addBooking(Booking booking) throws FlightBookingSystemException {
    	
    	for(Booking customerBooking : bookings) {
    		// Compare both flight ID to check if they are the same flight
    		if (booking.getFlight().getId() == customerBooking.getFlight().getId()) {
    			throw new FlightBookingSystemException("This flight is already booked");
    		}
    	}
    	
    	bookings.add(booking);
    }
    
    /**
     * Removes the specified booking from the customer's list of bookings
     * @throws FlightBookingSystemException if the customer�s list of bookings doesn�t contain a booking for the flight that is given as a parameter
     * @param flight as {@link Flight}
     */
    public void cancelBooking(Flight flight) throws FlightBookingSystemException {
    	
    	for (Booking booking : bookings) {
    		if (booking.getFlight().getId() == flight.getId()) {
    			bookings.remove(booking);
    			return;
    		}
    	}
    	
    	throw new FlightBookingSystemException("Could not find the specified flight to cancel");
    }

}
