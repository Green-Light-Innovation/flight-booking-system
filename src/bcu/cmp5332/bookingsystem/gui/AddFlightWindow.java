package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Flight;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
/**
 * GUI pop-up used to add new {@link Flight}s to the {@link FlightBookingSystem}
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class AddFlightWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField flightNoText = new JTextField();
    private JTextField originText = new JTextField();
    private JTextField destinationText = new JTextField();
    private JTextField depDateText = new JTextField();
    private JTextField passengerCapacityText = new JTextField();
    private JTextField priceText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");
    
	/**
	 * Initialize the window
	 * @param mw as {@link MainWindow}
	 */
    public AddFlightWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

	/**
	 * Create all the GUI elements and add them to the {@link JFrame}
	 */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Add a New Flight");

        setSize(350, 220);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(7, 2));
        topPanel.add(new JLabel("Flight No : "));
        topPanel.add(flightNoText);
        topPanel.add(new JLabel("Origin : "));
        topPanel.add(originText);
        topPanel.add(new JLabel("Destination : "));
        topPanel.add(destinationText);
        topPanel.add(new JLabel("Departure Date (YYYY-MM-DD) : "));
        topPanel.add(depDateText);
        topPanel.add(new JLabel("Passenger Capacity : "));
        topPanel.add(passengerCapacityText);
        topPanel.add(new JLabel("Price : "));
        topPanel.add(priceText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addFlight();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }
    
	/**
	 * Adds new Flight
	 * <p>Uses entered data and the {@link AddFlight} command to add a new {@link Flight} to the {@link FlightBookingSystem} 
	 */
    private void addFlight() {
        try {
            String flightNumber = flightNoText.getText();
            String origin = originText.getText();
            String destination = destinationText.getText();
            LocalDate departureDate = null;
            int passengerCapacity = 0;
            double price = 0;
            try {
                departureDate = LocalDate.parse(depDateText.getText());
                passengerCapacity = Integer.parseInt(passengerCapacityText.getText());
                price = Double.parseDouble(priceText.getText());
            }
            catch (DateTimeParseException dtpe) {
                throw new FlightBookingSystemException("Date must be in YYYY-DD-MM format");
            }
            // create and execute the AddFlight Command
            Command addFlight = new AddFlight(flightNumber, origin, destination, departureDate, passengerCapacity, price);
            addFlight.execute(mw.getFlightBookingSystem());
            // refresh the view with the list of flights
            mw.displayFlights();
            // hide (close) the AddFlightWindow
            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
