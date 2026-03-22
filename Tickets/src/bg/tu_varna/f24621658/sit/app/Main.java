package bg.tu_varna.f24621658.sit.app;

import bg.tu_varna.f24621658.sit.comm.commands.Command;
import bg.tu_varna.f24621658.sit.comm.core.CommandInterpreter;
import bg.tu_varna.f24621658.sit.comm.core.FileManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        FileManager fileManager = new FileManager();
        CommandInterpreter interpreter = new CommandInterpreter(fileManager);

        while (true) {

            System.out.print("> ");
            String input = scanner.nextLine();

            String[] tokens = input.split(" ");

            Command command = interpreter.getCommand(tokens[0]);

            if (command == null) {
                System.out.println("Unknown command");
                continue;
            }

            command.execute(tokens);
        }
    }
}