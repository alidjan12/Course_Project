package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

import java.time.LocalDate;

public class MostWatchedCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length == 1) {
            context.getTicketSystem().mostWatched();
            return;
        }

        if (args.length == 2) {
            LocalDate from = LocalDate.parse(args[1]);
            context.getTicketSystem().mostWatched(from);
            return;
        }

        if (args.length == 3) {
            LocalDate from = LocalDate.parse(args[1]);
            LocalDate to = LocalDate.parse(args[2]);
            context.getTicketSystem().mostWatched(from, to);
            return;
        }

        context.getPrinter().printMessage("Usage: mostwatched [from] [to]");
    }
}
