package bg.tu_varna.f24621658.sit.entity;

import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;

import java.time.LocalDate;
import java.util.*;

/**
 * Модел на представление със зала, дата и билети за местата в залата.
 * Пази името на представлението, залата, датата и обект за генериране
 * на кодове при закупуване на билети.
 */
public class Event {
    private final String name;
    private final Hall hall;
    private final LocalDate date;

    private final GenerateCode codeGenerator;

    /**
     * Създава ново представление с подадено име, зала и дата.
     * При невалидно име, липсваща зала или липсваща дата се хвърля изключение.
     * Залата се копира, за да има всяко представление собствени места и билети.
     *
     * @param name името на представлението
     * @param hall залата, в която ще се проведе представлението
     * @param date датата на представлението
     */
    public Event(String name, Hall hall, LocalDate date) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Невалидно име!");
        }
        if (hall == null) {
            throw new IllegalArgumentException("hall is null");
        }
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
        this.name = name;
        this.hall = new Hall(hall);
        this.date = date;
        this.codeGenerator = new GenerateCode();
    }

    /**
     * Връща името на представлението.
     *
     * @return името на представлението
     */
    public String getName() {
        return name;
    }

    /**
     * Връща залата, в която се провежда представлението.
     *
     * @return залата на представлението
     */
    public Hall getHall() {
        return hall;
    }

    /**
     * Връща датата на представлението.
     *
     * @return датата на представлението
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Връща всички билети на представлението, които не са свободни.
     * Свободните места без билет не се добавят в резултата.
     *
     * @return списък с билети, които са запазени или закупени
     */
    public List<Ticket> getTickets() {
        List<Ticket> result = new ArrayList<>();
        for (Seat seat : hall.getSeats()) {
            if (!seat.isFree()) {
                result.add(seat.getTicket());
            }
        }
        return result;
    }

    /**
     * Изчислява процента посещаемост на представлението.
     * Посещаемостта се определя според броя продадени билети спрямо капацитета на залата.
     *
     * @return процентът посещаемост на представлението
     */
    public double getAttendancePercent() {
        return (double) getSoldTickets() / hall.getCapacity() * 100;
    }

    /**
     * Изчислява броя на свободните места за представлението.
     *
     * @return броят свободни места
     */
    public int getFreeSeatsCount() {
        int count = 0;
        for (Seat seat : hall.getSeats()) {
            if (seat.isFree()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Запазва билет за конкретно място в залата.
     * Към резервацията се добавя подадената бележка.
     *
     * @param row номерът на реда
     * @param seat номерът на мястото
     * @param note бележката към резервацията
     */
    public void bookTicket(int row, int seat, String note) {
        hall.findSeat(row, seat).book(this, note);
    }

    /**
     * Закупува билет за конкретно място в залата.
     * Генерира код за билета и го връща при успешно закупуване.
     *
     * @param row номерът на реда
     * @param seat номерът на мястото
     * @return генерираният код на закупения билет
     */
    public String buyTicket(int row, int seat) {
        Seat selectedSeat = hall.findSeat(row, seat);
        String code = codeGenerator.generateCode(this, selectedSeat);
        selectedSeat.buy(this, code);
        return code;
    }

    /**
     * Отменя резервация за конкретно място в залата.
     *
     * @param row номерът на реда
     * @param seat номерът на мястото
     */
    public void unBookTicket(int row, int seat) {
        hall.findSeat(row, seat).unbook();
    }

    /**
     * Изчислява броя на продадените билети за представлението.
     *
     * @return броят продадени билети
     */
    public int getSoldTickets() {
        int count = 0;
        for (Ticket ticket : getTickets()) {
            if (ticket.getStatus() == TicketStatus.SOLD) {
                count++;
            }
        }
        return count;
    }

    /**
     * Възстановява билет за конкретно място при зареждане на данни от файл.
     * Използва подадените статус, бележка и код, за да възстанови състоянието на билета.
     *
     * @param row номерът на реда
     * @param seat номерът на мястото
     * @param status статусът на билета
     * @param note бележката към билета
     * @param code кодът на билета
     */
    public void restoreTicket(int row, int seat, TicketStatus status, String note, String code) {
        if (status == null) {
            throw new IllegalArgumentException("Невалиден статус на билет.");
        }
        hall.findSeat(row, seat).restoreTicket(this, status, note, code);
    }

    /**
     * Проверява дали представлението има същите име и дата като подадените.
     *
     * @param name името за сравнение
     * @param date датата за сравнение
     * @return true, ако името и датата съвпадат; false в противен случай
     */
    public boolean hasSameIdentity(String name, LocalDate date) {
        return this.name.equals(name) && this.date.equals(date);
    }

    /**
     * Проверява дали текущото представление е равно на друг обект.
     * Две представления се считат за равни, ако имат еднакви име и дата.
     *
     * @param o обектът, с който се сравнява текущото представление
     * @return true, ако обектите са равни; false в противен случай
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event event)) {
            return false;
        }
        return Objects.equals(name, event.name) && Objects.equals(date, event.date);
    }

    /**
     * Връща хеш код на представлението.
     * Хеш кодът се изчислява на база име и дата.
     *
     * @return хеш код на представлението
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }
}