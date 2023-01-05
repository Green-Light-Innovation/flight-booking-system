package bcu.cmp5332.bookingsystem.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;


/**
 * GUI pop-up used to view {@link Customer}s on a given {@link Flight} through the {@link FlightBookingSystem}
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class PassengersWindow extends JFrame {
	
	private MainWindow mainWindow;
	
	private FlightBookingSystem flightBookingSystem;
	
	private int flightId;
	private Flight flight;
	/**
	 * Initialize the window
	 * @param mainWindow as {@link MainWindow}
	 * @param flightId as integer for current {@link Flight}
	 */
	public PassengersWindow(MainWindow mainWindow, int flightId) {
		this.mainWindow = mainWindow;
		this.flightId = flightId;
		this.flightBookingSystem = mainWindow.getFlightBookingSystem();
		initialize();
	}
	
	/**
	 * Create all the GUI elements and add them to the {@link JFrame}
	 */
	private void initialize() {
		try {
			flight = flightBookingSystem.getFlightByID(flightId);
			
		}  catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this.getContentPane(), "Flight not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
		if (flight.getPassengers().size() == 0) {
            JOptionPane.showMessageDialog(this.getContentPane(), "No passengers found for flight", "Info", JOptionPane.INFORMATION_MESSAGE);

		} else {
			// Pop-up set-up
			setSize(400,350);
			setTitle("Flight " + flight.getFlightNumber() + " Passengers");
			// Set-up table header + table data
	        String[] columns = new String[]{"Customer Name","Customer Phone", "Customer E-Mail"};
	        Object[][] data = new Object[flight.getPassengers().size()][3];
	        
	        for (int i = 0; i < flight.getPassengers().size(); i++) {
	            Customer customer = flight.getPassengers().get(i);
	            data[i][0] = customer.getName();
	            data[i][1] = customer.getPhone();
	            data[i][2] = customer.getEmail();
	        }
	        
	        JTable passengerTable = new JTable(data, columns) {
	            private static final long serialVersionUID = 1L;
	
	            public boolean isCellEditable(int row, int column) {                
	                    return false;               
	            };  
	        };
	        
	        // Add to panel
			this.getContentPane().add(passengerTable.getTableHeader(), BorderLayout.NORTH);
			this.getContentPane().add(passengerTable, BorderLayout.CENTER);
			setLocationRelativeTo(mainWindow);
			setVisible(true);
		}
        

	}
}
