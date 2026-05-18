package bg.tu_varna.f24621658.sit.services;

import bg.tu_varna.f24621658.sit.entity.*;
import bg.tu_varna.f24621658.sit.entity.contracts.InformationSystemCommands;
import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;
import bg.tu_varna.f24621658.sit.entity.keys.EventKey;
import bg.tu_varna.f24621658.sit.entity.keys.SeatKey;
import bg.tu_varna.f24621658.sit.print.InformationPrinter;
import bg.tu_varna.f24621658.sit.repository.EventRepository;
import bg.tu_varna.f24621658.sit.repository.HallRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

public class TicketSystem implements InformationSystemCommands {
    private EventRepository eventRepo;
    private HallRepository hallRepo;
    private InformationPrinter printer;

    public TicketSystem(InformationPrinter printer) {
        this.eventRepo = new EventRepository();
        this.hallRepo = new HallRepository();
        this.printer = printer;
    }

    @Override
    public void addEvent(LocalDate date, int hall, String name) {
        Hall foundHall = hallRepo.findByNumber(hall);

        if (foundHall == null) {
            throw new RuntimeException("Няма зала с номер " + hall);
        }

        eventRepo.save(new Event(name, foundHall, date));
    }

    @Override
    public void removeEvent(LocalDate date, String name) {
        eventRepo.remove(date, name);
        printer.printMessage("Представлението е свалено успешно.");
    }

    @Override
    public void freeSeats(LocalDate date, String name) {
        Event event = eventRepo.findByNameAndDate(name, date);

        if(event == null) {
            throw new RuntimeException("Няма такова представление!");
        }

        Hall hall = event.getHall();
        Map<SeatKey,Ticket> tickets = event.getTickets();

        boolean hasFreeSeats = false;

        for(Row row:hall.getRows()) {
            for(Seat seat:row.getSeats()) {
                SeatKey key = new SeatKey(row.getRowNumber(), seat.getSeatNumber());
                Ticket ticket = tickets.get(key);

                if(ticket == null || ticket.getStatus()== TicketStatus.FREE) {
                    printer.printMessage("Ред " + row.getRowNumber() +
                            ", място " + seat.getSeatNumber());
                    hasFreeSeats = true;
                }
            }
        }
        if (!hasFreeSeats) {
            printer.printMessage("Няма свободни места");
        }
     }

    @Override
    public void book(int row, int seat, LocalDate date, String name, String note) {
        Event  event = eventRepo.findByNameAndDate(name,date);
        if (event == null) {
            throw new RuntimeException("Няма такова представление");
        }
        event.bookTicket(row,seat,note);
    }

    @Override
    public void unbook(int row, int seat, LocalDate date, String name) {
        Event event = eventRepo.findByNameAndDate(name,date);
        if (event == null) {
            throw new RuntimeException("Няма такова представление");
        }
        event.unBookTicket(row,seat);
    }

    @Override
    public String buy(int row, int seat, LocalDate date, String name) {
        Event event = eventRepo.findByNameAndDate(name, date);

        if (event == null) {
            throw new RuntimeException("Няма такова представление.");
        }

        return event.buyTicket(row, seat);
    }

    @Override
    public void check(String code) {
        TicketDetails details = eventRepo.findTicketDetailsByCode(code);

        if (details == null) {
            throw new RuntimeException("Невалиден билетен код!");
        }

        Ticket ticket = details.getTicket();

        printer.printMessage(
                "Билетът е валиден за представление " +
                        details.getEventName() +
                        " на дата " + details.getDate() +
                        ", зала " + details.getHallNumber() +
                        ", ред " + ticket.getRow() +
                        ", място " + ticket.getSeat()
        );
    }

    @Override
    public void bookings(LocalDate date, String name) {printBookings(eventRepo.getBookedTickets(name, date));}

    @Override
    public void bookings(String name) {
        printBookings(eventRepo.getBookedTickets(name));
    }

    @Override
    public void bookings(LocalDate date) {
        printBookings(eventRepo.getBookedTickets(date));
    }

    @Override
    public void bookings() { printBookings(eventRepo.getBookedTickets());}

    @Override
    public void report(LocalDate from, LocalDate to, int hallNumber) {
        Map<EventKey,Event> events = eventRepo.getEvents();

        for(Event event : events.values()){
           boolean isInPeriod = !event.getDate().isBefore(from) && !event.getDate().isAfter(to);

           boolean isInHall = event.getHall().getHallNumber() ==  hallNumber;

           if(isInPeriod && isInHall) {
                printer.printReport(event, event.getSoldTickets());
           }
        }
    }

    @Override
    public void report(LocalDate from, LocalDate to) {
        Map<EventKey,Event> events = eventRepo.getEvents();

        for(Event event : events.values()){
            boolean isInPeriod = !event.getDate().isBefore(from) && !event.getDate().isAfter(to);

            if(isInPeriod) {

                printer.printReport(event, event.getSoldTickets());
            }
        }
    }

    @Override
    public void mostWatched(LocalDate from, LocalDate to) {
        List<Event> events = eventRepo.getEventsBetween(from,to);

        events.sort((e1, e2) -> Integer.compare(e2.getSoldTickets(),e1.getSoldTickets()));

        if(events.isEmpty()){
            printer.printMessage("Няма представления за този период.");
            return;
        }

        printer.printMostWatchedEvents(events);

    }

    @Override
    public void mostWatched(LocalDate from) {
        List<Event> events = new ArrayList<>(eventRepo.getEvents().values());

        events.removeIf(event -> event.getDate().isBefore(from));

        events.sort((e1, e2) -> Integer.compare(e2.getSoldTickets(), e1.getSoldTickets()));

        if (events.isEmpty()) {
            printer.printMessage("Няма представления след тази дата.");
            return;
        }

        printer.printMostWatchedEvents(events);
    }

    @Override
    public void mostWatched() {
        List<Event> events = new ArrayList<>(eventRepo.getEvents().values());

        events.sort((e1, e2) -> Integer.compare(e2.getSoldTickets(),e1.getSoldTickets()));

        if(events.isEmpty()){
            printer.printMessage("Няма представления за този период.");
            return;
        }

        printer.printMostWatchedEvents(events);
    }

    @Override
    public void lowAttendance(LocalDate from, LocalDate to) {
        List<Event> events = eventRepo.getEventsBetween(from,to);

        boolean found = false;

        for(Event event:events){
            if(event.getAttendancePercent()<10){
                printer.printMessage(
                        "Представление: " + event.getName() +
                                ", дата: " + event.getDate() +
                                ", зала: " + event.getHall().getHallNumber() +
                                ", посещаемост: " + String.format("%.2f", event.getAttendancePercent()) + "%"
                );

                found = true;
            }
        }

        if (!found) {
            printer.printMessage("Няма представления с посещаемост под 10%.");
        }
    }

    @Override
    public void showEvents() {
        printer.printEvents(eventRepo.getSortedEvents());
    }

    @Override
    public void showHalls() {
        printer.printHalls(hallRepo.getHalls());
    }

    public String exportData(){
        StringBuilder sb = new StringBuilder();

        sb.append("TICKETS_V1").append("\n");

        for(Event event:eventRepo.getEvents().values()){
            sb.append("Event;")
                    .append(event.getDate()).append(";")
                    .append(event.getHall().getHallNumber()).append(";")
                    .append(event.getName())
                    .append("\n");

            for (Ticket ticket : event.getTickets().values()) {
                if (ticket.getStatus() == TicketStatus.FREE) {
                    continue;
                }

                sb.append("TICKET;")
                        .append(event.getDate()).append(";")
                        .append(event.getName()).append(";")
                        .append(ticket.getRow()).append(";")
                        .append(ticket.getSeat()).append(";")
                        .append(ticket.getStatus()).append(";")
                        .append(ticket.getNote() == null || ticket.getNote().isBlank() ? "-" : ticket.getNote()).append(";")
                        .append(ticket.getCode() == null || ticket.getCode().isBlank() ? "-" : ticket.getCode())
                        .append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    public void importData(String content) {
        eventRepo.clear();

        if (content == null || content.isBlank()) {
            return;
        }

        String[] lines = content.split("\\R");

        if (!lines[0].equals("TICKETS_V1")) {
            throw new RuntimeException("Невалиден файлов формат.");
        }

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];

            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split(";", -1);

            if (parts[0].equalsIgnoreCase("EVENT")) {
                LocalDate date = LocalDate.parse(parts[1]);
                int hallNumber = Integer.parseInt(parts[2]);
                String name = parts[3];

                addEvent(date, hallNumber, name);
            } else if (parts[0].equalsIgnoreCase("TICKET")) {
                LocalDate date = LocalDate.parse(parts[1]);
                String eventName = parts[2];
                int row = Integer.parseInt(parts[3]);
                int seat = Integer.parseInt(parts[4]);
                TicketStatus status = TicketStatus.valueOf(parts[5]);

                String note = parts[6].equals("-") ? "" : parts[6];
                String code = parts[7].equals("-") ? "" : parts[7];

                Event event = eventRepo.findByNameAndDate(eventName, date);

                if (event == null) {
                    throw new RuntimeException("Билет към несъществуващо представление: " + eventName);
                }

                event.restoreTicket(row, seat, status, note, code);
            } else {
                throw new RuntimeException("Непознат ред във файла: " + line);
            }
        }
    }

    public boolean eventExists(LocalDate date, String name) {
        return eventRepo.eventExists(date, name);
    }

    public String findClosestEventName(LocalDate date, String input) {
        return eventRepo.findClosestEventName(date, input);
    }

    public void clear() {
        eventRepo.clear();
    }

    private void printBookings(List<TicketDetails> tickets) {
        if (tickets.isEmpty()) {
            printer.printMessage("Няма запазени билети.");
            return;
        }

        tickets.sort(
                Comparator.comparing(TicketDetails::getDate)
                        .thenComparing(TicketDetails::getEventName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(TicketDetails::getHallNumber)
                        .thenComparing(details -> details.getTicket().getRow())
                        .thenComparing(details -> details.getTicket().getSeat())
        );

        printer.printTickets(tickets);
    }
}