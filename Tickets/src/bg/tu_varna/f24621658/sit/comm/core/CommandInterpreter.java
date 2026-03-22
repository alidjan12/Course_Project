package bg.tu_varna.f24621658.sit.comm.core;

import bg.tu_varna.f24621658.sit.comm.commands.Command;
import bg.tu_varna.f24621658.sit.comm.commands.OpenCommand;


import java.util.HashMap;
import java.util.Map;

public class CommandInterpreter {

    private Map<String, Command> commands = new HashMap<>();

    public CommandInterpreter(FileManager fileManager) {

        commands.put("open", new OpenCommand(fileManager));
//        commands.put("close", new CloseCommand(fileManager));
//        commands.put("save", new SaveCommand(fileManager));
//        commands.put("saveas", new SaveAsCommand(fileManager));
//        commands.put("help", new HelpCommand());
//        commands.put("exit", new ExitCommand());
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }
}