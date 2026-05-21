package bg.tu_varna.f24621658.sit.entity;

import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;

/**
 * Модел на място в ред от зала.
 * Пази номера на реда, номера на мястото и билета,
 * който е свързан с това място.
 */
public class Seat {
    private final int rowNumber;
    private final int seatNumber;
    private Ticket ticket;

    /**
     * Създава ново място с подаден номер на ред и номер на място.
     * При невалидни стойности се хвърля изключение.
     *
     * @param rowNumber номерът на реда
     * @param seatNumber номерът на мястото
     */
    public Seat(int rowNumber, int seatNumber) {
        if (rowNumber < 1) {
            throw new IllegalArgumentException("Невалиден номер на ред!");
        }
        if (seatNumber < 1) {
            throw new IllegalArgumentException("Невалиден номер на място!");
        }
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }

    /**
     * Връща номера на реда, в който се намира мястото.
     *
     * @return номерът на реда
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * Връща номера на мястото в реда.
     *
     * @return номерът на мястото
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Връща билета, свързан с това място.
     *
     * @return билетът за мястото или null, ако няма билет
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Проверява дали мястото е свободно.
     * Мястото е свободно, ако няма билет или ако билетът е със статус FREE.
     *
     * @return true, ако мястото е свободно; false в противен случай
     */
    public boolean isFree() {
        return ticket == null || ticket.getStatus() == TicketStatus.FREE;
    }

    /**
     * Запазва мястото за конкретно представление.
     * Създава нов билет със статус BOOKED и добавя бележка към резервацията.
     *
     * @param event представлението, за което се запазва мястото
     * @param note бележката към резервацията
     */
    public void book(Event event, String note) {
        if (!isFree()) {
            throw new RuntimeException("Мястото е вече заето");
        }
        this.ticket = new Ticket(event, this, TicketStatus.BOOKED, note, "");
    }

    /**
     * Закупува билет за мястото за конкретно представление.
     * Ако мястото е свободно, се създава нов закупен билет.
     * Ако вече има билет, той се маркира като закупен.
     *
     * @param event представлението, за което се закупува билетът
     * @param code кодът на закупения билет
     */
    public void buy(Event event, String code) {
        if (isFree()) {
            this.ticket = new Ticket(event, this, TicketStatus.SOLD, "", code);
            return;
        }
        ticket.buy(code);
    }

    /**
     * Отменя резервацията за мястото.
     * След отмяната мястото отново става свободно.
     */
    public void unbook() {
        if (ticket == null || ticket.getStatus() != TicketStatus.BOOKED) {
            throw new RuntimeException("Билетът не е запазен");
        }
        ticket.unbook();
        ticket = null;
    }

    /**
     * Възстановява билет за мястото при зареждане на данни от файл.
     * Използва се за възстановяване на статус, бележка и код на билет.
     *
     * @param event представлението, към което принадлежи билетът
     * @param status статусът на билета
     * @param note бележката към билета
     * @param code кодът на билета
     */
    public void restoreTicket(Event event, TicketStatus status, String note, String code) {
        if (!isFree()) {
            throw new IllegalArgumentException("Дублиран билет за ред " + rowNumber + ", място " + seatNumber + ".");
        }
        if (status == TicketStatus.FREE) {
            ticket = null;
            return;
        }
        ticket = new Ticket(event, this, status, note == null ? "" : note, code == null ? "" : code);
    }

    /**
     * Връща текстово представяне на мястото.
     *
     * @return текст с номер на ред и номер на място
     */
    @Override
    public String toString() {
        return "Seat: " + rowNumber + "-" + seatNumber + " ";
    }
}