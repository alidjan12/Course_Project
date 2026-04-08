package bg.tu_varna.f24621658.sit.repository;

import bg.tu_varna.f24621658.sit.entity.Event;
import bg.tu_varna.f24621658.sit.entity.keys.EventKey;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EventRepository{
    private Map<EventKey, Event> events;

    public EventRepository() {
        events = new HashMap<>();
    }

    //Трябва командата да връща грешка ако вече съществува др представление на същата дата
    public String save(Event event) {
        if(existsByHallAndDate(event.getHall().getHallNumber(), event.getDate())){
           return "Съществува друго представление в същата зала и време.";
        }
        EventKey key = new EventKey(event.getName(),event.getDate());

        if(events.containsKey(key)){
           return "Същестувава друго представление със същата дата и име.";
        }
        events.put(key,event);
        return "";
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

    public Map<EventKey, Event> getEvents() {
        return events;
    }
}
