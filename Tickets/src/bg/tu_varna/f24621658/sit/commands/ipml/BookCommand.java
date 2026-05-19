package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

import java.time.LocalDate;

/**
 * Команда book. Намира представление по дата и име, отделя бележката от входа и резервира избраното място.
 */
public class BookCommand implements Command {
    /**
     * Проверява входа, намира къде свършва името на представлението и къде започва бележката, след което резервира мястото.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length < 6) {
            context.getPrinter().printMessage("Употреба: book <ред> <място> <дата> <име> <бележка>");
            return;
        }

        int row;
        int seat;
        LocalDate date;

        try {
            row = Integer.parseInt(args[1]);
            seat = Integer.parseInt(args[2]);
            date = LocalDate.parse(args[3]);
        } catch (NumberFormatException e) {
            context.getPrinter().printMessage("Грешка: редът и мястото трябва да са числа.");
            return;
        } catch (Exception e) {
            context.getPrinter().printMessage("Грешка: датата трябва да е във формат yyyy-mm-dd.");
            return;
        }

        String foundName = null;
        String note = null;

        // Пробва различни дължини на името, защото и името на представлението, и бележката могат да съдържат интервали.
        for (int endOfName = args.length - 1; endOfName >= 5; endOfName--) {
            String possibleName = CommandUtils.joinArguments(args, 4, endOfName);

            if (context.getTicketSystem().eventExists(date, possibleName)) {
                foundName = possibleName;
                note = CommandUtils.joinArguments(args, endOfName);
                break;
            }
        }
        // Ако не е намерено представление, целият текст след датата се използва за търсене на най-близко съществуващо име.
        if (foundName == null) {
            String allTextAfterDate = CommandUtils.joinArguments(args, 4);

            if (context.getTicketSystem().eventExists(date, allTextAfterDate)) {
                context.getPrinter().printMessage("Грешка: Липсва бележка за резервацията.");
                context.getPrinter().printMessage("Употреба: book <ред> <място> <дата> <име> <бележка>");
                return;
            }

            context.getPrinter().printClosestEventMessage(context, date, allTextAfterDate);
            return;
        }

        if (note.isBlank()) {
            context.getPrinter().printMessage("Грешка: Липсва бележка за резервацията.");
            context.getPrinter().printMessage("Употреба: book <ред> <място> <дата> <име> <бележка>");
            return;
        }

        context.getTicketSystem().book(row, seat, date, foundName, note);
        context.getPrinter().printMessage("Успешно запазен билет.");
    }
}