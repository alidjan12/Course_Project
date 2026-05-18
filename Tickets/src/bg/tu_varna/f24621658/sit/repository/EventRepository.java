package bg.tu_varna.f24621658.sit.repository;

import bg.tu_varna.f24621658.sit.entity.Event;
import bg.tu_varna.f24621658.sit.entity.Hall;
import bg.tu_varna.f24621658.sit.entity.Ticket;
import bg.tu_varna.f24621658.sit.entity.TicketDetails;
import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;
import bg.tu_varna.f24621658.sit.entity.keys.EventKey;
import bg.tu_varna.f24621658.sit.entity.keys.SeatKey;

import java.time.LocalDate;
import java.util.*;

public class EventRepository{
    private Map<EventKey, Event> events;

    public EventRepository() {
        events = new HashMap<>();
    }

    //добавяне на евент
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

    //премахване на евент
    public void remove(LocalDate date, String name) {
        EventKey key = new EventKey(name, date);

        if (!events.containsKey(key)) {
            throw new RuntimeException("Няма такова представление.");
        }

        events.remove(key);
    }

    public Map<EventKey, Event> getEvents() {
        return events;
    }
    //изчиствам паметта
    public void clear(){
        events.clear();
    }

    public Event findByNameAndDate(String name, LocalDate date){
        return events.get(new EventKey(name,date));
    }

    public boolean existsByHallAndDate(int hallNumber, LocalDate date){
        for(Event e : events.values()){
            if(e.getHall().getHallNumber() == hallNumber &&  e.getDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    //Връщам сортиран лист на представленията
    public List<Event> getSortedEvents() {
        List<Event> result = new ArrayList<>(events.values());
        result.sort(eventComparator());
        return result;
    }
    //Връщам списък от представления за дадена дата
    public List<Event> getEventsByDate(LocalDate date){
        List<Event> dateEvents = new ArrayList<>();

        for(Event e : events.values()){
            if(e.getDate().equals(date)){
                dateEvents.add(e);
            }
        }

        return dateEvents;
    }
    //Връщам списък от представления с име ....
    public List<Event> getEventsByName(String name){
        List<Event> dateEvents = new ArrayList<>();

        for(Event e : events.values()){
            if(e.getName().equals(name)){
                dateEvents.add(e);
            }
        }

        return dateEvents;
    }


    public boolean eventExists(LocalDate date, String name) {

        return findByNameAndDate(name, date) != null;
    }

    //Връщам списък с евенти между две дати
    public List<Event> getEventsBetween(LocalDate from, LocalDate to){
        List<Event> result = new ArrayList<>();
        for (Event event : events.values()) {
            boolean isInPeriod = !event.getDate().isBefore(from) && !event.getDate().isAfter(to);

            if(isInPeriod){
                result.add(event);
            }
        }
        return result;
    }
    //Намирам детайлите на билет по кода
    public TicketDetails findTicketDetailsByCode(String code) {
        for (Event event : events.values()) {
            for (Ticket ticket : event.getTickets().values()) {
                if (ticket.getCode() != null && ticket.getCode().equals(code)) {
                    return new TicketDetails(event, ticket);
                }
            }
        }

        return null;
    }

    public List<TicketDetails> getBookedTickets() {
        List<TicketDetails> result = new ArrayList<>();

        for (Event event : getSortedEvents()) {
            addBookedTickets(result, event);
        }

        sortTicketDetails(result);
        return result;
    }

    public List<TicketDetails> getBookedTickets(LocalDate date) {
        List<TicketDetails> result = new ArrayList<>();

        for (Event event : getEventsByDate(date)) {
            addBookedTickets(result, event);
        }

        sortTicketDetails(result);
        return result;
    }

    public List<TicketDetails> getBookedTickets(String name) {
        List<TicketDetails> result = new ArrayList<>();

        for (Event event : getEventsByName(name)) {
            addBookedTickets(result, event);
        }

        sortTicketDetails(result);
        return result;
    }

    public List<TicketDetails> getBookedTickets(String name, LocalDate date) {
        Event event = events.get(new EventKey(name, date));

        if (event == null) {
            throw new RuntimeException("Няма такова представление.");
        }

        List<TicketDetails> result = new ArrayList<>();
        addBookedTickets(result, event);
        sortTicketDetails(result);
        return result;
    }

    public String findClosestEventName(LocalDate date, String input) {
        String closestName = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Event event : getEvents().values()) {
            if (!event.getDate().equals(date)) {
                continue;
            }

            int distance = levenshteinDistance(
                    event.getName().toLowerCase(),
                    input.toLowerCase()
            );

            if (distance < bestDistance) {
                bestDistance = distance;
                closestName = event.getName();
            }
        }

        return closestName;
    }

    //
    private void addBookedTickets(List<TicketDetails> result, Event event) {
        for (Ticket ticket : event.getTickets().values()) {
            if (ticket.getStatus() == TicketStatus.BOOKED) {
                result.add(new TicketDetails(event, ticket));
            }
        }
    }

    //сортирам билети по дата, представление, зала, редица, място
    private void sortTicketDetails(List<TicketDetails> tickets) {
        tickets.sort(Comparator
                .comparing(TicketDetails::getDate)
                .thenComparing(TicketDetails::getEventName)
                .thenComparingInt(TicketDetails::getHallNumber)
                .thenComparingInt(item -> item.getTicket().getRow())
                .thenComparingInt(item -> item.getTicket().getSeat()));
    }

    //Подредени по дата, име, зала
    private Comparator<Event> eventComparator() {
        return Comparator
                .comparing(Event::getDate)
                .thenComparing(Event::getName)
                .thenComparingInt(event -> event.getHall().getHallNumber());
    }

    //
    private int levenshteinDistance(String first, String second) {
        int[][] dp = new int[first.length() + 1][second.length() + 1];

        for (int i = 0; i <= first.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= second.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                int cost = first.charAt(i - 1) == second.charAt(j - 1) ? 0 : 1;

                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }

        return dp[first.length()][second.length()];
    }
}
