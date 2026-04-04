package bg.tu_varna.f24621658.sit.commands;

import bg.tu_varna.f24621658.sit.commands.ipml.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final Map<String, Command> commands;

    public CommandFactory() {
        commands = new HashMap<>();
        commands.put("open", new OpenCommand());
        commands.put("close", new CloseCommand());
        commands.put("save", new SaveCommand());
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand());
    }

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
