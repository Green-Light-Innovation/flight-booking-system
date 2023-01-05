package bcu.cmp5332.bookingsystem.gui;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * GUI pop-up used to view bookings for a given customer through the {@link FlightBookingSystem}
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class BookingsWindow extends JFrame{
	
	private FlightBookingSystem flightBookingSystem;
	private MainWindow mainWindow;
	
	private int customerID;
	private Customer customer;

	public BookingsWindow(MainWindow mainWindow, int customerID) {
		this.mainWindow = mainWindow;
		this.customerID = customerID;
		this.flightBookingSystem = mainWindow.getFlightBookingSystem();
		initialize();
	}
	
	private void initialize() {
		try {
			customer = flightBookingSystem.getCustomerByID(customerID);
			
		}  catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this.getContentPane(), "Customer not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
		if (customer.getBookings().size() == 0) {
            JOptionPane.showMessageDialog(this.getContentPane(), "No bookings found for customer", "Info", JOptionPane.INFORMATION_MESSAGE);

		} else {
			// Pop-up set-up
			setSize(400,350);
			setTitle("Customer bookings");
			// Set-up table header + table data
	        String[] columns = new String[]{"Flight Number", "Flight Date"};
	        Object[][] data = new Object[customer.getBookings().size()][2];
	        
	        for (int i = 0; i < customer.getBookings().size(); i++) {
	            Booking booking = customer.getBookings().get(i);
	            data[i][0] = booking.getFlight().getFlightNumber();
	            data[i][1] = booking.getFlight().getDepartureDate();
	        }
	        
	        JTable bookingTable = new JTable(data, columns) {
	            private static final long serialVersionUID = 1L;
	
	            public boolean isCellEditable(int row, int column) {                
	                    return false;               
	            };  
	        };
	        
	        // Add to panel
			this.getContentPane().add(bookingTable.getTableHeader(), BorderLayout.NORTH);
			this.getContentPane().add(bookingTable, BorderLayout.CENTER);
			setLocationRelativeTo(mainWindow);
			setVisible(true);
		}
	}
	
}
