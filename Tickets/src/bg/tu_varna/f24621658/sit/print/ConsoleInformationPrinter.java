package bg.tu_varna.f24621658.sit.print;

import bg.tu_varna.f24621658.sit.entity.Event;
import bg.tu_varna.f24621658.sit.entity.Ticket;

import java.util.List;

public class ConsoleInformationPrinter implements InformationPrinter {
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printTickets(List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            System.out.println("Няма резултати.");
            return;
        }

        for (Ticket ticket : tickets) {
            System.out.println(
                    "Ред " + ticket.getRow() +
                            ", място " + ticket.getSeat() +
                            ", статус: " + ticket.getStatus() +
                            ", бележка: " + ticket.getNote() +
                            ", код: " + ticket.getCode()
            );
        }
    }

    @Override
    public void printReport(Event event,int soldTickets) {
        System.out.println("Представление: " + event.getName());
        System.out.println("Дата: " + event.getDate());
        System.out.println("Зала: " + event.getHall().getHallNumber());
        System.out.println("Продадени билети: " + soldTickets);
        System.out.println("-----------------------------");
    }
}
