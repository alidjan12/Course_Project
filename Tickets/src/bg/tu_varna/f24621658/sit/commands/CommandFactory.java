package bg.tu_varna.f24621658.sit.commands;

import bg.tu_varna.f24621658.sit.commands.ipml.*;

import java.util.HashMap;
import java.util.Map;
/**
 * Създава конкретни команди според въведеното от потребителя име на команда.
 */
public class CommandFactory {
    private final Map<String, Command> commands;
    /**
     * Създава нов обект от тип CommandFactory.
     */
    public CommandFactory() {
        commands = new HashMap<>();
        commands.put("open", new OpenCommand());
        commands.put("close", new CloseCommand());
        commands.put("save", new SaveCommand());
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand());
        commands.put("addevent", new AddEventCommand());
        commands.put("freeseats", new FreeSeatsCommand());
        commands.put("book", new BookCommand());
        commands.put("unbook", new UnBookCommand());
        commands.put("buy",new BuyCommand());
        commands.put("bookings",new BookingsCommand());
        commands.put("check",new CheckCommand());
        commands.put("report",new ReportCommand());
        commands.put("mostwatched", new MostWatchedCommand());
        commands.put("lowattendance", new LowAttendanceCommand());
        commands.put("removeevent", new RemoveEventCommand());
        commands.put("showevents", new ShowEventsCommand());
        commands.put("showhalls", new ShowHallsCommand());
    }

    /**
     * Връща стойността на съответното поле.
     * @param inputLine пълният ред, въведен от потребителя.
     * @return Command обект за въведената команда
     */
    public Command getCommand(String inputLine) {
        String[] tokens = inputLine.trim().split("\\s+");

        if (tokens.length == 0 || tokens[0].isEmpty()) {
            return null;
        }

        if (tokens.length >= 2 &&
                tokens[0].equalsIgnoreCase("save") &&
                tokens[1].equalsIgnoreCase("as")) {
            return new SaveAsCommand();
        }

        return commands.get(tokens[0].toLowerCase());
    }
}
