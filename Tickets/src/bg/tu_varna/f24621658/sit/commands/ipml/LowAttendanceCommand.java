package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

import java.time.LocalDate;

public class LowAttendanceCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length != 3) {
            context.getPrinter().printMessage("Usage: lowattendance <from> <to>");
            return;
        }

        LocalDate from = LocalDate.parse(args[1]);
        LocalDate to = LocalDate.parse(args[2]);

        context.getTicketSystem().lowAttendance(from, to);
    }
}
