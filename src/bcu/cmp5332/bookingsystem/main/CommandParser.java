package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CommandParser {
    
    public static Command parse(String line) throws IOException, FlightBookingSystemException {
    	
    	String[] parts = line.split(" ", 3);
    	String cmd = parts[0];
    	
    	// Commands with no arguments
    	if (parts.length == 1) {
    		if (cmd.equals("help")) {
    			return new Help();
    		}
    		
    		else if (cmd.equals("exit")) {
    			return new Exit();
    		}
    		
    		else if (cmd.equals("listflights")) {
    			return new ListFlights();
    		}
    		
    		else if (cmd.equals("listcustomers")) {
    			return new ListCustomers();
    		}
    		
    		else if (cmd.equals("addflight")) {
    			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Flight Number: ");
                String flighNumber = reader.readLine();
                System.out.print("Origin: ");
                String origin = reader.readLine();
                System.out.print("Destination: ");
                String destination = reader.readLine();

                LocalDate departureDate = parseDateWithAttempts(reader);

                return new AddFlight(flighNumber, origin, destination, departureDate);
    		}
    		
    		else if (cmd.equals("addcustomer")) {
    			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    			System.out.print("Customer Name: ");
    			String name = reader.readLine();
    			System.out.println("Phone Number: ");
    			String phone = reader.readLine();
    			System.out.println("Email: ");
    			String email = reader.readLine();
    			return new AddCustomer(name, phone, email);
    		}
    		
    		else if (cmd.equals("loadgui")) {
    			return new LoadGUI();
    		}
    	}
    	
    	// Commands with 1 argument
    	else if (parts.length == 2) {
    		if (cmd.equals("showflight")) {
    			try {
    				Integer.parseInt(parts[1]);
    			} catch (NumberFormatException ex) {
    				throw new FlightBookingSystemException("'" + parts[1] + "' is not a valid flight ID");
    			}
    			
    			int id = Integer.parseInt(parts[1]);
    			return new ShowFlight(id);
    		}
    		
    		else if(cmd.equals("showcustomer")) {
    			try {
    				Integer.parseInt(parts[1]);
    			} catch (NumberFormatException ex) {
    				throw new FlightBookingSystemException("'" + parts[1] + "' is not a valid customer ID");
    			}
    			
    			int id = Integer.parseInt(parts[1]);
    			return new ShowCustomer(id);
    		}
    	}
    	
    	// Commands with 2 arguments
    	else if (parts.length == 3) {
    		if (cmd.equals("addbooking")) {
    			try {
    				Integer.parseInt(parts[1]);
    				Integer.parseInt(parts[2]);
    			} catch(NumberFormatException ex) {
    				throw new FlightBookingSystemException("An invalid ID has been entered");
    			}
    			
    			int customerID = Integer.parseInt(parts[1]);
    			int flightID = Integer.parseInt(parts[2]);
    			
    			return new AddBooking(customerID, flightID);
    		}
    		
    		else if(cmd.equals("cancelbooking")) {
    			try {
    				Integer.parseInt(parts[1]);
    				Integer.parseInt(parts[2]);
    			} catch(NumberFormatException ex) {
    				throw new FlightBookingSystemException("An invalid ID has been entered");
    			}
    			
    			int customerID = Integer.parseInt(parts[1]);
    			int flightID = Integer.parseInt(parts[2]);
    			
    			return new CancelBooking(customerID, flightID);
    		}
    		
    		else if(cmd.equals("editbooking")) {
    			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    			System.out.print("Enter ID of new flight: ");
    			String flightIDString = reader.readLine();
    			
    			try {
    				Integer.parseInt(parts[1]);
    				Integer.parseInt(parts[2]);
    				Integer.parseInt(flightIDString);
    			} catch (NumberFormatException ex) {
    				throw new FlightBookingSystemException("'" + flightIDString + "' is not a valid flight ID.");
    			}
    			
    			int customerID = Integer.parseInt(parts[1]);
				int flightID = Integer.parseInt(parts[2]);
    			int newFlightID = Integer.parseInt(flightIDString);
    			
    			return new EditBooking(customerID, flightID, newFlightID);
    		}
    	}
    	
    	throw new FlightBookingSystemException("Invalid Command.");
    }
    
    private static LocalDate parseDateWithAttempts(BufferedReader br, int attempts) throws IOException, FlightBookingSystemException {
        if (attempts < 1) {
            throw new IllegalArgumentException("Number of attempts should be higher that 0");
        }
        while (attempts > 0) {
            attempts--;
            System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
            try {
                LocalDate departureDate = LocalDate.parse(br.readLine());
                return departureDate;
            } catch (DateTimeParseException dtpe) {
                System.out.println("Date must be in YYYY-MM-DD format. " + attempts + " attempts remaining...");
            }
        }
        
        throw new FlightBookingSystemException("Incorrect departure date provided. Cannot create flight.");
    }
    
    private static LocalDate parseDateWithAttempts(BufferedReader br) throws IOException, FlightBookingSystemException {
        return parseDateWithAttempts(br, 3);
    }
}
