package bg.tu_varna.f24621658.sit.repository;

import bg.tu_varna.f24621658.sit.entity.Event;
import bg.tu_varna.f24621658.sit.entity.Ticket;
import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;
import bg.tu_varna.f24621658.sit.entity.keys.EventKey;
import bg.tu_varna.f24621658.sit.entity.keys.SeatKey;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventRepository{
    private Map<EventKey, Event> events;

    public EventRepository() {
        events = new HashMap<>();
    }

    //Трябва командата да връща грешка ако вече съществува др представление на същата дата
    public void save(Event event) {
        if(existsByHallAndDate(event.getHall().getHallNumber(), event.getDate())){
           throw new RuntimeException("Съществува друго представление в същата зала и време.");
        }
        EventKey key = new EventKey(event.getName(),event.getDate());

        if(events.containsKey(key)){
            throw new RuntimeException("Същестувава друго представление със същата дата и име.");
        }
        events.put(key,event);
    }

    public Event findByNameAndDate(String name, LocalDate date){
        return events.get(new EventKey(name,date));
    }

    public boolean existsByNameAndDate(String name, LocalDate date) {
        return events.containsKey(new EventKey(name, date));
    }

    public Event findByHallAndDate(int hallNumber, LocalDate date){
        for(Event e : events.values()){
            if(e.getHall().getHallNumber() == hallNumber &&  e.getDate().equals(date)){
                return e;
            }
        }
        return null;
    }

    public boolean existsByHallAndDate(int hallNumber, LocalDate date){
        for(Event e : events.values()){
            if(e.getHall().getHallNumber() == hallNumber &&  e.getDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    //Търсене на билет по въведен код
    public Ticket findTicketByCode(String code) {
        for (Event event : events.values()) {
            for (Ticket ticket : event.getTickets().values()) {
                if (ticket.getCode() != null && ticket.getCode().equals(code)) {
                    return ticket;
                }
            }
        }
        return null;
    }

    public Map<EventKey, Event> getEvents() {
        return events;
    }

    public List<Ticket> getBookedTickets(String name, LocalDate date){
        Event event = events.get(new EventKey(name, date));

        if (event == null) {
            throw new RuntimeException("Няма такова представление");
        }

        List<Ticket> bookedTickets = new ArrayList<>();

        for (Ticket ticket : event.getTickets().values()) {
            if (ticket.getStatus() == TicketStatus.BOOKED) {
                bookedTickets.add(ticket);
            }
        }

        return bookedTickets;
    }

    public List<Event> getEventsByDate(LocalDate date){
        List<Event> dateEvents = new ArrayList<>();

        for(Event e : events.values()){
            if(e.getDate().equals(date)){
                dateEvents.add(e);
            }
        }

        return dateEvents;
    }

    public List<Event> getEventsByName(String name){
        List<Event> dateEvents = new ArrayList<>();

        for(Event e : events.values()){
            if(e.getName().equals(name)){
                dateEvents.add(e);
            }
        }

        return dateEvents;
    }

    public List<Ticket> getBookedTickets(LocalDate date){
        List<Ticket> bookedTickets = new ArrayList<>();

        for(Event e:getEventsByDate(date)){
            for(Ticket ticket : e.getTickets().values()){
                if(ticket.getStatus() == TicketStatus.BOOKED){
                    bookedTickets.add(ticket);
                }
            }
        }
        return bookedTickets;
    }

    public List<Ticket> getBookedTickets(String name){
        List<Ticket> bookedTickets = new ArrayList<>();

        for(Event e:getEventsByName(name)){
            for(Ticket ticket : e.getTickets().values()){
                if(ticket.getStatus() == TicketStatus.BOOKED){
                    bookedTickets.add(ticket);
                }
            }
        }
        return bookedTickets;
    }
}
