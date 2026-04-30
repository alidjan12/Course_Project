package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

import java.time.LocalDate;

public class FreeSeatsCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if(args.length <3){
            System.out.println("Usage: freeseats <date> <name>");
            return;
        }
        LocalDate date = LocalDate.parse(args[1]);
        String name = args[2];
        context.getTicketSystem().freeSeats(date, name);
    }
}
