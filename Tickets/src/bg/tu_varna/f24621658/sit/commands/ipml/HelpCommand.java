package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

public class HelpCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        System.out.println("The following commands are supported:");
        System.out.println("open <file>      opens <file>");
        System.out.println("close            closes currently opened file");
        System.out.println("save             saves the currently opened file");
        System.out.println("save as <file>   saves the currently opened file in <file>");
        System.out.println("help             prints this information");
        System.out.println("exit             exits the program");
    }
}
