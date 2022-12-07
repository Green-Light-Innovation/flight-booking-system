package bcu.cmp5332.bookingsystem.test;

import bcu.cmp5332.bookingsystem.data.*;
import bcu.cmp5332.bookingsystem.model.*;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import org.junit.Test;

import java.io.IOException;
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
}
