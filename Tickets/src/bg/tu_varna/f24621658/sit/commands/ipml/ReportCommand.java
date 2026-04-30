package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

import java.time.LocalDate;

public class ReportCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length != 3 && args.length != 4) {
            System.out.println("Usage: report <from> <to> [<hall>]");
            return;
        }

        LocalDate from = LocalDate.parse(args[1]);
        LocalDate to = LocalDate.parse(args[2]);

        if (args.length == 4) {
            int hall = Integer.parseInt(args[3]);
            context.getTicketSystem().report(from, to, hall);
        } else {
            context.getTicketSystem().report(from, to);
        }
    }
}
