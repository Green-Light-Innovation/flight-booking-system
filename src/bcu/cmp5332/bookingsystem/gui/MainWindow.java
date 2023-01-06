package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.commands.Exit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;

/**
 * Main window used as basis for GUI
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu flightsMenu;
    private JMenu bookingsMenu;
    private JMenu customersMenu;

    private JMenuItem adminExit;

    private JMenuItem flightsView;
    private JMenuItem flightsAdd;
    private JMenuItem flightsDel;
    
    private JTable flightTable;
    private JButton viewPassengers;
    
    private JMenuItem bookingsIssue;
    private JMenuItem bookingsUpdate;
    private JMenuItem bookingsCancel;

    private JMenuItem custView;
    private JMenuItem custAdd;
    private JMenuItem custDel;
    
    private JTable customerTable;
    private JButton viewBookings;
    

    private FlightBookingSystem fbs;

	/**
	 * Initialize the window
	 * @param fbs as {@link FlightBookingSystem}
	 */
    public MainWindow(FlightBookingSystem fbs) {

        initialize();
        this.fbs = fbs;
    }
    /**
     * 
     * @return {@link FlightBookingSystem}
     */
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

	/**
	 * Create all the GUI elements and add them to the {@link JFrame}
	 */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Flight Booking Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding Flights menu and menu items
        flightsMenu = new JMenu("Flights");
        menuBar.add(flightsMenu);

        flightsView = new JMenuItem("View");
        flightsAdd = new JMenuItem("Add");
        flightsDel = new JMenuItem("Delete");
        flightsMenu.add(flightsView);
        flightsMenu.add(flightsAdd);
        flightsMenu.add(flightsDel);
        // adding action listener for Flights menu items
        for (int i = 0; i < flightsMenu.getItemCount(); i++) {
            flightsMenu.getItem(i).addActionListener(this);
        }
        
        // adding Bookings menu and menu items
        bookingsMenu = new JMenu("Bookings");
        menuBar.add(bookingsMenu);
        
        bookingsIssue = new JMenuItem("Issue");
        bookingsUpdate = new JMenuItem("Update");
        bookingsCancel = new JMenuItem("Cancel");
        bookingsMenu.add(bookingsIssue);
        bookingsMenu.add(bookingsUpdate);
        bookingsMenu.add(bookingsCancel);
        // adding action listener for Bookings menu items
        for (int i = 0; i < bookingsMenu.getItemCount(); i++) {
            bookingsMenu.getItem(i).addActionListener(this);
        }

        // adding Customers menu and menu items
        customersMenu = new JMenu("Customers");
        menuBar.add(customersMenu);

        custView = new JMenuItem("View");
        custAdd = new JMenuItem("Add");
        custDel = new JMenuItem("Delete");

        customersMenu.add(custView);
        customersMenu.add(custAdd);
        customersMenu.add(custDel);
        // adding action listener for Customers menu items
        custView.addActionListener(this);
        custAdd.addActionListener(this);
        custDel.addActionListener(this);

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
//    public static void main(String[] args) throws IOException, FlightBookingSystemException {
//        FlightBookingSystem fbs = FlightBookingSystemData.load();
//        new MainWindow(fbs);			
//    }



    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            try {
                new Exit().execute(fbs);
            } catch (FlightBookingSystemException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        } else if (ae.getSource() == flightsView) {
            displayFlights();
            
        } else if (ae.getSource() == flightsAdd) {
            new AddFlightWindow(this);
            
        } else if (ae.getSource() == flightsDel) {
        	//TODO 70-79%
        	
        } else if (ae.getSource() == bookingsIssue) {
        	new IssueBookingWindow(this);
            
        } else if (ae.getSource() == bookingsCancel) {
        	new CancelBookingWindow(this);
            
        } else if (ae.getSource() == custView) {
            displayCustomers();
            
        } else if (ae.getSource() == custAdd) {
            new AddCustomerWindow(this);
            
        } else if (ae.getSource() == custDel) {
        	//TODO 70-79%
        } else if (ae.getSource() == viewPassengers) {
        	try {
        		int row = flightTable.getSelectedRow();
            	int flightId = (int) flightTable.getModel().getValueAt(row, 0);
            	new PassengersWindow(this, flightId);
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this.getContentPane(), "No flight selected", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        	
        } else if (ae.getSource() == viewBookings) {
        	try {
        		int row = customerTable.getSelectedRow();
            	int customerId = (int) customerTable.getModel().getValueAt(row, 0);
            	new BookingsWindow(this, customerId);
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this.getContentPane(), "No customer selected", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
	/**
	 * Displays all non-cancelled {@link Flight}s
	 */
    public void displayFlights() {
        List<Flight> flightsList = fbs.getCurrentFlights();
        // Button for showing flight passengers
        viewPassengers = new JButton("View Passengers");
        viewPassengers.addActionListener(this);
        
        // headers for the table
        String[] columns = new String[]{"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Capacity", "Price"};

        Object[][] data = new Object[flightsList.size()][7];
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
            data[i][0] = flight.getId();
            data[i][1] = flight.getFlightNumber();
            data[i][2] = flight.getOrigin();
            data[i][3] = flight.getDestination();
            data[i][4] = flight.getDepartureDate();
            data[i][5] = flight.getPassengerCapacity();
            data[i][6] = flight.getPrice();
        }

        flightTable = new JTable(data, columns) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                    return false;               
            };
        };
        flightTable.setRowSelectionAllowed(true);
        // Hide ID Column
        flightTable.removeColumn(flightTable.getColumnModel().getColumn(0));
        this.getContentPane().removeAll();
        this.getContentPane().add(flightTable.getTableHeader(), BorderLayout.NORTH);
        this.getContentPane().add(flightTable, BorderLayout.CENTER);
        this.getContentPane().add(viewPassengers, BorderLayout.SOUTH);
        this.revalidate();
    }
    
	/**
	 * Displays all current {@link Customer}s
	 */
    public void displayCustomers() {
    	List<Customer> customersList = fbs.getCurrentCustomers();
        // Button for showing Customer bookings
        viewBookings = new JButton("View Bookings");
        viewBookings.addActionListener(this);
    	// Header for the table
    	String[] columns = new String[] {"ID","Name", "Phone Number", "Email","No. Bookings"};
    	Object[][] data = new Object[customersList.size()][5];
    	
    	for (int i = 0; i < customersList.size(); i++) {
    		Customer customer = customersList.get(i);
    		data[i][0] = customer.getID();
    		data[i][1] = customer.getName();
    		data[i][2] = customer.getPhone();
    		data[i][3] = customer.getEmail();
    		data[i][4] = customer.getBookings().size();
    	}
    	
    	customerTable = new JTable(data, columns) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                    return false;               
            };
        };
        customerTable.setRowSelectionAllowed(true);
        // Hide ID Column
        customerTable.removeColumn(customerTable.getColumnModel().getColumn(0));
        this.getContentPane().removeAll();
        this.getContentPane().add(customerTable.getTableHeader(), BorderLayout.NORTH);
        this.getContentPane().add(customerTable, BorderLayout.CENTER);
        this.getContentPane().add(viewBookings, BorderLayout.SOUTH);
    	this.revalidate();
    }
}
