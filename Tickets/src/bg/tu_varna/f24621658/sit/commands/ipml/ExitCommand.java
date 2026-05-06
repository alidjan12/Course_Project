package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

public class ExitCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        context.getPrinter().printMessage("Exiting the program...");
        context.stop();
    }
}