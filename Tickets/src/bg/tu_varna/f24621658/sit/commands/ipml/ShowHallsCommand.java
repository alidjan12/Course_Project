package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

/**
 * Команда showhalls. Извежда списък със залите и техните параметри.
 */
public class ShowHallsCommand implements Command {
    /**
     * Извежда всички предварително дефинирани зали.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        context.getTicketSystem().showHalls();
    }
}