package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
/**
 * Команда bookings. Показва резервации без филтър, по дата, по име или по дата и име.
 */
public class BookingsCommand implements Command {
    /**
     * Разпознава дали първият аргумент е дата и според това извежда резервации по дата, по име, по двете или без филтър.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length == 1) {
            context.getTicketSystem().bookings();
            return;
        }

        try {
            LocalDate date = LocalDate.parse(args[1]);

            if (args.length == 2) {
                context.getTicketSystem().bookings(date);
                return;
            }

            String name = CommandUtils.joinArguments(args, 2);
            context.getTicketSystem().bookings(date, name);

        } catch (DateTimeParseException e) {
            String name = CommandUtils.joinArguments(args, 1);
            context.getTicketSystem().bookings(name);
        }
    }
}