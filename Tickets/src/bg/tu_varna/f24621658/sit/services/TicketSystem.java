package bg.tu_varna.f24621658.sit.services;

import bg.tu_varna.f24621658.sit.entity.*;
import bg.tu_varna.f24621658.sit.entity.contracts.InformationSystemCommands;
import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;
import bg.tu_varna.f24621658.sit.entity.keys.EventKey;
import bg.tu_varna.f24621658.sit.entity.keys.SeatKey;
import bg.tu_varna.f24621658.sit.print.ConsoleInformationPrinter;
import bg.tu_varna.f24621658.sit.print.InformationPrinter;
import bg.tu_varna.f24621658.sit.repository.EventRepository;
import bg.tu_varna.f24621658.sit.repository.HallRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TicketSystem implements InformationSystemCommands {
    private EventRepository eventRepo;
    private HallRepository hallRepo;
    private InformationPrinter printer;

    public TicketSystem() {
        this.eventRepo = new EventRepository();
        this.hallRepo = new HallRepository();
        this.printer = new ConsoleInformationPrinter();
    }

    //addevent - готово
    //freeseats -   готово
    //book  -   готово
    //unbook    -   готово
    //buy   -   готово
    //bookings  -   готово
    //check -   готово
    //report

    @Override
    public void addEvent(LocalDate date, int hall, String name) {
        Hall foundHall = hallRepo.findByNumber(hall);

        if (foundHall == null) {
            throw new RuntimeException("Няма зала с номер " + hall);
        }

        eventRepo.save(new Event(name, foundHall, date));
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
                    //Нарушава SOLID
                    System.out.println("Ред " + row.getRowNumber() +
                            ", място " + seat.getSeatNumber());
                    hasFreeSeats = true;
                }
            }
        }
        //Нарушава SOLID
        if (!hasFreeSeats) {
            System.out.println("Няма свободни места");
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
    public void buy(int row, int seat, LocalDate date, String name) {
        Event event = eventRepo.findByNameAndDate(name, date);
        if (event == null) {
            throw new RuntimeException("Няма такова представление");
        }
        event.buyTicket(row,seat);
    }

    @Override
    public void check(String code) {
        Ticket ticket = eventRepo.findTicketByCode(code);

        if(ticket == null) {
            throw new RuntimeException("Невалиден билетен код!");
        }

        //Това разваля SOLID принципите
        System.out.println("Билетът е валиден за ред " +
                ticket.getRow() + ", място " + ticket.getSeat());
    }

    @Override
    public void bookings(LocalDate date, String name) {
       List<Ticket> bookedTicket = eventRepo.getBookedTickets(name, date);

       if(bookedTicket.isEmpty()) {
           printer.printMessage("Няма запазени билети.");
           return;
       }
       printer.printTickets(bookedTicket);
    }

    @Override
    public void bookings(String name) {
        List<Ticket> bookedTicket = eventRepo.getBookedTickets(name);

        if(bookedTicket.isEmpty()) {
            printer.printMessage("Няма запазени билети.");
            return;
        }
        printer.printTickets(bookedTicket);
    }

    @Override
    public void bookings(LocalDate date) {
        List<Ticket> bookedTicket = eventRepo.getBookedTickets(date);

        if(bookedTicket.isEmpty()) {
            printer.printMessage("Няма запазени билети.");
            return;
        }
        printer.printTickets(bookedTicket);
    }

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
}