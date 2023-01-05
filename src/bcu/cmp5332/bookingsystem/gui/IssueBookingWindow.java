package bcu.cmp5332.bookingsystem.gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import bcu.cmp5332.bookingsystem.model.*;
import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

/**
 * GUI pop-up used to add {@link Booking}s to the {@link FlightBookingSystem}
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class IssueBookingWindow extends JFrame implements ActionListener {
	
	private MainWindow mainWindow;
	
	private FlightBookingSystem flightBookingSystem;
	
	private JComboBox<String> customerSelection;
	private JComboBox<String> flightSelection;
	
	private JButton submitButton;
	private JButton cancelButton;
	
	/**
	 * Initialize the window
	 * @param mainWindow as {@link MainWindow}
	 */
	public IssueBookingWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.flightBookingSystem = mainWindow.getFlightBookingSystem();
		initialize();
	}
	
	/**
	 * Create all the GUI elements and add them to the {@link JFrame}
	 */
	private void initialize() {
		setSize(300,200);
		setTitle("Issue Booking");
		
		DefaultComboBoxModel<String> customerEmails = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<String> flightNumbers = new DefaultComboBoxModel<>();
		
		flightBookingSystem.getCustomers().forEach(customer -> {
			customerEmails.addElement(customer.getEmail());
		});
		
		flightBookingSystem.getFlights().forEach(flight -> {
			flightNumbers.addElement(flight.getFlightNumber());
		});
		
		customerSelection = new JComboBox<>(customerEmails);
		flightSelection = new JComboBox<>(flightNumbers);
		
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		submitButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2,2));
		
		topPanel.add(new JLabel("Customer"));
		topPanel.add(new JLabel("Flight"));
		topPanel.add(customerSelection);
		topPanel.add(flightSelection);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3));
		bottomPanel.add(cancelButton);
		bottomPanel.add(new JLabel("    "));
		bottomPanel.add(submitButton);
		
		this.getContentPane().add(topPanel, BorderLayout.NORTH);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		setLocationRelativeTo(mainWindow);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton) {
			addBooking();
		}
		else if (e.getSource() == cancelButton) {
			this.dispose();
		}
	}
	
	/**
	 * Fetches flight and customer data
	 * <p>Uses the {@link AddBooking} command to add the booking to the {@link FlightBookingSystem} 
	 */
	private void addBooking() {
		// Fetch customers and flights using the combo boxes getSelectedIndex method
		Customer customer = flightBookingSystem.getCustomers().get( customerSelection.getSelectedIndex() );
		Flight flight = flightBookingSystem.getFlights().get( flightSelection.getSelectedIndex() );
		
		try {
			new AddBooking(customer.getID(), flight.getId()).execute(flightBookingSystem);
			JOptionPane.showMessageDialog(this, ("Booking for " + customer.getName() + "\nfor flight: " + flight.getFlightNumber() + " has been successfully made"), "Booking Created", JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		} catch(FlightBookingSystemException ex) {
			JOptionPane.showMessageDialog(this, ex, "Booking Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
