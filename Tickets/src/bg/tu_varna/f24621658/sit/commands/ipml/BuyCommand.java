package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

import java.time.LocalDate;

public class BuyCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length < 5) {
            context.getPrinter().printMessage("Употреба: buy <ред> <място> <дата> <име>");
            return;
        }

        int row = Integer.parseInt(args[1]);
        int seat = Integer.parseInt(args[2]);
        LocalDate date = LocalDate.parse(args[3]);
        String name = CommandUtils.joinArguments(args, 4);

        if (!context.getTicketSystem().eventExists(date, name)) {
            context.getPrinter().printClosestEventMessage(context, date, name);
            return;
        }

        context.getTicketSystem().buy(row, seat, date, name);
        context.getPrinter().printMessage("Успешно закупен билет.");
    }
}