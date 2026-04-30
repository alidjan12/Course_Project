package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

import java.time.LocalDate;

public class BookCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if(args.length<6){
            System.out.println("Usage: book <row> <seat> <date> <name> <note>");
            return;
        }
        int row = Integer.parseInt(args[1]);
        int seat = Integer.parseInt(args[2]);
        LocalDate date = LocalDate.parse(args[3]);
        String name = args[4];
        String note = args[5];

        context.getTicketSystem().book(row, seat, date, name, note);
    }
}
