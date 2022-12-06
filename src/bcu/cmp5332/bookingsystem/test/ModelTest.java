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
		customer = new Customer(1, "John Doe", "07777777777");
		Assert.assertEquals(1, customer.getID());
		Assert.assertEquals("John Doe", customer.getName());
		Assert.assertEquals("07777777777", customer.getPhone());
		Assert.assertEquals(Collections.EMPTY_LIST, customer.getBookings());
	}
	
	@Test
	public void testCustomerAddBooking() throws FlightBookingSystemException {
		// Test that a booking can be created and added to the customer's booking list
		customer = new Customer(1, "John Doe", "07777777777");
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
		customer = new Customer(1, "John Doe", "07777777777");
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
		customer = new Customer(1, "John Doe", "07777777777");
		flight.addPassenger(customer);
		
		Assert.assertEquals(1, flight.getPassengers().size());
		Assert.assertThrows(FlightBookingSystemException.class, () -> { flight.addPassenger(customer); });
	}
	
	@Test
	public void testFlightRemovePassenger() throws FlightBookingSystemException {
		flight = new Flight(1, "Flight1", "UK", "USA", LocalDate.parse("2022-11-11"));
		customer = new Customer(1, "John Doe", "07777777777");
		
		flight.addPassenger(customer);
		flight.removePassenger(customer);
		Assert.assertEquals(Collections.EMPTY_LIST, flight.getPassengers());
		
		Assert.assertThrows(FlightBookingSystemException.class, () -> { flight.removePassenger(customer); });
	}
}
