package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

public class SaveAsCommand implements Command {
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