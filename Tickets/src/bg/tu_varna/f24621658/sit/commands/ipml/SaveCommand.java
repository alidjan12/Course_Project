package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

public class SaveCommand implements Command {
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
