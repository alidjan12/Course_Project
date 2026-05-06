package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

public class CloseCommand implements Command {
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