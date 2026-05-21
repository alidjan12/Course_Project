package bg.tu_varna.f24621658.sit.repository;

import bg.tu_varna.f24621658.sit.entity.Event;
import bg.tu_varna.f24621658.sit.entity.Ticket;
import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;

import java.time.LocalDate;
import java.util.*;

/**
 * Хранилище за представленията в системата.
 * Пази представленията в Map по ключ от име и дата и предоставя
 * методи за добавяне, премахване, търсене, филтриране и справки за билети.
 */
public class EventRepository {
    private final List<Event> events;
    /**
     * Създава ново хранилище за представления.
     * Инициализира празен списък, в който се пазят всички представления.
     */
    public EventRepository() {
        events = new ArrayList<>();
    }

    /**
     * Записва ново представление в хранилището.
     * Проверява дали представлението не е null, дали няма друго представление
     * в същата зала на същата дата и дали няма представление със същото име и дата.
     *
     * @param event представлението, което трябва да бъде добавено
     */
    public void save(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("event is null");
        }
        if (existsByHallAndDate(event.getHall().getHallNumber(), event.getDate())) {
            throw new RuntimeException("Съществува друго представление в същата зала и време.");
        }
        if (findByNameAndDate(event.getName(), event.getDate()) != null) {
            throw new RuntimeException("Съществува друго представление със същата дата и име.");
        }
        events.add(event);
    }

    /**
     * Премахва представление от хранилището по дата и име.
     *
     * @param date датата на представлението
     * @param name името на представлението
     */
    public void remove(LocalDate date, String name) {
        Event event = findByNameAndDate(name, date);
        if (event == null) {
            throw new RuntimeException("Няма такова представление.");
        }
        events.remove(event);
    }
    /**
     * Връща всички представления, записани в хранилището.
     *
     * @return списък с всички представления
     */
    public List<Event> getEvents() {
        return events;
    }
    /**
     * Изчиства всички представления от хранилището.
     */
    public void clear() {
        events.clear();
    }
    /**
     * Търси представление по име и дата.
     *
     * @param name името на представлението
     * @param date датата на представлението
     * @return намереното представление или null, ако не съществува
     */
    public Event findByNameAndDate(String name, LocalDate date) {
        for (Event event : events) {
            if (event.hasSameIdentity(name, date)) {
                return event;
            }
        }
        return null;
    }
    /**
     * Проверява дали вече съществува представление в подадената зала на подадената дата.
     *
     * @param hallNumber номерът на залата
     * @param date датата на представлението
     * @return true, ако съществува представление в тази зала и дата; false в противен случай
     */
    public boolean existsByHallAndDate(int hallNumber, LocalDate date) {
        for (Event event : events) {
            if (event.getHall().getHallNumber() == hallNumber && event.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Връща всички представления, сортирани по дата, име и номер на зала.
     *
     * @return сортиран списък с представления
     */
    public List<Event> getSortedEvents() {
        List<Event> result = new ArrayList<>(events);
        result.sort(eventComparator());
        return result;
    }
    /**
     * Връща всички представления за подадена дата.
     *
     * @param date датата, по която се филтрират представленията
     * @return списък с представления за подадената дата
     */
    public List<Event> getEventsByDate(LocalDate date) {
        List<Event> result = new ArrayList<>();
        for (Event event : events) {
            if (event.getDate().equals(date)) {
                result.add(event);
            }
        }
        return result;
    }
    /**
     * Връща всички представления с подаденото име.
     *
     * @param name името, по което се филтрират представленията
     * @return списък с представления с подаденото име
     */
    public List<Event> getEventsByName(String name) {
        List<Event> result = new ArrayList<>();
        for (Event event : events) {
            if (event.getName().equals(name)) {
                result.add(event);
            }
        }
        return result;
    }
    /**
     * Проверява дали съществува представление с подадените дата и име.
     *
     * @param date датата на представлението
     * @param name името на представлението
     * @return true, ако представлението съществува; false в противен случай
     */
    public boolean eventExists(LocalDate date, String name) {
        return findByNameAndDate(name, date) != null;
    }
    /**
     * Връща всички представления в зададен период.
     * Включва както началната, така и крайната дата.
     *
     * @param from начална дата на периода
     * @param to крайна дата на периода
     * @return списък с представления в зададения период
     */
    public List<Event> getEventsBetween(LocalDate from, LocalDate to) {
        List<Event> result = new ArrayList<>();
        for (Event event : events) {
            boolean isInPeriod = !event.getDate().isBefore(from) && !event.getDate().isAfter(to);
            if (isInPeriod) {
                result.add(event);
            }
        }
        return result;
    }
    /**
     * Търси закупен билет по неговия код.
     *
     * @param code кодът на билета
     * @return намереният билет или null, ако няма билет с такъв код
     */
    public Ticket findTicketByCode(String code) {
        for (Event event : events) {
            for (Ticket ticket : event.getTickets()) {
                if (ticket.getCode() != null && ticket.getCode().equals(code)) {
                    return ticket;
                }
            }
        }
        return null;
    }
    /**
     * Връща всички запазени билети в системата.
     * Билетите се сортират по дата, име на представление, зала, ред и място.
     *
     * @return списък със запазени билети
     */
    public List<Ticket> getBookedTickets() {
        List<Ticket> result = new ArrayList<>();
        for (Event event : getSortedEvents()) {
            addBookedTickets(result, event);
        }
        sortTickets(result);
        return result;
    }
    /**
     * Връща всички запазени билети за подадена дата.
     *
     * @param date датата, по която се филтрират билетите
     * @return списък със запазени билети за подадената дата
     */
    public List<Ticket> getBookedTickets(LocalDate date) {
        List<Ticket> result = new ArrayList<>();
        for (Event event : getEventsByDate(date)) {
            addBookedTickets(result, event);
        }
        sortTickets(result);
        return result;
    }
    /**
     * Връща всички запазени билети за представления с подаденото име.
     *
     * @param name името на представлението
     * @return списък със запазени билети за подаденото име
     */
    public List<Ticket> getBookedTickets(String name) {
        List<Ticket> result = new ArrayList<>();
        for (Event event : getEventsByName(name)) {
            addBookedTickets(result, event);
        }
        sortTickets(result);
        return result;
    }
    /**
     * Връща всички запазени билети за конкретно представление на конкретна дата.
     *
     * @param name името на представлението
     * @param date датата на представлението
     * @return списък със запазени билети за конкретното представление
     */
    public List<Ticket> getBookedTickets(String name, LocalDate date) {
        Event event = findByNameAndDate(name, date);
        if (event == null) {
            throw new RuntimeException("Няма такова представление.");
        }
        List<Ticket> result = new ArrayList<>();
        addBookedTickets(result, event);
        sortTickets(result);
        return result;
    }
    /**
     * Намира най-близкото име на представление до подадения текст за конкретна дата.
     * Използва разстояние на Левенщайн за сравнение между имената.
     *
     * @param date датата, на която се търси представлението
     * @param input въведеният от потребителя текст
     * @return най-близкото намерено име или null, ако няма представления за датата
     */
    public String findClosestEventName(LocalDate date, String input) {
        String closestName = null;
        int bestDistance = Integer.MAX_VALUE;
        for (Event event : events) {
            if (!event.getDate().equals(date)) {
                continue;
            }
            int distance = levenshteinDistance(event.getName().toLowerCase(), input.toLowerCase());
            if (distance < bestDistance) {
                bestDistance = distance;
                closestName = event.getName();
            }
        }
        return closestName;
    }
    /**
     * Добавя към подадения списък всички запазени билети за конкретно представление.
     *
     * @param result списъкът, към който се добавят намерените билети
     * @param event представлението, чиито запазени билети се търсят
     */
    private void addBookedTickets(List<Ticket> result, Event event) {
        for (Ticket ticket : event.getTickets()) {
            if (ticket.getStatus() == TicketStatus.BOOKED) {
                result.add(ticket);
            }
        }
    }
    /**
     * Сортира списък с билети по дата, име на представление, зала, ред и място.
     *
     * @param tickets списъкът с билети, който трябва да бъде сортиран
     */
    private void sortTickets(List<Ticket> tickets) {
        tickets.sort(Comparator
                .comparing((Ticket ticket) -> ticket.getEvent().getDate())
                .thenComparing(ticket -> ticket.getEvent().getName())
                .thenComparingInt(ticket -> ticket.getEvent().getHall().getHallNumber())
                .thenComparingInt(ticket -> ticket.getSeat().getRowNumber())
                .thenComparingInt(ticket -> ticket.getSeat().getSeatNumber()));
    }
    /**
     * Създава компаратор за сортиране на представления.
     * Представленията се подреждат по дата, име и номер на зала.
     *
     * @return компаратор за представления
     */
    private Comparator<Event> eventComparator() {
        return Comparator
                .comparing(Event::getDate)
                .thenComparing(Event::getName)
                .thenComparingInt(event -> event.getHall().getHallNumber());
    }
    /**
     * Изчислява разстоянието на Левенщайн между два текста.
     * Използва се за намиране на най-близко име при грешно въведено представление.
     *
     * @param first първият текст за сравнение
     * @param second вторият текст за сравнение
     * @return броят операции, нужни за преобразуване на единия текст в другия
     */
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
