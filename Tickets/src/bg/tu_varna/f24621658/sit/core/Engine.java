package bg.tu_varna.f24621658.sit.core;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandFactory;
import bg.tu_varna.f24621658.sit.services.FileService;

import java.util.Scanner;

public class Engine {
    private final Scanner scanner;
    private final CommandFactory commandFactory;
    private final CommandContext context;

    public Engine() {
        this.scanner = new Scanner(System.in);
        this.commandFactory = new CommandFactory();
        this.context = new CommandContext(new FileService());
    }

    public void start() {
        while (context.isRunning()) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            Command command = commandFactory.getCommand(input);

            if (command == null) {
                System.out.println("Error: Unknown command.");
                continue;
            }

            String[] args = input.split("\\s+");
            command.execute(args, context);
        }
    }
}
