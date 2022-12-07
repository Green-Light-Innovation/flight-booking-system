package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

/**
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
     * @return an unmodifiable list of flights as {@link List}&lt;{@link Flight}&gt;
     */
    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>(flights.values());
        return Collections.unmodifiableList(out);
    }
    
    /**
     * Find a specific flight in the system by ID
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
     * @return an unmodifiable list of flights as {@link List}&lt;{@link Customer}&gt;
     */
    public List<Customer> getCustomers() {
    	List<Customer> customerList = new ArrayList<>();
    	customers.forEach((id, customer) -> { customerList.add(customer); }); // Add customer objects to list
    	
    	return Collections.unmodifiableList(customerList);
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
     * Add a customer to the list of customers in the system
     * @param customer as {@link Customer}
     * @throws IllegalArgumentException if a customer with the same ID is already in the system
     */
    public void addCustomer(Customer customer) throws IllegalArgumentException {
    	// Check if the customer already exists in the tree map
    	if (customers.containsKey(customer.getID())) {
    		throw new IllegalArgumentException("This customer has already been added to the system.");
    	}
    	
    	customers.put(customer.getID(), customer);
    }
}
