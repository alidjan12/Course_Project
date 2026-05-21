package bg.tu_varna.f24621658.sit.print;

import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.entity.Event;
import bg.tu_varna.f24621658.sit.entity.Hall;
import bg.tu_varna.f24621658.sit.entity.Ticket;

import java.time.LocalDate;
import java.util.List;

/**
 * Реализация на {@link InformationPrinter}, която визуализира информацията
 * от системата чрез стандартния изход на конзолата.
 *
 * Класът отговаря само за форматиране и отпечатване на данни към потребителя.
 */
public class ConsoleInformationPrinter implements InformationPrinter {
    /**
     * Извежда текстово съобщение директно в конзолата.
     * Методът се използва за показване на системни съобщения,
     * потвърждения, грешки или резултати от изпълнени команди.
     *
     * @param message текстът, който трябва да бъде показан на потребителя.
     */
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
    /**
     * Извежда списък с билети, като всеки билет се форматира с информация
     * за дата на представлението, име на представлението, номер на зала,
     * ред, място и бележка към билета.
     *
     * Ако списъкът е {@code null} или празен, методът извежда съобщение,
     * че няма намерени резултати. Когато билетът няма въведена бележка,
     * вместо празен текст се извежда символът "-".
     *
     * @param tickets списък с детайлна информация за билетите, които трябва да бъдат изведени.
     */
    @Override
    public void printTickets(List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            System.out.println("Няма резултати.");
            return;
        }

        for (Ticket ticket : tickets) {
            Event event = ticket.getEvent();
            String note = ticket.getNote() == null || ticket.getNote().isBlank() ? "-" : ticket.getNote();

            System.out.println(
                    event.getDate() + " | " +
                            event.getName() + " | зала " +
                            event.getHall().getHallNumber() + " | ред " +
                            ticket.getSeat().getRowNumber() + ", място " +
                            ticket.getSeat().getSeatNumber() + " | бележка: " +
                            note
            );
        }
    }
    /**
     * Извежда справка за конкретно представление.
     * Справката включва името на представлението, датата,
     * номера на залата и броя продадени билети.
     *
     * Методът не изчислява броя на продадените билети, а само получава
     * вече изчислена стойност чрез параметъра {@code soldTickets}
     * и я показва в конзолата.
     *
     * @param event представлението, за което се извежда справката.
     * @param soldTickets броят продадени билети за това представление.
     */
    @Override
    public void printReport(Event event, int soldTickets) {
        System.out.println("Представление: " + event.getName());
        System.out.println("Дата: " + event.getDate());
        System.out.println("Зала: " + event.getHall().getHallNumber());
        System.out.println("Продадени билети: " + soldTickets);
        System.out.println("-----------------------------");
    }
    /**
     * Извежда помощно съобщение, когато потребителят е въвел име на представление,
     * което не съществува за дадена дата.
     *
     * Методът търси най-близкото съществуващо име на представление чрез
     * {@code TicketSystem}. Ако бъде намерено близко съвпадение, то се предлага
     * на потребителя като възможна корекция. Ако за подадената дата няма
     * никакви представления, се извежда отделно съобщение за липса на представления.
     *
     * @param context контекстът на командата, чрез който се достъпват системата за билети и принтерът.
     * @param date датата, за която се проверява дали има представления.
     * @param inputName името на представлението, въведено от потребителя.
     */
    @Override
    public void printClosestEventMessage(CommandContext context, LocalDate date, String inputName) {
        String closestName = context.getTicketSystem().findClosestEventName(date, inputName);

        if (closestName != null) {
            context.getPrinter().printMessage("Няма такова представление. Най-близко намерено: " + closestName);
        } else {
            context.getPrinter().printMessage("Няма представления за дата " + date + ".");
        }
    }
    /**
     * Извежда списък с най-гледаните представления.
     * За всяко представление се показват име, дата, номер на зала
     * и брой продадени билети.
     *
     * Методът приема вече подготвен списък с представления и не извършва
     * сортиране или филтриране. Ако списъкът е {@code null} или празен,
     * се извежда съобщение, че няма резултати.
     *
     * @param events списък с представления, които трябва да бъдат показани като най-гледани.
     */
    @Override
    public void printMostWatchedEvents(List<Event> events) {
        if (events == null || events.isEmpty()) {
            System.out.println("Няма резултати.");
            return;
        }

        for (Event event : events) {
            System.out.println(
                    "Представление: " + event.getName() +
                            ", дата: " + event.getDate() +
                            ", зала: " + event.getHall().getHallNumber() +
                            ", продадени билети: " + event.getSoldTickets()
            );
        }
    }
    /**
     * Извежда всички добавени представления в системата.
     * За всяко представление се показват неговото име, дата и номер на зала.
     *
     * Методът служи само за визуализиране на вече съществуващ списък
     * и не променя данните за представленията. Ако списъкът е {@code null}
     * или празен, се извежда съобщение, че няма добавени представления.
     *
     * @param events списък с представленията, които трябва да бъдат изведени в конзолата.
     */
    @Override
    public void printEvents(List<Event> events) {
        if (events == null || events.isEmpty()) {
            System.out.println("Няма добавени представления.");
            return;
        }

        for (Event event : events) {
            System.out.println(
                    "Представление: " + event.getName() +
                            ", дата: " + event.getDate() +
                            ", зала: " + event.getHall().getHallNumber()
            );
        }
    }
    /**
     * Извежда информация за наличните зали.
     * За всяка зала се отпечатва нейното текстово разположение,
     * получено чрез {@code getLayout()}, което представя структурата
     * на редовете и местата в залата.
     *
     * Ако списъкът е {@code null} или празен, методът извежда съобщение,
     * че няма налични зали.
     *
     * @param halls списък със залите, чието разположение трябва да бъде показано.
     */
    @Override
    public void printHalls(List<Hall> halls) {
        if (halls == null || halls.isEmpty()) {
            System.out.println("Няма налични зали.");
            return;
        }

        for (Hall hall : halls) {
            System.out.println(hall.getLayout());
        }
    }
}