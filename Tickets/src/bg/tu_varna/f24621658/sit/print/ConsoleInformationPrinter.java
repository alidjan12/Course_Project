package bg.tu_varna.f24621658.sit.print;

import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.entity.Event;
import bg.tu_varna.f24621658.sit.entity.Hall;
import bg.tu_varna.f24621658.sit.entity.Ticket;
import bg.tu_varna.f24621658.sit.entity.TicketDetails;

import java.time.LocalDate;
import java.util.List;

public class ConsoleInformationPrinter implements InformationPrinter {
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printTickets(List<TicketDetails> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            System.out.println("Няма резултати.");
            return;
        }

        for (TicketDetails details : tickets) {
            Ticket ticket = details.getTicket();
            String note = ticket.getNote() == null || ticket.getNote().isBlank() ? "-" : ticket.getNote();

            System.out.println(
                    details.getDate() + " | " +
                            details.getEventName() + " | зала " +
                            details.getHallNumber() + " | ред " +
                            ticket.getRow() + ", място " +
                            ticket.getSeat() + " | бележка: " +
                            note
            );
        }
    }

    @Override
    public void printReport(Event event, int soldTickets) {
        System.out.println("Представление: " + event.getName());
        System.out.println("Дата: " + event.getDate());
        System.out.println("Зала: " + event.getHall().getHallNumber());
        System.out.println("Продадени билети: " + soldTickets);
        System.out.println("-----------------------------");
    }

    @Override
    public void printClosestEventMessage(CommandContext context, LocalDate date, String inputName) {
        String closestName = context.getTicketSystem().findClosestEventName(date, inputName);

        if (closestName != null) {
            context.getPrinter().printMessage("Няма такова представление. Най-близко намерено: " + closestName);
        } else {
            context.getPrinter().printMessage("Няма представления за дата " + date + ".");
        }
    }

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