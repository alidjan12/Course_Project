package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

/**
 * Команда help. Извежда списък с поддържаните файлови и билетни команди.
 */
public class HelpCommand implements Command {
    /**
     * Извежда помощното меню с файловите и билетните команди.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
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

    /**
     * Извежда помощ за командите за работа с файлове.
     * @return намереният, сглобен или генериран текст
     */
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
    /**
     * Извежда помощ за командите за представления, резервации и справки.
     * @return намереният, сглобен или генериран текст
     */
    private String ticketCommandsHelp() {
        return """
        Команди за билетната система:

        showevents
            Показва всички добавени представления, подредени по дата, име и зала.

        showhalls
            Показва всички налични зали.
        
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

        bookings
            Показва всички запазени билети.

        bookings <дата>
            Показва запазените билети за конкретна дата.
            Пример: bookings 2026-12-12

        bookings <име>
            Показва запазените билети за представление.
            Пример: bookings Hamlet

        bookings <дата> <име>
            Показва запазените билети за конкретно представление на конкретна дата.
            Пример: bookings 2026-12-12 Hamlet

        check <код>
            Проверява валидността на билет.

        report <от дата> <до дата> [<зала>]
            Показва продадените билети за период.
            Ако залата липсва, показва информация за всички зали.

        mostwatched
            Показва всички представления, сортирани по брой продадени билети.

        mostwatched <от дата>
            Показва най-гледаните представления от дадена дата нататък.
            Пример: mostwatched 2026-01-01

        mostwatched <от дата> <до дата>
            Показва най-гледаните представления за даден период.
            Пример: mostwatched 2026-01-01 2026-12-31

        lowattendance <от дата> <до дата>
            Показва представленията с посещаемост под 10% за даден период.
            Пример: lowattendance 2026-01-01 2026-12-31

        removeevent <дата> <име>
            Сваля/премахва представление от програмата.
            Пример: removeevent 2026-12-12 Hamlet
        """;
    }
}
