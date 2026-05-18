package bg.tu_varna.f24621658.sit.entity;

import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;
import bg.tu_varna.f24621658.sit.entity.keys.SeatKey;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Event {
    private String name;
    private Hall hall;
    private LocalDate date;
    private Map<SeatKey,Ticket> tickets;
    private GenerateCode codeGenerator;

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

    public String getName() {
        return name;
    }

    public Hall getHall() {
        return hall;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<SeatKey, Ticket> getTickets() {
        return tickets;
    }

    public double getAttendancePercent() {
        return (double) getSoldTickets() / hall.getCapacity() * 100;
    }


    public void bookTicket(int row, int seat, String note){
        hall.validateSeat(row, seat); //проверка дали мястото съществува
        SeatKey seatKey = new SeatKey(row,seat);
        Ticket ticket = tickets.computeIfAbsent(seatKey, k -> new Ticket(row, seat, TicketStatus.FREE, "", ""));

        ticket.book(note);
    }

    public String buyTicket(int row, int seat){
        hall.validateSeat(row, seat);

        SeatKey seatKey = new SeatKey(row,seat);
        Ticket ticket = tickets.computeIfAbsent(seatKey, k -> new Ticket(row, seat, TicketStatus.FREE, "", ""));

        String code = codeGenerator.generateCode(this,row,seat);
        ticket.buy(code);
        return code;
    }

    public void unBookTicket(int row, int seat) {
        hall.validateSeat(row, seat);

        SeatKey seatKey = new SeatKey(row, seat);
        Ticket ticket = tickets.get(seatKey);

        if (ticket == null) {
            throw new RuntimeException("Билетът не е запазен");
        }

        ticket.unbook();
    }

    public int getSoldTickets() {
        int count = 0;

        for (Ticket t : tickets.values()) {
            if (t.getStatus() == TicketStatus.SOLD) {
                count++;
            }
        }

        return count;
    }

    public void restoreTicket(int row, int seat, TicketStatus status, String note, String code) {
        hall.validateSeat(row, seat);

        if (status == null) {
            throw new IllegalArgumentException("Невалиден статус на билет.");
        }

        SeatKey key = new SeatKey(row, seat);

        if (tickets.containsKey(key)) {
            throw new IllegalArgumentException("Дублиран билет за ред " + row + ", място " + seat + ".");
        }

        Ticket ticket = new Ticket(row, seat, status, note == null ? "" : note, code == null ? "" : code);
        tickets.put(key, ticket);
    }
}
