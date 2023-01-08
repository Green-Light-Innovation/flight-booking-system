package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

/**
 * Class that represents core functionality of the program
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class FlightBookingSystem {
    
    private final LocalDate systemDate = LocalDate.parse("2020-11-11");
    
    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    
    /**
     * @return the set system date as {@link LocalDate}
     */
    public LocalDate getSystemDate() {
        return systemDate;
    }
    
    /**
     * Fetches all flights, including flights which are "removed" from the system
     * @return an unmodifiable list of flights as as {@link List}&lt;{@link Flight}&gt;
     */
    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>(flights.values());
        return Collections.unmodifiableList(out);
    }
    /**
     * Fetches all flights which are not "removed" from the system
     * @return an unmodifiable list of flights as {@link List}&lt;{@link Flight}&gt;
     */
    public List<Flight> getCurrentFlights() {
        List<Flight> out = new ArrayList<>();
        for (Flight flight : flights.values()) {
        	// Check that flight is NOT removed
        	if (! flight.isRemoved()) {
        		out.add(flight);
        	}
        }
        return Collections.unmodifiableList(out);
    }
    
    /**
     * Find a specific flight in the system by Flight ID
     * @param id as {@link Integer}
     * @return a {@link Flight} object with the corresponding ID
     * @throws FlightBookingSystemException if a flight with the specified ID could not be found
     */
    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        return flights.get(id);
    }
    /**
     * Fetches all customers, including customers which are "removed" from the system
     * @return an unmodifiable list of customers as {@link List}&lt;{@link Customer}&gt;
     */
    public List<Customer> getCustomers() {
    	List<Customer> customerList = new ArrayList<>();
    	customers.forEach((id, customer) -> { customerList.add(customer); }); // Add customer objects to list
    	return Collections.unmodifiableList(customerList);
    }
    /**
     * Fetches all customers which are not "removed" from the system
     * @return an unmodifiable list of non-removed customers as {@link List}&lt;{@link Customer}&gt;
     */
    public List<Customer> getCurrentCustomers() {
    	List<Customer> out = new ArrayList<>();
    	for (Customer customer : customers.values()) {
    		if (! customer.isRemoved()) {
    			out.add(customer);
    		}
    	}
    	return Collections.unmodifiableList(out);
    }
    
    /**
     * Find a specific customer in the system by ID
     * @param id as {@link Integer}
     * @return a {@link Customer} object with the corresponding ID
     * @throws FlightBookingSystemException if a customer with the specified ID could not be found
     */
    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        if (customers.get(id) == null) {
        	throw new FlightBookingSystemException("Could not find a customer with the specified ID in the system.");
        }
        return customers.get(id);
    }
    
    /**
     * Add a flight to the list of flights in the system
     * @param flight as {@link Flight}
     * @throws IllegalArgumentException if the flight has a duplicate ID to one already stored in the system
     * @throws FlightBookingSystemException if a flight with the same {@code flightNumber} and {@code departureDate} is already in the system
     */
    public void addFlight(Flight flight) throws IllegalArgumentException, FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber()) 
                && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with same "
                        + "number and departure date in the system");
            }
        }
        flights.put(flight.getId(), flight);
    }
    /**
     * Set a flight as removed
     * @param flight as {@link Flight}
     * @throws IllegalArgumentException when the given flight cannot be found in the system
     */
    public void removeFlight(Flight flight) throws IllegalArgumentException {
        if (flights.containsKey(flight.getId())) {
        	flight.setRemoved(true);
        } else {
            throw new IllegalArgumentException("No flight found.");	
        }
    }
    
    /**
     * Add a customer to the list of customers in the system
     * @param customer as {@link Customer}
     * @throws IllegalArgumentException if a customer with the same ID is already in the system
     * @throws FlightBookingSystemException if a customer with the same email is already in the system
     */
    public void addCustomer(Customer customer) throws IllegalArgumentException, FlightBookingSystemException {
    	// Check if the customer already exists in the tree map
    	if (customers.containsKey(customer.getID())) {
    		throw new IllegalArgumentException("This customer has already been added to the system.");
    	}
    	
    	// Check to see if customer with the same email already exists
    	for (Customer systemCustomer : customers.values()) {
    		if (customer.getEmail().equals( systemCustomer.getEmail() )) {
    			throw new FlightBookingSystemException("A customer with the email: '" + customer.getEmail() + "' already exists.");
    		}
    	}
    	
    	customers.put(customer.getID(), customer);
    }
    /**
     * Removes a customer from the list of customers in the system
     * @param customer as {@link Customer}
     * @throws IllegalArgumentException when the given customer cannot be found in the system

     */
    public void removeCustomer(Customer customer) throws IllegalArgumentException {
        if (customers.containsKey(customer.getID())) {
        	customer.setRemoved(true);
        } else {
            throw new IllegalArgumentException("No customer found.");	
        }
    }
}
