package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

import java.time.LocalDate;
/**
 * Команда report. Генерира справка за продадените билети в период, с опционален филтър по зала.
 */
public class ReportCommand implements Command {
    /**
     * Преобразува периода и по избор номера на залата, след което генерира справка за продажбите.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length != 3 && args.length != 4) {
            context.getPrinter().printMessage("Usage: report <from> <to> [<hall>]");
            return;
        }

        LocalDate from = LocalDate.parse(args[1]);
        LocalDate to = LocalDate.parse(args[2]);

        if (args.length == 4) {
            int hall = Integer.parseInt(args[3]);
            context.getTicketSystem().report(from, to, hall);
        } else {
            context.getTicketSystem().report(from, to);
        }
    }
}
