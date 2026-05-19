package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

/**
 * Команда exit. Спира основния цикъл на приложението.
 */
public class ExitCommand implements Command {
    /**
     * Извежда съобщение и спира основния цикъл на приложението.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        context.getPrinter().printMessage("Exiting the program...");
        context.stop();
    }
}