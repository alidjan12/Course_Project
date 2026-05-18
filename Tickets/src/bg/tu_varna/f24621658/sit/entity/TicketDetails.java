package bg.tu_varna.f24621658.sit.entity;

import java.time.LocalDate;

public class TicketDetails {
    private final Event event;
    private final Ticket ticket;

    public TicketDetails(Event event, Ticket ticket) {
        this.event = event;
        this.ticket = ticket;
    }

    public Event getEvent() {
        return event;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public LocalDate getDate() {
        return event.getDate();
    }

    public String getEventName() {
        return event.getName();
    }

    public int getHallNumber() {
        return event.getHall().getHallNumber();
    }
}
