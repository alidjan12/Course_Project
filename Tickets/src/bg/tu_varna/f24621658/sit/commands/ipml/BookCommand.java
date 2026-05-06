package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

import java.time.LocalDate;

public class BookCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length < 6) {
            context.getPrinter().printMessage("Употреба: book <ред> <място> <дата> <име> <бележка>");
            return;
        }

        int row = Integer.parseInt(args[1]);
        int seat = Integer.parseInt(args[2]);
        LocalDate date = LocalDate.parse(args[3]);

        String foundName = null;
        String note = null;

        for (int endOfName = args.length - 1; endOfName >= 5; endOfName--) {
            String possibleName = CommandUtils.joinArguments(args, 4, endOfName);

            if (context.getTicketSystem().eventExists(date, possibleName)) {
                foundName = possibleName;
                note = CommandUtils.joinArguments(args, endOfName);
                break;
            }
        }

        if (foundName == null) {
            String allTextAfterDate = CommandUtils.joinArguments(args, 4);

            if (context.getTicketSystem().eventExists(date, allTextAfterDate)) {
                context.getPrinter().printMessage("Грешка: Липсва бележка за резервацията.");
                context.getPrinter().printMessage("Употреба: book <ред> <място> <дата> <име> <бележка>");
                return;
            }

            String closestName = context.getTicketSystem().findClosestEventName(date, allTextAfterDate);

            if (closestName != null) {
                context.getPrinter().printMessage("Няма такова представление. Най-близко намерено: " + closestName);
            } else {
                context.getPrinter().printMessage("Няма представления за дата " + date + ".");
            }

            return;
        }

        context.getTicketSystem().book(row, seat, date, foundName, note);
        context.getPrinter().printMessage("Успешно запазен билет.");
    }
}