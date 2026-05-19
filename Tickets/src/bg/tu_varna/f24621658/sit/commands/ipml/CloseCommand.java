package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

/**
 * Команда close. Затваря текущо отворения файл и изчиства заредените данни от системата.
 */
public class CloseCommand implements Command {
    /**
     * Затваря текущия файл и премахва заредените в паметта представления.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        try {
            context.getFileService().close();
            context.getTicketSystem().clear();
        } catch (IllegalStateException e) {
            context.getPrinter().printMessage("Error: " + e.getMessage());
        }
    }
}