package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.commands.CommandUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Команда addevent. Чете дата, номер на зала и име на представление и ги подава към TicketSystem.
 */
public class AddEventCommand implements Command {
    /**
     * Проверява броя аргументи, преобразува датата и номера залата и добавя новото представление.
     * @param args аргументите на командата, въведени от потребителя.
     * @param context общият контекст с достъп до TicketSystem, FileService, принтер и състоянието на приложението.
     */
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length < 4) {
            context.getPrinter().printMessage("Команда: addevent <date> <hall> <name>");
            return;
        }

        try {
            LocalDate date = LocalDate.parse(args[1]);
            int hall = Integer.parseInt(args[2]);
            String name = CommandUtils.joinArguments(args, 3);

            context.getTicketSystem().addEvent(date, hall, name);
            context.getPrinter().printMessage("Успешно добавено представление.");
        } catch (DateTimeParseException e) {
            context.getPrinter().printMessage("Грешка: Датата трябва да е във формат yyyy-MM-dd. Пример: 2026-02-25");
        } catch (NumberFormatException e) {
            context.getPrinter().printMessage("Грешка: Номерът на залата трябва да е число.");
        }
    }
}