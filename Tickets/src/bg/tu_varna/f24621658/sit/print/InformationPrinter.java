package bg.tu_varna.f24621658.sit.print;

import bg.tu_varna.f24621658.sit.entity.Event;
import bg.tu_varna.f24621658.sit.entity.Ticket;

import java.util.List;

public interface InformationPrinter {
    void printMessage(String message);
    void printTickets(List<Ticket> tickets);
    void printReport(Event event,int soldTickets);
}
