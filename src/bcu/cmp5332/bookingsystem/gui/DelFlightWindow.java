package bcu.cmp5332.bookingsystem.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * GUI pop-up class used by the user to remove {@link Flight}s from view, and mark them as removed
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class DelFlightWindow extends JFrame implements ActionListener {
	private MainWindow mainWindow;
	private FlightBookingSystem flightBookingSystem;
	
	private JTable delFlightTable;
    private JButton delButton;
	
    List<Flight> flightsList;
    private Flight selectedFlight;
	
	
	/**
	 * Initialize the window
	 * @param mainWindow as {@link MainWindow}
	 */
	public DelFlightWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.flightBookingSystem = mainWindow.getFlightBookingSystem();
		initialize();
	}
	
	/**
	 * Create all the GUI elements and add them to the {@link JFrame}
	 */
	private void initialize() {
		setSize(600,300);
		// Don't allow interaction w deleted flights, so use GetCurrent
		flightsList = flightBookingSystem.getCurrentFlights();
		
        // Button for deleting flight
        delButton = new JButton("Delete");
        delButton.addActionListener(this);
        
        // Headers for the table
        String[] columns = new String[]{"Flight ID", "Flight No","Origin","Destination"};
        
        // Uses ID to avoid issues with using index values
        Object[][] data = new Object[flightsList.size()][4];
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
            data[i][0] = flight.getId();
            data[i][1] = flight.getFlightNumber();
            data[i][2] = flight.getOrigin();
            data[i][3] = flight.getDestination();
        }
        
        
        delFlightTable = new JTable(data, columns) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                    return false;               
            };
        };
        delFlightTable.setRowSelectionAllowed(true);
        // Hide ID Column
        delFlightTable.removeColumn(delFlightTable.getColumnModel().getColumn(0));
        
        getContentPane().add(delFlightTable.getTableHeader(), BorderLayout.NORTH);
        getContentPane().add(delFlightTable, BorderLayout.CENTER);
        getContentPane().add(delButton, BorderLayout.SOUTH);
        
		setLocationRelativeTo(mainWindow);
		setVisible(true);
        
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == delButton) {
			try {
        		int row = delFlightTable.getSelectedRow();
            	int flightId = (int) delFlightTable.getModel().getValueAt(row, 0);
            	selectedFlight = flightBookingSystem.getFlightByID(flightId);
            	// Set removed attribute
            	selectedFlight.setRemoved(true);
            	flightsList = flightBookingSystem.getCurrentFlights();
            	// Re-draw updated table
            	delFlightTable.revalidate();
    			try {
    				FlightBookingSystemData.store(flightBookingSystem);
                	JOptionPane.showMessageDialog(this.getContentPane(), "Flight removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

    			} catch (IOException exc) {
    				selectedFlight.setRemoved(false);
    				throw new FlightBookingSystemException("Flight could not be removed, rolling back changes...\nError: " + exc);
    			}
            
			} catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this.getContentPane(), "No flight selected", "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (FlightBookingSystemException ex) {
                JOptionPane.showMessageDialog(this.getContentPane(), "Flight not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
		}
	}
	
}
