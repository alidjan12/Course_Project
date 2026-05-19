package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

/**
 * Команда save. Експортира текущите данни и ги записва във вече отворения файл.
 */
public class SaveCommand implements Command {
    /**
     * Сериализира текущите данни и ги записва в текущо отворения файл.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        try {
            String content = context.getTicketSystem().exportData();
            context.getFileService().setContent(content);
            context.getFileService().save();

        } catch (IllegalStateException e) {
            context.getPrinter().printMessage("Error: " + e.getMessage());
        }
    }
}
