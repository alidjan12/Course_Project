package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

public class OpenCommand implements Command {
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