package bcu.cmp5332.bookingsystem.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.model.Customer;

/**
 * GUI pop-up used to add new {@link Customer}s to the {@link FlightBookingSystem}
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class AddCustomerWindow extends JFrame implements ActionListener {
	
	private MainWindow mainWindow;
	
	private JTextField customerNameField = new JTextField();
	private JTextField customerPhoneField = new JTextField();
	private JTextField customerEmailField = new JTextField();
	
	private JButton addButton = new JButton("Add");
	private JButton cancelButton = new JButton("Cancel");
	/**
	 * Initialize the window
	 * @param mainWindow as {@link MainWindow}
	 */
	public AddCustomerWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		initialize();
	}
	/**
	 * Create all the GUI elements and add them to the {@link JFrame}
	 */
	private void initialize()  {
		setTitle("Add a New Customer");
		
		setSize(350, 220);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(4,2));
		topPanel.add(new JLabel("Full Name:"));
		topPanel.add(customerNameField);
		topPanel.add(new JLabel("Phone Number:"));
		topPanel.add(customerPhoneField);
		topPanel.add(new JLabel("Email:"));
		topPanel.add(customerEmailField);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3));
		bottomPanel.add(addButton);
		bottomPanel.add(new JLabel("     "));
		bottomPanel.add(cancelButton);
		
		addButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		this.getContentPane().add(topPanel, BorderLayout.CENTER);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		setLocationRelativeTo(mainWindow);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			System.out.println("Called");
			addCustomer();
		}
		else if (e.getSource() == cancelButton) {
			this.setVisible(false);
		}
	}
	/**
	 * Adds new customer
	 * <p>Uses entered data and the {@link AddCustomer} command to add a new {@link Customer} to the {@link FlightBookingSystem} 
	 */
	private void addCustomer() {
		try {
			String name = customerNameField.getText();
			String phone = customerPhoneField.getText();
			String email = customerEmailField.getText();
			
			Command command = new AddCustomer(name, phone, email);
			command.execute(mainWindow.getFlightBookingSystem());

			mainWindow.displayCustomers();
			this.setVisible(false);
		} catch(FlightBookingSystemException ex) {
			JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
