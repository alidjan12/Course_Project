package bg.tu_varna.f24621658.sit.entity;

import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;
import bg.tu_varna.f24621658.sit.entity.keys.SeatKey;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Модел на представление със зала, дата и билети за местата в залата.
 */
public class Event {
    private String name;
    private Hall hall;
    private LocalDate date;
    private Map<SeatKey,Ticket> tickets;
    private GenerateCode codeGenerator;

    /**
     * Създава нов обект от тип Event.
     * @param name името на представлението.
     * @param hall номерът на залата за новото представление.
     * @param date датата на представлението или датата, използвана като филтър.
     */
    public Event(String name, Hall hall, LocalDate date) {
        if(name == null){
            throw new IllegalArgumentException("name is null");
        }
        if(hall == null){
            throw new IllegalArgumentException("hall is null");
        }
        if(date == null){
            throw new IllegalArgumentException("date is null");
        }

        this.name = name;
        this.hall = hall;
        this.date = date;

        tickets = new HashMap<>();
        this.codeGenerator = new GenerateCode();
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public String getName() {
        return name;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public Hall getHall() {
        return hall;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public LocalDate getDate() {
        return date;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public Map<SeatKey, Ticket> getTickets() {
        return tickets;
    }

    /**
     * Изчислява процента посещаемост на представлението.
     * Използва броя продадени билети спрямо капацитета на залата.
     */
    public double getAttendancePercent() {
        return (double) getSoldTickets() / hall.getCapacity() * 100;
    }

    /**
     * Връща броя на свободните места за представлението.
     * Обхождам всички места в залата и проверявам кои от тях нямат билет или са със статус FREE.
     */
    public int getFreeSeatsCount() {
        int count = 0;

        for (Row row : hall.getRows()) {
            for (Seat seat : row.getSeats()) {
                SeatKey key = new SeatKey(row.getRowNumber(), seat.getSeatNumber());
                Ticket ticket = tickets.get(key);

                if (ticket == null || ticket.getStatus() == TicketStatus.FREE) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Запазва билет за подаден ред и място.
     * Ако билет за това място още не съществува, той се създава като свободен и след това се резервира.
     * @param row номерът на реда в залата.
     * @param seat номерът на мястото в реда.
     * @param note бележката, която се записва към резервацията.
     */
    public void bookTicket(int row, int seat, String note){
        hall.validateSeat(row, seat); //проверка дали мястото съществува
        SeatKey seatKey = new SeatKey(row,seat);
        Ticket ticket = tickets.computeIfAbsent(seatKey, k -> new Ticket(row, seat, TicketStatus.FREE, "", ""));

        ticket.book(note);
    }

    /**
     * Закупува билет за подаден ред и място.
     * Генерира уникален код за закупения билет и го връща.
     * @param row номерът на реда в залата.
     * @param seat номерът на мястото в реда.
     * @return намереният, сглобен или генериран текст
     */
    public String buyTicket(int row, int seat){
        hall.validateSeat(row, seat);

        SeatKey seatKey = new SeatKey(row,seat);
        Ticket ticket = tickets.computeIfAbsent(seatKey, k -> new Ticket(row, seat, TicketStatus.FREE, "", ""));

        // Генериране на кода
        String code = codeGenerator.generateCode(this,row,seat);
        ticket.buy(code);
        return code;
    }

    /**
     * Проверява мястото и отменя резервацията, ако билетът е резервиран.
     * @param row номерът на реда в залата.
     * @param seat номерът на мястото в реда.
     */
    public void unBookTicket(int row, int seat) {
        hall.validateSeat(row, seat);

        SeatKey seatKey = new SeatKey(row, seat);
        Ticket ticket = tickets.get(seatKey);

        if (ticket == null) {
            throw new RuntimeException("Билетът не е запазен");
        }

        ticket.unbook();
    }

    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public int getSoldTickets() {
        int count = 0;

        for (Ticket t : tickets.values()) {
            if (t.getStatus() == TicketStatus.SOLD) {
                count++;
            }
        }

        return count;
    }

    /**
     * Възстановява билет от файл, като проверява мястото, статуса и дублиране за същото място.
     * @param row номерът на реда в залата.
     * @param seat номерът на мястото в реда.
     * @param status статусът, който трябва да се зададе или възстанови за билета.
     * @param note бележката, която се записва към резервацията.
     * @param code уникалният код на закупен билет.
     */
    public void restoreTicket(int row, int seat, TicketStatus status, String note, String code) {
        hall.validateSeat(row, seat);

        if (status == null) {
            throw new IllegalArgumentException("Невалиден статус на билет.");
        }

        SeatKey key = new SeatKey(row, seat);

        if (tickets.containsKey(key)) {
            throw new IllegalArgumentException("Дублиран билет за ред " + row + ", място " + seat + ".");
        }

        // Ако note или code са null, заменям ги с празен текст, за да избегна грешки.
        Ticket ticket = new Ticket(row, seat, status, note == null ? "" : note, code == null ? "" : code);
        tickets.put(key, ticket);
    }
}
