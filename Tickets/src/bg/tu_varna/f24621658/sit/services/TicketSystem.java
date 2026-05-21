package bg.tu_varna.f24621658.sit.services;

import bg.tu_varna.f24621658.sit.entity.*;
import bg.tu_varna.f24621658.sit.entity.contracts.InformationSystemCommands;
import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;
import bg.tu_varna.f24621658.sit.print.InformationPrinter;
import bg.tu_varna.f24621658.sit.repository.EventRepository;
import bg.tu_varna.f24621658.sit.repository.HallRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 * Основен сервиз за управление на билетната система.
 * Обединява работата с представления, зали, резервации,
 * продажби, справки, импорт и експорт на данни.
 */
public class TicketSystem implements InformationSystemCommands {
    private final EventRepository eventRepo;
    private final HallRepository hallRepo;
    private final InformationPrinter printer;

    /**
     * Създава нова билетна система.
     * Инициализира хранилищата за представления и зали,
     * както и обекта за извеждане на информация.
     *
     * @param printer обектът, който извежда информация към потребителя
     */
    public TicketSystem(InformationPrinter printer) {
        this.eventRepo = new EventRepository();
        this.hallRepo = new HallRepository();
        this.printer = printer;
    }
    /**
     * Добавя ново представление в системата.
     * Намира залата по номер и създава представление за подадената дата и име.
     *
     * @param date датата на представлението
     * @param hall номерът на залата
     * @param name името на представлението
     */
    @Override
    public void addEvent(LocalDate date, int hall, String name) {
        Hall foundHall = hallRepo.findByNumber(hall);
        if (foundHall == null) {
            throw new RuntimeException("Няма зала с номер " + hall);
        }
        eventRepo.save(new Event(name, foundHall, date));
    }
    /**
     * Премахва представление от системата по дата и име.
     *
     * @param date датата на представлението
     * @param name името на представлението
     */
    @Override
    public void removeEvent(LocalDate date, String name) {
        eventRepo.remove(date, name);
        printer.printMessage("Представлението е свалено успешно.");
    }
    /**
     * Показва свободните места за конкретно представление.
     * Местата се извеждат по редове, за да се вижда разположението им в залата.
     *
     * @param date датата на представлението
     * @param name името на представлението
     */
    @Override
    public void freeSeats(LocalDate date, String name) {
        Event event = eventRepo.findByNameAndDate(name, date);
        if (event == null) {
            throw new RuntimeException("Няма такова представление!");
        }

        Hall hall = event.getHall();
        boolean hasFreeSeats = false;

        printer.printMessage("Представление: " + event.getName());
        printer.printMessage("Дата: " + event.getDate());
        printer.printMessage("Зала " + hall.getHallNumber());
        printer.printMessage("Свободни места: " + event.getFreeSeatsCount());

        for (Row row : hall.getRows()) {
            StringBuilder line = new StringBuilder();
            line.append("Ред ").append(row.getRowNumber()).append(": ");
            boolean hasFreeSeatsOnRow = false;

            for (Seat seat : row.getSeats()) {
                if (seat.isFree()) {
                    line.append("[").append(seat.getSeatNumber()).append("]");
                    hasFreeSeats = true;
                    hasFreeSeatsOnRow = true;
                }
            }

            if (hasFreeSeatsOnRow) {
                printer.printMessage(line.toString());
            }
        }

        if (!hasFreeSeats) {
            printer.printMessage("Няма свободни места");
        }
    }
    /**
     * Запазва билет за конкретно представление, ред и място.
     * Към резервацията се добавя и бележка.
     *
     * @param row номерът на реда
     * @param seat номерът на мястото
     * @param date датата на представлението
     * @param name името на представлението
     * @param note бележката към резервацията
     */
    @Override
    public void book(int row, int seat, LocalDate date, String name, String note) {
        Event event = eventRepo.findByNameAndDate(name, date);
        if (event == null) {
            throw new RuntimeException("Няма такова представление");
        }
        event.bookTicket(row, seat, note);
    }
    /**
     * Отменя резервация за конкретно представление, ред и място.
     *
     * @param row номерът на реда
     * @param seat номерът на мястото
     * @param date датата на представлението
     * @param name името на представлението
     */
    @Override
    public void unbook(int row, int seat, LocalDate date, String name) {
        Event event = eventRepo.findByNameAndDate(name, date);
        if (event == null) {
            throw new RuntimeException("Няма такова представление");
        }
        event.unBookTicket(row, seat);
    }
    /**
     * Закупува билет за конкретно представление, ред и място.
     * При успешно закупуване връща генерирания код на билета.
     *
     * @param row номерът на реда
     * @param seat номерът на мястото
     * @param date датата на представлението
     * @param name името на представлението
     * @return генерираният код на закупения билет
     */
    @Override
    public String buy(int row, int seat, LocalDate date, String name) {
        Event event = eventRepo.findByNameAndDate(name, date);
        if (event == null) {
            throw new RuntimeException("Няма такова представление.");
        }
        return event.buyTicket(row, seat);
    }
    /**
     * Проверява дали подаденият код принадлежи на закупен билет.
     * Ако кодът е валиден, извежда информация за представление, дата, зала, ред и място.
     *
     * @param code кодът на билета
     */
    @Override
    public void check(String code) {
        Ticket ticket = eventRepo.findTicketByCode(code);
        if (ticket == null) {
            throw new RuntimeException("Невалиден билетен код!");
        }

        Event event = ticket.getEvent();
        Seat seat = ticket.getSeat();
        printer.printMessage(
                "Билетът е валиден за представление " +
                        event.getName() +
                        " на дата " + event.getDate() +
                        ", зала " + event.getHall().getHallNumber() +
                        ", ред " + seat.getRowNumber() +
                        ", място " + seat.getSeatNumber()
        );
    }
    /**
     * Показва запазените билети за конкретно представление на конкретна дата.
     *
     * @param date датата на представлението
     * @param name името на представлението
     */
    @Override
    public void bookings(LocalDate date, String name) {
        printBookings(eventRepo.getBookedTickets(name, date));
    }
    /**
     * Показва всички запазени билети за представление с подадено име.
     *
     * @param name името на представлението
     */
    @Override
    public void bookings(String name) {
        printBookings(eventRepo.getBookedTickets(name));
    }
    /**
     * Показва всички запазени билети за подадена дата.
     *
     * @param date датата, по която се филтрират билетите
     */
    @Override
    public void bookings(LocalDate date) {
        printBookings(eventRepo.getBookedTickets(date));
    }
    /**
     * Показва всички запазени билети в системата.
     */
    @Override
    public void bookings() {
        printBookings(eventRepo.getBookedTickets());
    }
    /**
     * Извежда справка за продадените билети в зададен период и конкретна зала.
     *
     * @param from начална дата на периода
     * @param to крайна дата на периода
     * @param hallNumber номерът на залата
     */
    @Override
    public void report(LocalDate from, LocalDate to, int hallNumber) {
        boolean found = false;
        for (Event event : eventRepo.getEvents()) {
            boolean isInPeriod = !event.getDate().isBefore(from) && !event.getDate().isAfter(to);
            boolean isInHall = event.getHall().getHallNumber() == hallNumber;
            if (isInPeriod && isInHall) {
                printer.printReport(event, event.getSoldTickets());
                found = true;
            }
        }
        if (!found) {
            printer.printMessage("Няма продадени билети за този период и зала.");
        }
    }
    /**
     * Извежда справка за продадените билети в зададен период за всички зали.
     *
     * @param from начална дата на периода
     * @param to крайна дата на периода
     */
    @Override
    public void report(LocalDate from, LocalDate to) {
        boolean found = false;
        for (Event event : eventRepo.getEvents()) {
            boolean isInPeriod = !event.getDate().isBefore(from) && !event.getDate().isAfter(to);
            if (isInPeriod) {
                printer.printReport(event, event.getSoldTickets());
                found = true;
            }
        }
        if (!found) {
            printer.printMessage("Няма продадени билети за този период.");
        }
    }
    /**
     * Показва най-гледаните представления за зададен период.
     * Представленията се сортират по брой продадени билети в намаляващ ред.
     *
     * @param from начална дата на периода
     * @param to крайна дата на периода
     */
    @Override
    public void mostWatched(LocalDate from, LocalDate to) {
        List<Event> events = eventRepo.getEventsBetween(from, to);
        events.sort((e1, e2) -> Integer.compare(e2.getSoldTickets(), e1.getSoldTickets()));
        if (events.isEmpty()) {
            printer.printMessage("Няма представления за този период.");
            return;
        }
        printer.printMostWatchedEvents(events);
    }
    /**
     * Показва най-гледаните представления от подадена дата нататък.
     *
     * @param from началната дата, от която започва филтрирането
     */
    @Override
    public void mostWatched(LocalDate from) {
        List<Event> events = new ArrayList<>(eventRepo.getEvents());
        events.removeIf(event -> event.getDate().isBefore(from));
        events.sort((e1, e2) -> Integer.compare(e2.getSoldTickets(), e1.getSoldTickets()));
        if (events.isEmpty()) {
            printer.printMessage("Няма представления след тази дата.");
            return;
        }
        printer.printMostWatchedEvents(events);
    }
    /**
     * Показва всички представления, сортирани по брой продадени билети.
     */
    @Override
    public void mostWatched() {
        List<Event> events = new ArrayList<>(eventRepo.getEvents());
        events.sort((e1, e2) -> Integer.compare(e2.getSoldTickets(), e1.getSoldTickets()));
        if (events.isEmpty()) {
            printer.printMessage("Няма представления за този период.");
            return;
        }
        printer.printMostWatchedEvents(events);
    }
    /**
     * Показва представленията с посещаемост под 10% за зададен период.
     *
     * @param from начална дата на периода
     * @param to крайна дата на периода
     */
    @Override
    public void lowAttendance(LocalDate from, LocalDate to) {
        List<Event> events = eventRepo.getEventsBetween(from, to);
        boolean found = false;

        for (Event event : events) {
            if (event.getAttendancePercent() < 10) {
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
    /**
     * Показва всички представления, подредени по дата, име и зала.
     */
    @Override
    public void showEvents() {
        printer.printEvents(eventRepo.getSortedEvents());
    }
    /**
     * Показва всички предварително зададени зали в системата.
     */
    @Override
    public void showHalls() {
        printer.printHalls(hallRepo.getHalls());
    }
    /**
     * Преобразува текущото състояние на системата в текстов формат за запис във файл.
     * Записват се представленията и всички билети, които не са свободни.
     *
     * @return текстово представяне на данните в системата
     */
    public String exportData() {
        StringBuilder sb = new StringBuilder();
        sb.append("TICKETS_V1").append("\n");

        for (Event event : eventRepo.getEvents()) {
            sb.append("Event;")
                    .append(event.getDate()).append(";")
                    .append(event.getHall().getHallNumber()).append(";")
                    .append(event.getName())
                    .append("\n");

            for (Ticket ticket : event.getTickets()) {
                if (ticket.getStatus() == TicketStatus.FREE) {
                    continue;
                }

                Seat seat = ticket.getSeat();
                sb.append("TICKET;")
                        .append(event.getDate()).append(";")
                        .append(event.getName()).append(";")
                        .append(seat.getRowNumber()).append(";")
                        .append(seat.getSeatNumber()).append(";")
                        .append(ticket.getStatus()).append(";")
                        .append(ticket.getNote() == null || ticket.getNote().isBlank() ? "-" : ticket.getNote()).append(";")
                        .append(ticket.getCode() == null || ticket.getCode().isBlank() ? "-" : ticket.getCode())
                        .append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
    /**
     * Зарежда данни от текстов файл.
     * Възстановява представленията и билетите от записания файлов формат.
     *
     * @param content съдържанието на файла като текст
     */
    public void importData(String content) {
        eventRepo.clear();
        if (content == null || content.isBlank()) {
            return;
        }

        String[] lines = content.split("\\R");
        if (!lines[0].trim().equals("TICKETS_V1")) {
            throw new RuntimeException("Невалиден файлов формат.");
        }

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split(";", -1);
            String lineType = parts[0].trim();
            int lineNumber = i + 1;

            if (lineType.equalsIgnoreCase("EVENT")) {
                importEventLine(parts, lineNumber);
            } else if (lineType.equalsIgnoreCase("TICKET")) {
                importTicketLine(parts, lineNumber);
            } else {
                throw new RuntimeException("Грешка на ред " + lineNumber + ": Непознат ред във файла.");
            }
        }
    }
    /**
     * Обработва един ред от файла, който съдържа информация за представление.
     *
     * @param parts частите на реда, разделени със символ ;
     * @param lineNumber номерът на реда във файла
     */
    private void importEventLine(String[] parts, int lineNumber) {
        try {
            // Един ред за представление трябва да съдържа точно 4 части.
            if (parts.length != 4) {
                throw new RuntimeException("Невалиден ред за представление.");
            }
            LocalDate date = LocalDate.parse(parts[1]);
            int hallNumber = Integer.parseInt(parts[2]);
            String name = parts[3];
            addEvent(date, hallNumber, name);
        } catch (RuntimeException e) {
            throw new RuntimeException("Грешка на ред " + lineNumber + ": " + e.getMessage());
        }
    }
    /**
     * Обработва един ред от файла, който съдържа информация за билет.
     * При грешка редът се пропуска, без да се прекратява зареждането.
     *
     * @param parts частите на реда, разделени със символ ;
     * @param lineNumber номерът на реда във файла
     */
    private void importTicketLine(String[] parts, int lineNumber) {
        try {
            // Един ред за билет трябва да съдържа точно 8 части.
            if (parts.length != 8) {
                throw new RuntimeException("Невалиден ред за билет.");
            }

            LocalDate date = LocalDate.parse(parts[1]);
            String eventName = parts[2];
            int row = Integer.parseInt(parts[3]);
            int seat = Integer.parseInt(parts[4]);
            TicketStatus status = TicketStatus.valueOf(parts[5]);
            String note = parts[6].equals("-") ? "" : parts[6];
            String code = parts[7].equals("-") ? "" : parts[7];
            Event event = eventRepo.findByNameAndDate(eventName, date);

            // Ако представлението не съществува, пропускаме само този билет.
            if (event == null) {
                printer.printMessage(
                        "Грешка на ред " + lineNumber +
                                ": билет към несъществуващо представление '" + eventName +
                                "' на дата " + date + ". Редът е пропуснат."
                );
                return;
            }

            // Възстановявам билета към намереното представление.
            event.restoreTicket(row, seat, status, note, code);
        } catch (RuntimeException e) {
            printer.printMessage("Грешка на ред " + lineNumber + ": " + e.getMessage() + " Редът е пропуснат.");
        }
    }
    /**
     * Проверява дали съществува представление с подадените дата и име.
     *
     * @param date датата на представлението
     * @param name името на представлението
     * @return true, ако представлението съществува; false в противен случай
     */
    public boolean eventExists(LocalDate date, String name) {
        return eventRepo.eventExists(date, name);
    }
    /**
     * Намира най-близкото име на представление до въведения текст.
     * Използва се при помощ за грешно изписано име на представление.
     *
     * @param date датата, на която се търси представлението
     * @param input въведеният от потребителя текст
     * @return най-близкото намерено име или null, ако няма подходящо
     */
    public String findClosestEventName(LocalDate date, String input) {
        return eventRepo.findClosestEventName(date, input);
    }
    /**
     * Изчиства всички заредени представления от системата.
     */
    public void clear() {
        eventRepo.clear();
    }
    /**
     * Сортира и извежда списък със запазени билети.
     * Билетите се подреждат по дата, име на представление, зала, ред и място.
     *
     * @param tickets списъкът със запазени билети
     */
    private void printBookings(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            printer.printMessage("Няма запазени билети.");
            return;
        }

        tickets.sort(
                Comparator.comparing((Ticket ticket) -> ticket.getEvent().getDate())
                        .thenComparing(ticket -> ticket.getEvent().getName(), String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(ticket -> ticket.getEvent().getHall().getHallNumber())
                        .thenComparing(ticket -> ticket.getSeat().getRowNumber())
                        .thenComparing(ticket -> ticket.getSeat().getSeatNumber())
        );

        printer.printTickets(tickets);
    }
}