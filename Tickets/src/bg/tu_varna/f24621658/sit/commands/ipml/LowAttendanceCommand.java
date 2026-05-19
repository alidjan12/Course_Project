package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

import java.time.LocalDate;
/**
 * Команда lowattendance. Извежда представленията с посещаемост под 10% за подаден период.
 */
public class LowAttendanceCommand implements Command {
    /**
     * Проверява аргументите, преобразува начална и крайна дата и извежда слабо посетените представления.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length != 3) {
            context.getPrinter().printMessage("Usage: lowattendance <from> <to>");
            return;
        }

        LocalDate from = LocalDate.parse(args[1]);
        LocalDate to = LocalDate.parse(args[2]);

        context.getTicketSystem().lowAttendance(from, to);
    }
}
