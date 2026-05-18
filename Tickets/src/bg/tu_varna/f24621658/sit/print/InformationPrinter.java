package bg.tu_varna.f24621658.sit.print;

import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.entity.Event;
import bg.tu_varna.f24621658.sit.entity.Hall;
import bg.tu_varna.f24621658.sit.entity.TicketDetails;

import java.time.LocalDate;
import java.util.List;

public interface InformationPrinter {
    void printMessage(String message);

    void printTickets(List<TicketDetails> tickets);

    void printReport(Event event, int soldTickets);

    void printClosestEventMessage(CommandContext context, LocalDate date, String inputName);

    void printMostWatchedEvents(List<Event> events);

    void printEvents(List<Event> events);

    void printHalls(List<Hall> halls);
}