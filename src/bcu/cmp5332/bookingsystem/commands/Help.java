package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * {@link Command} class that prints out a list of commands
 * @author Jack Atkins
 * @author Daniel Jukes
 */
public class Help implements Command {

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) {
        System.out.println(Command.HELP_MESSAGE);
    }
}
