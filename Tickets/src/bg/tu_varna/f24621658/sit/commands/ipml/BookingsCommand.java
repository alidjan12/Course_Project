package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BookingsCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length < 2) {
            context.getPrinter().printMessage("Usage: bookings <date> [<name>] OR bookings <name>");
            return;
        }

        try {
            LocalDate date = LocalDate.parse(args[1]);

            if (args.length >= 3) {
                String name = CommandUtils.joinArguments(args, 2);
                context.getTicketSystem().bookings(date, name);
            } else {
                context.getTicketSystem().bookings(date);
            }

        } catch (DateTimeParseException e) {
            String name = CommandUtils.joinArguments(args, 1);
            context.getTicketSystem().bookings(name);
        }
    }
}
