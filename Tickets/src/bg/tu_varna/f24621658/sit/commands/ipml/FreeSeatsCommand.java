package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

import java.time.LocalDate;

/**
 * Команда freeseats. Показва свободните места за представление по дата и име.
 */
public class FreeSeatsCommand implements Command {
    /**
     * Преобразува датата, сглобява името на представлението и извежда свободните места.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        if(args.length <3){
            context.getPrinter().printMessage("Usage: freeseats <date> <name>");
            return;
        }
        LocalDate date = LocalDate.parse(args[1]);
        String name = CommandUtils.joinArguments(args ,2);
        context.getTicketSystem().freeSeats(date, name);
    }
}
