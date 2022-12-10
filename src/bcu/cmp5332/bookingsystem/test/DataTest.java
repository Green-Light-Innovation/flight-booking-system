package bcu.cmp5332.bookingsystem.test;

import bcu.cmp5332.bookingsystem.data.*;
import bcu.cmp5332.bookingsystem.model.*;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Assert;

public class DataTest {
	CustomerDataManager cdm;
	FlightDataManager fdm;
	BookingDataManager bdm;
	FlightBookingSystem system;
	
	@Test
	public void testCustomerDataManagerLoadData() throws IOException, FlightBookingSystemException {
		cdm = new CustomerDataManager("./resources/test/customer_data_manager/test_load_data.txt");
		system = new FlightBookingSystem();
		
		cdm.loadData(system);
		
		Assert.assertEquals(2, system.getCustomers().size());
		
		cdm = new CustomerDataManager("./resources/test/customer_data_manager/non_existant_file.txt");
		system = new FlightBookingSystem();
		
		Assert.assertThrows(IOException.class, () -> { cdm.loadData(system); });
	}
	
	@Test
	public void testCustomerDataManagerStoreData() throws IllegalArgumentException, FlightBookingSystemException, IOException {
		cdm = new CustomerDataManager("./resources/test/customer_data_manager/test_store_data.txt");
		system = new FlightBookingSystem();
		
		Customer customer1 = new Customer(1, "John Doe", "07111111111", "john.doe@mail.com");
		Customer customer2 = new Customer(2, "Jane Doe", "07111111112", "jane.doe@mail.com");
		system.addCustomer(customer1);
		system.addCustomer(customer2);
		
		cdm.storeData(system);
		
		system = new FlightBookingSystem();
		cdm.loadData(system);
		
		Assert.assertEquals(customer1.getDetailsShort(), system.getCustomerByID(1).getDetailsShort());
		Assert.assertEquals(customer2.getDetailsShort(), system.getCustomerByID(2).getDetailsShort());
	}
	
	@Test
	public void testBookingDataManagerLoadData() throws IOException, FlightBookingSystemException {
		system = new FlightBookingSystem();
		cdm = new CustomerDataManager("./resources/test/booking_data_manager/load_test_data/customers.txt");
		fdm = new FlightDataManager("./resources/test/booking_data_manager/load_test_data/flights.txt");
		bdm = new BookingDataManager("./resources/test/booking_data_manager/load_test_data/bookings.txt");
		
		fdm.loadData(system);
		cdm.loadData(system);
		bdm.loadData(system);
		
		for (Customer customer : system.getCustomers()) {
			Assert.assertEquals(1, customer.getBookings().size());
		}
		
		for (Flight flight : system.getFlights()) {
			Assert.assertEquals(1, flight.getPassengers().size());
		}
		
		bdm = new BookingDataManager("./resources/test/booking_data_manager/load_test_data/invalid_booking.txt");
		Assert.assertThrows(FlightBookingSystemException.class, () -> { bdm.loadData(system); });
		
		bdm = new BookingDataManager("./resources/test/booking_data_manager/load_test_data/non_existant_file.txt");
		Assert.assertThrows(IOException.class, () -> { bdm.loadData(system); });
	}
	
	@Test
	public void testBookingDataManagerStoreData() throws IllegalArgumentException, FlightBookingSystemException, IOException {
		system = new FlightBookingSystem();
		bdm = new BookingDataManager("./resources/test/booking_data_manager/test_store_data.txt");
		
		// Create Data and store
		Customer customer1 = new Customer(1, "John Doe", "07111111111", "john.doe@mail.com");
		Customer customer2 = new Customer(2, "Jane Doe", "07111111112", "jane.doe@mail.com");
		
		Flight flight = new Flight(1, "FLIGHT1", "London", "France", LocalDate.parse("2022-11-11"), 10, 40.0);
		flight.addPassenger(customer1);
		flight.addPassenger(customer2);
		
		Booking customer1Booking = new Booking(customer1, flight, LocalDate.parse("2022-09-10"));
		Booking customer2Booking = new Booking(customer2, flight, LocalDate.parse("2022-09-10"));
		
		customer1.addBooking(customer1Booking);
		customer2.addBooking(customer2Booking);
		
		system.addFlight(flight);
		system.addCustomer(customer1);
		system.addCustomer(customer2);
		
		bdm.storeData(system);
		
		// Create new FlightBookingSystem with the same customers and flights and test if booking data is loaded correctly
		system = new FlightBookingSystem();
		
		flight = new Flight(1, "FLIGHT1", "London", "France", LocalDate.parse("2022-11-11"), 10, 40.0);
		customer1 = new Customer(1, "John Doe", "07111111111", "john.doe@mail.com");
		customer2 = new Customer(2, "Jane Doe", "07111111112", "jane.doe@mail.com");
		
		system.addFlight(flight);
		system.addCustomer(customer1);
		system.addCustomer(customer2);
		
		bdm.loadData(system);
		
		for (Customer customer : system.getCustomers()) {
			Assert.assertEquals(1, customer.getBookings().size());
		}
		
		Assert.assertEquals(2, system.getFlightByID(1).getPassengers().size());
	}
}
