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
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * GUI pop-up class used by the user to remove {@link Customer}s from view, and mark them as removed
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class DelCustomerWindow extends JFrame implements ActionListener {
	private MainWindow mainWindow;
	private FlightBookingSystem flightBookingSystem;
	
	private JTable delCustomerTable;
    private JButton delButton;
	
    List<Customer> customerList;
    private Customer selectedCustomer;
	
	
	/**
	 * Initialize the window
	 * @param mainWindow as {@link MainWindow}
	 */
	public DelCustomerWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.flightBookingSystem = mainWindow.getFlightBookingSystem();
		initialize();
	}
	
	/**
	 * Create all the GUI elements and add them to the {@link JFrame}
	 */
	private void initialize() {
		setSize(300,400);
		// Don't allow interaction w deleted flights, so use GetCurrent
		customerList = flightBookingSystem.getCurrentCustomers();
		
        // Button for deleting flight
        delButton = new JButton("Delete");
        delButton.addActionListener(this);
        
        // Headers for the table
        String[] columns = new String[]{"Customer ID", "Name"};
        
        // Uses ID to avoid issues with using index values
        Object[][] data = new Object[customerList.size()][2];
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            data[i][0] = customer.getID();
            data[i][1] = customer.getName();
        }
        
        
        delCustomerTable = new JTable(data, columns) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                    return false;               
            };
        };
        delCustomerTable.setRowSelectionAllowed(true);
        // Hide ID Column
        delCustomerTable.removeColumn(delCustomerTable.getColumnModel().getColumn(0));
        
        getContentPane().add(delCustomerTable.getTableHeader(), BorderLayout.NORTH);
        getContentPane().add(delCustomerTable, BorderLayout.CENTER);
        getContentPane().add(delButton, BorderLayout.SOUTH);
        
		setLocationRelativeTo(mainWindow);
		setVisible(true);
        
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == delButton) {
			try {
        		int row = delCustomerTable.getSelectedRow();
            	int flightId = (int) delCustomerTable.getModel().getValueAt(row, 0);
            	selectedCustomer = flightBookingSystem.getCustomerByID(flightId);
            	// Set removed attribute
            	selectedCustomer.setRemoved(true);
            	customerList = flightBookingSystem.getCurrentCustomers();
            	// Re-draw updated table
            	delCustomerTable.revalidate();
    			try {
    				FlightBookingSystemData.store(flightBookingSystem);
                	JOptionPane.showMessageDialog(this.getContentPane(), "Customer removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

    			} catch (IOException exc) {
    				selectedCustomer.setRemoved(false);
    				throw new FlightBookingSystemException("Customer could not be removed, rolling back changes...\nError: " + exc);
    			}
            
			} catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this.getContentPane(), "No Customer selected", "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (FlightBookingSystemException ex) {
                JOptionPane.showMessageDialog(this.getContentPane(), "Customer not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
		}
	}
	
}