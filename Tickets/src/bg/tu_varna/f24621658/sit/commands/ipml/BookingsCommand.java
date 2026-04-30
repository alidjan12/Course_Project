package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BookingsCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length < 2 || args.length > 3) {
            System.out.println("Usage: bookings <date> [<name>] OR bookings <name>");
            return;
        }

        try {
            LocalDate date = LocalDate.parse(args[1]);

            if (args.length == 3) {
                context.getTicketSystem().bookings(date, args[2]);
            } else {
                context.getTicketSystem().bookings(date);
            }

        } catch (DateTimeParseException e) {
            String name = args[1];
            context.getTicketSystem().bookings(name);
        }
    }
}
