package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

import java.time.LocalDate;

/**
 * Команда unbook. Отменя резервация за конкретно място.
 */
public class UnBookCommand implements Command {
    /**
     * Преобразува ред, място и дата, проверява представлението и отменя резервацията.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length < 5) {
            context.getPrinter().printMessage("Употреба: unbook <ред> <място> <дата> <име>");
            return;
        }

        int row = Integer.parseInt(args[1]);
        int seat = Integer.parseInt(args[2]);
        LocalDate date = LocalDate.parse(args[3]);
        String name = CommandUtils.joinArguments(args, 4);

        if (!context.getTicketSystem().eventExists(date, name)) {
            context.getPrinter().printClosestEventMessage(context, date, name);
            return;
        }

        context.getTicketSystem().unbook(row, seat, date, name);
        context.getPrinter().printMessage("Успешно отменено запазване.");
    }
}