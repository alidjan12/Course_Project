package bg.tu_varna.f24621658.sit.entity;

import java.time.LocalDate;

/**
 * Обединява информацията, нужна при проверка на валидността на билет.
 */
public class TicketDetails {
    private final Event event;
    private final Ticket ticket;

    /**
     * Създава нов обект от тип TicketDetails.
     * @param event представлението, което се записва, проверява или използва за извеждане.
     * @param ticket билетът, за който се пазят допълнителни данни за представлението
     */
    public TicketDetails(Event event, Ticket ticket) {
        this.event = event;
        this.ticket = ticket;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public Event getEvent() {
        return event;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public Ticket getTicket() {
        return ticket;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public LocalDate getDate() {
        return event.getDate();
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public String getEventName() {
        return event.getName();
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public int getHallNumber() {
        return event.getHall().getHallNumber();
    }
}
