package bcu.cmp5332.bookingsystem.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;

public class CancelBookingWindow extends JFrame implements ActionListener {
	
	private MainWindow mainWindow;
	private FlightBookingSystem flightBookingSystem;
	
	private JComboBox<String> customerSelection;
	private JList<String> flightsList;
	private JButton submitButton;
	
	private Customer selectedCustomer;
	
	public CancelBookingWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.flightBookingSystem = mainWindow.getFlightBookingSystem();
		initialize();
	}
	
	private void initialize() {
		setSize(300,300);
		
		customerSelection = new JComboBox<>();
		loadCustomerData();
		customerSelection.setSelectedIndex(0);
		customerSelection.addActionListener(this);
		getContentPane().add(customerSelection, BorderLayout.NORTH);
		
		flightsList = new JList<>();
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(flightsList, BorderLayout.CENTER);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		getContentPane().add(submitButton, BorderLayout.SOUTH);

		loadCustomerBookings();
		
		setLocationRelativeTo(mainWindow);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == customerSelection) {
			loadCustomerBookings();
		}
		else if (e.getSource() == submitButton) {
			System.out.println("Called");
			try {
				cancelBooking();
				loadCustomerBookings();
			} catch (FlightBookingSystemException ex) {
				JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private Customer getSelectedCustomer() throws FlightBookingSystemException {
		return flightBookingSystem.getCustomers().get( customerSelection.getSelectedIndex() );
	}
	
	private void loadCustomerData() {
		flightBookingSystem.getCustomers().forEach(customer -> {
			customerSelection.addItem(customer.getEmail());
		});
	}
	
	private void loadCustomerBookings() {
		try {
			// Fetch customer from system
			selectedCustomer = getSelectedCustomer();
			
			// Go through each booking and add flight info to an array
			
			if(selectedCustomer.getBookings().size() < 1) {
				submitButton.setEnabled(false);
			} else {
				submitButton.setEnabled(true);
			}
			
			String[] flights = new String[selectedCustomer.getBookings().size()];
			int idx = 0;
			for(Booking booking : selectedCustomer.getBookings()) {
				flights[idx] = booking.getFlight().getFlightNumber() + " - " + booking.getFlight().getDepartureDate().toString();
				idx++;
			}
			
			// Add array to flightsList
			flightsList.setListData(flights);
			
			
		} catch (FlightBookingSystemException ex) {
			JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cancelBooking() throws FlightBookingSystemException {
		// Get the selected flight
		int selectedFlight = flightsList.getSelectedIndex();
		Flight flight = selectedCustomer.getBookings().get(selectedFlight).getFlight();
		
		// remove customer from flight
		flight.removePassenger(selectedCustomer);
		
		// remove booking from customer
		flightBookingSystem.getCustomerByID(selectedCustomer.getID()).cancelBooking(flight);
	}
}
