package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class model that represents a flight in the system
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class Flight {
    
    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;

    private final Set<Customer> passengers;

    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        
        passengers = new HashSet<>();
    }
    
    /**
     * @return the ID of the flight as {@link Integer}
     */
    public int getId() {
        return id;
    }
    
    /**
     * Set the ID of the flight
     * @param id as {@link Integer}
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @return the flight number as {@link String}
     */
    public String getFlightNumber() {
        return flightNumber;
    }
    
    /**
     * Set the flight number
     * @param flightNumber as {@link String}
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    /**
     * @return the origin location of this flight as {@link String}
     */
    public String getOrigin() {
        return origin;
    }
    
    /**
     * Set the origin location of this flight
     * @param origin as {@link String}
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    /**
     * @return the destination of this flight as {@link String}
     */
    public String getDestination() {
        return destination;
    }
    
    /**
     * Set the destination location of this flight
     * @param destination {@link as String}
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    /**
     * @return the departure date of the flight as {@link java.time.LocalDate}
     */
    public LocalDate getDepartureDate() {
        return departureDate;
    }
    
    /**
     * Set the departure date of the flight
     * @param departureDate as {@link java.time.LocalDate}
     */
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }
    
    /**
     * @return a list of passengers that are booked for the flight as {@link List}&lt;{@link Customer}&gt;
     */
    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }
	
    /**
     * @return the basic details of the flight as {@link String}
     */
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to " 
                + destination + " on " + departureDate.format(dtf);
    }
    
    /**
     * @return the flight's details and passengers as {@link String}
     */
    public String getDetailsLong() {
        String details = "Flight: #" + id + "\n"
        		+ "Flight No: " + flightNumber + "\n"
        		+ "Origin: " + origin + "\n"
        		+ "Destination: " + destination + "\n"
        		+ "Departure Date: " + departureDate +"\n"
        		+ "---------------------------\n"
        		+ "Passengers:\n";
        
        for (Customer passenger : passengers) {
        	details += "* " + passenger.getDetailsShort() + "\n";
        }
        details += passengers.size() + " passenger(s)";
        
        return details;
    }
    
    /**
     * Add a passenger to the flight's list of passengers
     * @param passenger as {@link Customer}
     * @throws FlightBookingSystemException if the customer is already in the flight's list of passengers
     */
    public void addPassenger(Customer passenger) throws FlightBookingSystemException {
    	// Check if passenger is already booked for this flight
    	for(Customer flightPassenger : passengers) {
    		if (passenger.getID() == flightPassenger.getID()) {
    			throw new FlightBookingSystemException("This passenger is already booked for this flight.");
    		}
    	}
    	
    	passengers.add(passenger);
    }
    
    /**
     * Remove a passenger from the flight's list of passengers
     * @param passenger as {@link Customer}
     * @throws FlightBookingSystemException if the specified passenger could not be found
     */
    public void removePassenger(Customer passenger) throws FlightBookingSystemException {
    	// Find the passenger in the passengers list
    	for (Customer flightPassenger : passengers) {
    		if (passenger.getID() == flightPassenger.getID()) {
    			passengers.remove(flightPassenger);
    			return;
    		}
    	}
    	throw new FlightBookingSystemException("Could not find the specified passenger in the flights booking list.");
    }
}
