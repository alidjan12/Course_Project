package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

/**
 * Команда saveas. Експортира текущите данни и ги записва в нов файл.
 */
public class SaveAsCommand implements Command {
    /**
     * Сериализира текущите данни и ги записва в нов файл, подаден като аргумент.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length < 3) {
            context.getPrinter().printMessage("Error: File path is required.");
            return;
        }

        String filePath = CommandUtils.joinArguments(args, 2);
        filePath = CommandUtils.removeQuotes(filePath);

        try {
            String content = context.getTicketSystem().exportData();
            context.getFileService().setContent(content);
            context.getFileService().saveAs(filePath);

        } catch (IllegalStateException e) {
            context.getPrinter().printMessage("Error: " + e.getMessage());
        }
    }
}