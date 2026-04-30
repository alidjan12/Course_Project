package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

public class CheckCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length != 2) {
            System.out.println("Usage: check <code>");
            return;
        }

        context.getTicketSystem().check(args[1]);
    }
}
