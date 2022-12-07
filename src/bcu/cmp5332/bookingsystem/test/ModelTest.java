package bcu.cmp5332.bookingsystem.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import org.junit.Test;
import org.junit.Assert;
import bcu.cmp5332.bookingsystem.model.*;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

public class ModelTest {
	
	Customer customer;
	Flight flight;
	Booking booking;
	FlightBookingSystem system;
	
	// Customer Model Tests
	@Test
	public void testNewCustomer() {
		customer = new Customer(1, "John Doe", "07777777777", "john.doe@mail.com");
		Assert.assertEquals(1, customer.getID());
		Assert.assertEquals("John Doe", customer.getName());
		Assert.assertEquals("07777777777", customer.getPhone());
		Assert.assertEquals("john.doe@mail.com", customer.getEmail());
		Assert.assertEquals(Collections.EMPTY_LIST, customer.getBookings());
	}
	
	@Test
	public void testCustomerAddBooking() throws FlightBookingSystemException {
		// Test that a booking can be created and added to the customer's booking list
		customer = new Customer(1, "John Doe", "07777777777", "john.doe@mail.com");
		flight = new Flight(1, "Flight1", "UK", "USA", LocalDate.parse("2022-11-11"));
		booking = new Booking(customer, flight, LocalDate.parse("2022-11-11"));
		
		customer.addBooking(booking);
		
		Assert.assertEquals(1, customer.getBookings().size());
		
		// Test that a booking with the same flight can't be made
		Assert.assertThrows(FlightBookingSystemException.class, () -> { customer.addBooking(booking); });
		
		// Test that another booking with a different flight can be made
		flight = new Flight(2, "Flight2", "UK", "USA", LocalDate.parse("2022-12-11"));
		booking = new Booking(customer, flight, LocalDate.parse("2022-11-11"));
		
		customer.addBooking(booking);
		
		Assert.assertEquals(2, customer.getBookings().size());
	}
	
	@Test
	public void testCustomerCancelBooking() throws FlightBookingSystemException {
		// Test that an already existing booking can be cancelled
		customer = new Customer(1, "John Doe", "07777777777", "john.doe@mail.com");
		flight = new Flight(1, "Flight1", "UK", "USA", LocalDate.parse("2022-11-11"));
		booking = new Booking(customer, flight, LocalDate.parse("2022-11-11"));
		customer.addBooking(booking);
		
		customer.cancelBooking(flight);
		
		Assert.assertEquals(Collections.EMPTY_LIST, customer.getBookings());
		
		// Test that a booking can't be cancelled if it doesn't exist
		Assert.assertThrows(FlightBookingSystemException.class, () -> { customer.cancelBooking(flight); });
	}
	
	// Flight Model Tests
	@Test
	public void testNewFlight() {
		flight = new Flight(1, "Flight1", "UK", "USA", LocalDate.parse("2022-11-11"));
		Assert.assertEquals(1, flight.getId());
		Assert.assertEquals("Flight1", flight.getFlightNumber());
		Assert.assertEquals("UK", flight.getOrigin());
		Assert.assertEquals("USA", flight.getDestination());
		Assert.assertEquals(LocalDate.parse("2022-11-11"), flight.getDepartureDate());
	}
	
	@Test
	public void testFlightAddPassenger() throws FlightBookingSystemException {
		flight = new Flight(1, "Flight1", "UK", "USA", LocalDate.parse("2022-11-11"));
		customer = new Customer(1, "John Doe", "07777777777", "john.doe@mail.com");
		flight.addPassenger(customer);
		
		Assert.assertEquals(1, flight.getPassengers().size());
		Assert.assertThrows(FlightBookingSystemException.class, () -> { flight.addPassenger(customer); });
	}
	
	@Test
	public void testFlightRemovePassenger() throws FlightBookingSystemException {
		flight = new Flight(1, "Flight1", "UK", "USA", LocalDate.parse("2022-11-11"));
		customer = new Customer(1, "John Doe", "07777777777", "john.doe@mail.com");
		
		flight.addPassenger(customer);
		flight.removePassenger(customer);
		Assert.assertEquals(Collections.EMPTY_LIST, flight.getPassengers());
		
		Assert.assertThrows(FlightBookingSystemException.class, () -> { flight.removePassenger(customer); });
	}
	
	// FlightBookingSystem Model Tests
	@Test
	public void testNewFlightBookingSystsem() {
		system = new FlightBookingSystem();
		Assert.assertEquals(Collections.EMPTY_LIST, system.getCustomers());
		Assert.assertEquals(Collections.EMPTY_LIST, system.getFlights());
	}
	
	@Test
	public void testFlightBookingSystemAddFlight() throws IllegalArgumentException, FlightBookingSystemException {
		system = new FlightBookingSystem();
		flight = new Flight(1, "Flight1", "UK", "USA", LocalDate.parse("2022-11-11"));
		system.addFlight(flight);
		
		Assert.assertEquals(1, system.getFlights().size());
		Assert.assertThrows(IllegalArgumentException.class, () -> { system.addFlight(flight); });
		
		flight = new Flight(2, "Flight1", "UK", "USA", LocalDate.parse("2022-11-11"));
		Assert.assertThrows(FlightBookingSystemException.class, () -> { system.addFlight(flight); });
	}
	
	@Test
	public void testFlightBookingSystemAddCustomer() throws IllegalArgumentException, FlightBookingSystemException {
		system = new FlightBookingSystem();
		customer = new Customer(1, "John Doe", "07777777777", "john.doe@mail.com");
		system.addCustomer(customer);
		
		Assert.assertEquals(1, system.getCustomers().size());
		Assert.assertThrows(IllegalArgumentException.class, () -> { system.addCustomer(customer); });
		
		customer = new Customer(2, "John Doe", "07777777777", "john.doe@mail.com");
		Assert.assertThrows(FlightBookingSystemException.class, () -> { system.addCustomer(customer); });
	}
	
	@Test
	public void testFlightBookingSystemGetFlights() throws IllegalArgumentException, FlightBookingSystemException, UnsupportedOperationException {
		system = new FlightBookingSystem();
		Flight flight1 = new Flight(1, "Flight1", "UK", "USA", LocalDate.parse("2022-11-11"));
		Flight flight2 = new Flight(2, "Flight2", "UK", "USA", LocalDate.parse("2022-11-11"));
		Flight flight3 = new Flight(3, "Flight3", "UK", "USA", LocalDate.parse("2022-11-11"));
		
		system.addFlight(flight1);
		system.addFlight(flight2);
		system.addFlight(flight3);
		
		Assert.assertEquals(3, system.getFlights().size());
		Assert.assertThrows(UnsupportedOperationException.class, () -> { system.getFlights().remove(0); });
	}
	
	@Test
	public void testFlightBookingSystemGetFlightByID() throws FlightBookingSystemException{
		system = new FlightBookingSystem();
		Flight flight1 = new Flight(1, "Flight1", "UK", "USA", LocalDate.parse("2022-11-11"));
		Flight flight2 = new Flight(2, "Flight2", "UK", "USA", LocalDate.parse("2022-11-11"));
		Flight flight3 = new Flight(3, "Flight3", "UK", "USA", LocalDate.parse("2022-11-11"));
		
		system.addFlight(flight1);
		system.addFlight(flight2);
		system.addFlight(flight3);
		
		Assert.assertEquals(flight1, system.getFlightByID(1));
		Assert.assertEquals(flight2, system.getFlightByID(2));
		Assert.assertEquals(flight3, system.getFlightByID(3));
		
		Assert.assertThrows(FlightBookingSystemException.class, () -> { system.getFlightByID(0); });
	}
	
	@Test
	public void testFlightBookingSystemGetCustomers() throws IllegalArgumentException, UnsupportedOperationException, FlightBookingSystemException {
		system = new FlightBookingSystem();
		Customer customer1 = new Customer(1, "John Doe", "07777777777", "john.doe@mail.com");
		Customer customer2 = new Customer(2, "Jane Doe", "07777777778", "jane.doe@mail.com");
		Customer customer3 = new Customer(3, "John Smith", "07777777779", "john.smith@mail.com");
		
		system.addCustomer(customer1);
		system.addCustomer(customer2);
		system.addCustomer(customer3);
		
		Assert.assertEquals(3, system.getCustomers().size());
		Assert.assertThrows(UnsupportedOperationException.class, () -> { system.getCustomers().add(customer); });
	}
	
	@Test
	public void testFlightBookingSystemGetCustomerByID() throws FlightBookingSystemException {
		system = new FlightBookingSystem();
		Customer customer1 = new Customer(1, "John Doe", "07777777777", "john.doe@mail.com");
		Customer customer2 = new Customer(2, "Jane Doe", "07777777778", "jane.doe@mail.com");
		Customer customer3 = new Customer(3, "John Smith", "07777777779", "john.smith@mail.com");
		
		system.addCustomer(customer1);
		system.addCustomer(customer2);
		system.addCustomer(customer3);
		
		Assert.assertEquals(customer1, system.getCustomerByID(1));
		Assert.assertEquals(customer2, system.getCustomerByID(2));
		Assert.assertEquals(customer3, system.getCustomerByID(3));
		
		Assert.assertThrows(FlightBookingSystemException.class, () -> { system.getCustomerByID(0); });
	}
}
