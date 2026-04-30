package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

import java.time.LocalDate;

public class BuyCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if(args.length<5){
            System.out.println("Usage: buy <row> <seat> <date> <name>");
            return;
        }
        int row = Integer.parseInt(args[1]);
        int seat = Integer.parseInt(args[2]);
        LocalDate date = LocalDate.parse(args[3]);
        String name = args[4];

        context.getTicketSystem().buy(row, seat, date, name);
    }

}
