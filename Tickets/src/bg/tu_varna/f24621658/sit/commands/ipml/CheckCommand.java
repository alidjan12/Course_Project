package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

/**
 * Команда check. Проверява валидността на билетен код и извежда информацията за билета.
 */
public class CheckCommand implements Command {
    /**
     * Проверява дали е подаден точно един код и го подава към системата за валидация.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length != 2) {
            context.getPrinter().printMessage("Команда: check <code>");
            return;
        }

        context.getTicketSystem().check(args[1]);
    }
}
