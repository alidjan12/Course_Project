package bg.tu_varna.f24621658.sit.services;

import bg.tu_varna.f24621658.sit.entity.Event;
import bg.tu_varna.f24621658.sit.entity.Hall;
import bg.tu_varna.f24621658.sit.entity.contracts.InformationSystemCommands;
import bg.tu_varna.f24621658.sit.repository.EventRepository;
import bg.tu_varna.f24621658.sit.repository.HallRepository;

import java.time.LocalDate;

public class TicketSystem implements InformationSystemCommands {
    private EventRepository eventRepo;
    private HallRepository hallRepo;

    public TicketSystem() {
        this.eventRepo = new EventRepository();
        this.hallRepo = new HallRepository();
    }

    @Override
    public void addEvent(LocalDate date, Hall hall, String name) {
        eventRepo.save(new Event(name,hall,date));
    }

    @Override
    public void freeSeats(LocalDate date, String name) {
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
}