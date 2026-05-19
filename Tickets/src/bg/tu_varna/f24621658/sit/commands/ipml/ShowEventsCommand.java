package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

/**
 * Команда showevents. Извежда списък с всички представления.
 */
public class ShowEventsCommand implements Command {
    /**
     * Извежда всички заредени представления.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        context.getTicketSystem().showEvents();
    }
}