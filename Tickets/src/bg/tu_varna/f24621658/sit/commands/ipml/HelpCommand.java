package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

public class HelpCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length == 1) {
            context.getPrinter().printMessage(fileCommandsHelp());
            return;
        }

        if (args.length == 2 && args[1].equalsIgnoreCase("more")) {
            context.getPrinter().printMessage(ticketCommandsHelp());
            return;
        }

        context.getPrinter().printMessage("Usage: help OR help more");
    }

    private String fileCommandsHelp() {
        return """
            Поддържани файлови команди:

            open <файл>          отваря файл
            close                затваря текущо отворения файл
            save                 запазва текущо отворения файл
            save as <файл>       запазва текущите данни в друг файл
            help                 показва основните команди
            help more            показва командите за билети
            exit                 изход от програмата
            """;
    }

    private String ticketCommandsHelp() {
        return """
            Команди за билетната система:

            addevent <дата> <зала> <име>
                Добавя ново представление.
                Пример: addevent 2026-12-12 1 Hamlet

            freeseats <дата> <име>
                Показва свободните места за представление.
                Пример: freeseats 2026-12-12 Hamlet

            book <ред> <място> <дата> <име> <бележка>
                Запазва билет.
                Пример: book 1 5 2026-12-12 Hamlet Ivan

            unbook <ред> <място> <дата> <име>
                Отменя запазване на билет.
                Пример: unbook 1 5 2026-12-12 Hamlet

            buy <ред> <място> <дата> <име>
                Закупува билет.
                Пример: buy 1 5 2026-12-12 Hamlet

            bookings <дата> [<име>]
                Показва запазените билети по дата или по дата и име.

            bookings <име>
                Показва запазените билети за представление.

            check <код>
                Проверява валидността на билет.

            report <от дата> <до дата> [<зала>]
                Показва продадените билети за период.
                Ако залата липсва, показва информация за всички зали.
            """;
    }
}
