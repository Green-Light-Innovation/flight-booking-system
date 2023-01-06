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
    private int passengerCapacity;
    private double price;
    private boolean isRemoved = false;

    private final Set<Customer> passengers;
    
    /**
     * @param id as {@link Integer}
     * @param flightNumber as {@link String}
     * @param origin as {@link String}
     * @param destination as {@link String}
     * @param departureDate as {@link LocalDate}
     * @param passengerCapacity as {@link Integer}
     * @param price as {@link Double}
     */
    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int passengerCapacity, double price) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.passengerCapacity = passengerCapacity;
        this.price = roundPrice(price);
        
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
     * @return the removed status as {@link boolean}
     */
	public boolean isRemoved() { return isRemoved; }
    
    /**
     * Set the customer's removed status
     * @param isRemoved as {@link boolean}
     */
	public void setRemoved(boolean isRemoved) { this.isRemoved = isRemoved;}
    
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
     * @param destination {@link String}
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
     * @return the number of passengers the {@link Flight} can have as {@link Integer}
     */
    public int getPassengerCapacity() {
    	return passengerCapacity;
    }
    
    /**
     * Set the passenger capacity of the flight
     * @param passengerCapacity as {@link Integer}
     */
    public void setPassengerCapacity(int passengerCapacity) {
    	this.passengerCapacity = passengerCapacity;
    }
    
    /**
     * @return the price of the flight as {@link Double}
     */
    public double getPrice() {
    	return price;
    }
    
    /**
     * set the price of the flight
     * @param price as {@link Double}
     */
    public void setPrice(double price) {
    	this.price = roundPrice(price);
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
                + destination + " on " + departureDate.format(dtf) +
                " (capacity of " + passengerCapacity + " passenger(s))" +
                " price: �" + price;
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
        		+ "Capacity: " + passengerCapacity + "\n"
        		+ "Price: �" + price + "\n"
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
    	// TODO Need to make sure no more passengers can be added past max capacity
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
    
    /**
     * @param price as {@link Float}
     * @return the specified price rounded to two decimal places
     */
    private double roundPrice(double price) {
    	return Double.parseDouble(String.format("%.2f", price));
    }

}
