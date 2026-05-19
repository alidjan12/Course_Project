package bg.tu_varna.f24621658.sit.core;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandFactory;
import bg.tu_varna.f24621658.sit.print.ConsoleInformationPrinter;
import bg.tu_varna.f24621658.sit.print.InformationPrinter;
import bg.tu_varna.f24621658.sit.services.FileService;
import bg.tu_varna.f24621658.sit.services.TicketSystem;

import java.util.Scanner;

/**
 * Главен цикъл на програмата, който чете команди и ги изпълнява.
 */
public class Engine {
    private final Scanner scanner;
    private final CommandFactory commandFactory;
    private final CommandContext context;

    /**
     * Създава нов обект от тип Engine.
     */
    public Engine() {
        this.scanner = new Scanner(System.in);
        this.commandFactory = new CommandFactory();
        InformationPrinter printer = new ConsoleInformationPrinter();

        this.context = new CommandContext(new FileService(), new TicketSystem(printer), printer);
    }

    /**
     * Стартира основния цикъл: чете команди от конзолата, проверява дали са разрешени и ги изпълнява.
     */
    public void start() {
        while (context.isRunning()) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            Command command = commandFactory.getCommand(input);

            if (command == null) {
                System.out.println("Грешка: Непозната команда.");
                continue;
            }

            String[] args = input.split("\\s+");
            String commandName = args[0].toLowerCase();

            if (!canExecuteCommand(commandName)) {
                continue;
            }

            try {
                command.execute(args, context);
            } catch (RuntimeException e) {
                System.out.println("Грешка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Възникна неочаквана грешка.");
            }
        }
    }
    /**
     * Проверява дали дадена команда може да се изпълни според това дали има отворен файл.
     * @param commandName името на командата, която се проверява.
     * @return true, ако командата е позволена в текущото състояние; false в противен случай
     */
    private boolean canExecuteCommand(String commandName) {
        boolean fileIsOpened = context.getFileService().hasOpenedFile();

        if (commandName.equals("open") && fileIsOpened) {
            System.out.println("Грешка: Вече има отворен файл. Първо го затворете.");
            return false;
        }

        if (!fileIsOpened && !isAllowedWithoutOpenedFile(commandName)) {
            System.out.println("Грешка: Няма отворен файл.");
            return false;
        }

        return true;
    }
    /**
     * Определя кои команди са позволени преди отваряне на файл.
     * @param commandName името на командата, която се проверява.
     * @return true, ако командата може да се изпълни без отворен файл
     */
    private boolean isAllowedWithoutOpenedFile(String commandName) {
        return commandName.equals("open")
                || commandName.equals("help")
                || commandName.equals("exit");
    }
}