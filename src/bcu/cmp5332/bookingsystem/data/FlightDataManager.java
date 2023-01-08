package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

/** 
 * Handles loading {@link Flight}s into and out of the system from bookings.txt
 * @author Daniel Jukes
 * @author Jack Atkins
 *
 */
public class FlightDataManager implements DataManager {
    
    private final String RESOURCE;
    
    /**
     * Create a {@link BookingDataManager} object with the default resources location
     */
    public FlightDataManager() {
    	this.RESOURCE = "./resources/data/flights.txt";
    }
    /**
     * Create a {@link BookingDataManager} object with a custom resources location
     * <p> used for testing
     * @param resource as resources location
     */
    public FlightDataManager(String resource) {
    	this.RESOURCE = resource;
    }
    /**
     * Opens flights.txt, parses individual flights, saving them to a FlightBookingSystem object
     *@param fbs as {@link FlightBookingSystem} to load {@link Flight}s into
     *@throws IOException if there is a file access error
     *@throws FlightBookingSystemException if there is an error parsing flight data
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
        		// Properties has length of 6 due to trailing ::
        		if (properties.length != 9) {
        			throw new FlightBookingSystemException("Unable to parse Booking ID " + properties[0] + " on line: " + line_idx + "\nMalformed Data");
        		}
        		
                try {
                    int id = Integer.parseInt(properties[0]);
                    String flightNumber = properties[1];
                    String origin = properties[2];
                    String destination = properties[3];
                    LocalDate departureDate = LocalDate.parse(properties[4]);
                    int passengerCapacity = Integer.parseInt(properties[5]);
                    double price = Double.parseDouble(properties[6]);
                    boolean isRemoved = Boolean.parseBoolean(properties[7]);
                    
                    Flight flight = new Flight(id, flightNumber, origin, destination, departureDate, passengerCapacity, price);
                    flight.setRemoved(isRemoved);
                    fbs.addFlight(flight);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse booking id " + properties[0] + " on line " + line_idx + "\nError: " + ex);
                } catch (IndexOutOfBoundsException ex) {
                    throw new FlightBookingSystemException("Unable to parse booking id " + properties[0] + " on line " + line_idx + "\nError: " + ex);
        		}
                line_idx++;
            }
        }
    }
    /**
     * Reads flight data from a {@link FlightBookingSystem} object, 
     * parsing each contained flight into a store-able format, writing bookings to flights.txt
     *@param fbs as {@link FlightBookingSystem} to load {@link Flight}s into
     *@throws IOException if there is a file access error
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
        	// Save ALL flights, including removed ones
            for (Flight flight : fbs.getFlights()) {
                out.print(flight.getId() + SEPARATOR);
                out.print(flight.getFlightNumber() + SEPARATOR);
                out.print(flight.getOrigin() + SEPARATOR);
                out.print(flight.getDestination() + SEPARATOR);
                out.print(flight.getDepartureDate() + SEPARATOR);
                out.print(flight.getPassengerCapacity() + SEPARATOR);
                out.print(flight.getPrice() + SEPARATOR);
                out.print(flight.isRemoved() + SEPARATOR);
                out.println();
            }
        }
    }
}
