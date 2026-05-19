package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

/**
 * Команда open. Отваря файл, зарежда съдържанието му и го импортира в TicketSystem.
 */
public class OpenCommand implements Command {
    /**
     * Премахва кавички от пътя, отваря файла и импортира съдържанието му в системата.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length < 2) {
            context.getPrinter().printMessage("Error: File path is required.");
            return;
        }

        String filePath = CommandUtils.joinArguments(args, 1);
        filePath = CommandUtils.removeQuotes(filePath);

        try {
            context.getFileService().open(filePath);
            context.getTicketSystem().importData(context.getFileService().getContent());
        } catch (RuntimeException e) {
            context.getPrinter().printMessage("Грешка: " + e.getMessage());
            context.stop();
        }
    }
}