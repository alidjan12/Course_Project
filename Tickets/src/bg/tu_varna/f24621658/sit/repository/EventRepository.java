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

/**
 * Хранилище за представленията в системата.
 * Пази представленията в Map по ключ от име и дата и предоставя
 * методи за добавяне, премахване, търсене, филтриране и справки за билети.
 */
public class EventRepository{
    private Map<EventKey, Event> events;

    /**
     * Създава празно хранилище за представления.
     */
    public EventRepository() {
        events = new HashMap<>();
    }

    /**
     * Добавя ново представление след проверки за дублиране.
     * Не позволява две представления в една и съща зала на една дата,
     * както и две представления със същото име и дата.
     *
     * @param event представлението, което се добавя.
     */
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

    /**
     * Премахва представление по дата и име.
     * Ако такова представление не съществува, се хвърля грешка.
     *
     * @param date датата на представлението.
     * @param name името на представлението.
     */
    public void remove(LocalDate date, String name) {
        EventKey key = new EventKey(name, date);

        if (!events.containsKey(key)) {
            throw new RuntimeException("Няма такова представление.");
        }

        events.remove(key);
    }

    /**
     * Връща всички съхранени представления.
     *
     * @return Map с представленията, където ключът е комбинация от име и дата.
     */
    public Map<EventKey, Event> getEvents() {
        return events;
    }

    /**
     * Изчиства всички представления от хранилището.
     */
    public void clear(){
        events.clear();
    }

    /**
     * Намира представление по точно име и дата.
     *
     * @param name името на представлението.
     * @param date датата на представлението.
     * @return намереното представление или null, ако липсва.
     */
    public Event findByNameAndDate(String name, LocalDate date){
        return events.get(new EventKey(name,date));
    }

    /**
     * Проверява дали дадена зала вече е заета на посочената дата.
     *
     * @param hallNumber номерът на залата.
     * @param date датата, която се проверява.
     * @return true, ако има представление в тази зала на тази дата.
     */
    public boolean existsByHallAndDate(int hallNumber, LocalDate date){
        for(Event e : events.values()){
            if(e.getHall().getHallNumber() == hallNumber &&  e.getDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    /**
     * Връща всички представления, сортирани по дата, име и номер на зала.
     *
     * @return сортиран списък с представления.
     */
    public List<Event> getSortedEvents() {
        List<Event> result = new ArrayList<>(events.values());
        result.sort(eventComparator());
        return result;
    }
    /**
     * Връща представленията за конкретна дата.
     *
     * @param date датата, по която се филтрира.
     * @return списък с представления на тази дата.
     */
    public List<Event> getEventsByDate(LocalDate date){
        List<Event> dateEvents = new ArrayList<>();

        for(Event e : events.values()){
            if(e.getDate().equals(date)){
                dateEvents.add(e);
            }
        }

        return dateEvents;
    }
    /**
     * Връща представленията с конкретно име.
     * Може да върне няколко резултата, ако същото представление има различни дати.
     *
     * @param name името, по което се филтрира.
     * @return списък с представления с това име.
     */
    public List<Event> getEventsByName(String name){
        List<Event> dateEvents = new ArrayList<>();

        for(Event e : events.values()){
            if(e.getName().equals(name)){
                dateEvents.add(e);
            }
        }

        return dateEvents;
    }

    /**
     * Проверява дали съществува представление с дадени дата и име.
     *
     * @param date датата на представлението.
     * @param name името на представлението.
     * @return true, ако представлението съществува.
     */
    public boolean eventExists(LocalDate date, String name) {

        return findByNameAndDate(name, date) != null;
    }

    /**
     * Връща представленията в зададен период, включително началната и крайната дата.
     *
     * @param from начална дата.
     * @param to крайна дата.
     * @return списък с представления в периода.
     */
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
    /**
     * Търси билет по уникален код.
     * Обхожда всички представления и техните билети, докато намери съвпадение.
     *
     * @param code кодът на закупения билет.
     * @return детайли за билета или null, ако кодът не е намерен.
     */
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

    /**
     * Връща всички резервирани билети в системата.
     * Резултатът се сортира по дата, представление, зала, ред и място.
     *
     * @return сортиран списък с резервирани билети.
     */
    public List<TicketDetails> getBookedTickets() {
        List<TicketDetails> result = new ArrayList<>();

        for (Event event : getSortedEvents()) {
            addBookedTickets(result, event);
        }

        sortTicketDetails(result);
        return result;
    }

    /**
     * Връща резервираните билети за конкретна дата.
     *
     * @param date датата, по която се филтрират билетите.
     * @return сортиран списък с резервирани билети за датата.
     */
    public List<TicketDetails> getBookedTickets(LocalDate date) {
        List<TicketDetails> result = new ArrayList<>();

        for (Event event : getEventsByDate(date)) {
            addBookedTickets(result, event);
        }

        sortTicketDetails(result);
        return result;
    }

    /**
     * Връща резервираните билети за представления с конкретно име.
     *
     * @param name името на представлението.
     * @return сортиран списък с резервирани билети.
     */
    public List<TicketDetails> getBookedTickets(String name) {
        List<TicketDetails> result = new ArrayList<>();

        for (Event event : getEventsByName(name)) {
            addBookedTickets(result, event);
        }

        sortTicketDetails(result);
        return result;
    }

    /**
     * Връща резервираните билети за точно определено представление.
     * Ако представлението не съществува, се хвърля грешка.
     *
     * @param name името на представлението.
     * @param date датата на представлението.
     * @return сортиран списък с резервирани билети.
     */
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

    /**
     * Намира най-близкото име на представление за дадена дата.
     * Използва редакционна дистанция, за да предложи корекция при грешно въведено име.
     *
     * @param date датата, за която се търси представление.
     * @param input въведеният от потребителя текст.
     * @return най-близкото име или null, ако няма представления за датата.
     */
    public String findClosestEventName(LocalDate date, String input) {
        String closestName = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Event event : getEvents().values()) {
            // Проверяват се само представленията за зададената дата.
            if (!event.getDate().equals(date)) {
                continue;
            }

            int distance = levenshteinDistance(
                    event.getName().toLowerCase(),
                    input.toLowerCase()
            );
            // Запазва се името с най-малка разлика спрямо въведения текст.
            if (distance < bestDistance) {
                bestDistance = distance;
                closestName = event.getName();
            }
        }

        return closestName;
    }

    /**
     * Добавя към резултата билетите със статус BOOKED от дадено представление.
     *
     * @param result списъкът, към който се добавят билетите.
     * @param event представлението, от което се вземат билетите.
     */
    private void addBookedTickets(List<TicketDetails> result, Event event) {
        for (Ticket ticket : event.getTickets().values()) {
            if (ticket.getStatus() == TicketStatus.BOOKED) {
                result.add(new TicketDetails(event, ticket));
            }
        }
    }

    /**
     * Сортира билетите по дата, име на представление, зала, ред и място.
     *
     * @param tickets списъкът с билети, който се сортира.
     */
    private void sortTicketDetails(List<TicketDetails> tickets) {
        tickets.sort(Comparator
                .comparing(TicketDetails::getDate)
                .thenComparing(TicketDetails::getEventName)
                .thenComparingInt(TicketDetails::getHallNumber)
                .thenComparingInt(item -> item.getTicket().getRow())
                .thenComparingInt(item -> item.getTicket().getSeat()));
    }

    /**
     * Създава comparator за сортиране на представления по дата, име и зала.
     *
     * @return comparator за подреждане на представления.
     */
    private Comparator<Event> eventComparator() {
        return Comparator
                .comparing(Event::getDate)
                .thenComparing(Event::getName)
                .thenComparingInt(event -> event.getHall().getHallNumber());
    }

    /**
     * Изчислява редакционната дистанция на Левенщайн между два текста.
     * Използва се за намиране на най-близко име при грешно въведено представление.
     *
     * @param first първият текст.
     * @param second вторият текст.
     * @return минималният брой редакции между двата текста.
     */
    private int levenshteinDistance(String first, String second) {
        int[][] dp = new int[first.length() + 1][second.length() + 1];

        // Първа колона: преобразуване към празен текст чрез изтриване.
        for (int i = 0; i <= first.length(); i++) {
            dp[i][0] = i;
        }
        // Първи ред: преобразуване от празен текст чрез добавяне.
        for (int j = 0; j <= second.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                int cost = first.charAt(i - 1) == second.charAt(j - 1) ? 0 : 1;

        // Избира се най-евтината операция: изтриване, добавяне или замяна.
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }

        return dp[first.length()][second.length()];
    }
}
