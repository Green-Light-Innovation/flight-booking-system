package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

/**
 * Class model that represents a booking made in the system
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class Booking {
    //TODO Add booking status?
    private Customer customer;
    private Flight flight;
    private LocalDate bookingDate;
    
    /**
     * @param customer as {@code Customer}
     * @param flight {@code Flight}
     * @param bookingDate {@code LocalDate}
     */
    public Booking(Customer customer, Flight flight, LocalDate bookingDate) {
        this.customer = customer;
        this.flight = flight;
        this.bookingDate = bookingDate;
    }
    
    // Getters
    
    /**
     * @return the customer that made the booking
     */
    public Customer getCustomer() { return customer; }
    
    /**
     * @return the flight that the booking was made for
     */
    public Flight getFlight() { return flight; }
    
    /** 
     * @return the date that the booking was made
     */
    public LocalDate getBookingDate() { return bookingDate; }
    
    // Setters
    
    /**
     * Set the customer who created the booking
     * @param customer as {@code Customer}
     */
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    /**
     * Set the flight that the booking is for
     * @param flight as {@code Flight}
     */
    public void setFlight(Flight flight) { this.flight = flight; }
    
    /**
     * Set the date that the booking was made
     * @param date as {@code LocalDate}
     */
    public void setBookingDate(LocalDate date) { this.bookingDate = date; }
}
