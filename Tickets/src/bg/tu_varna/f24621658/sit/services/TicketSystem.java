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

/**
 * Основен сервиз за управление на билетната система.
 * Обединява работата с представления, зали, резервации,
 * продажби, справки, импорт и експорт на данни.
 */
public class TicketSystem implements InformationSystemCommands {
    private EventRepository eventRepo;
    private HallRepository hallRepo;
    private InformationPrinter printer;

    /**
     * Създава билетна система с празни хранилища за представления и зали.
     *
     * @param printer обектът, чрез който системата извежда съобщения към потребителя.
     */
    public TicketSystem(InformationPrinter printer) {
        this.eventRepo = new EventRepository();
        this.hallRepo = new HallRepository();
        this.printer = printer;
    }

    /**
     * Добавя ново представление в системата.
     * Проверява дали залата съществува и след това записва представлението.
     *
     * @param date датата на представлението.
     * @param hall номерът на залата.
     * @param name името на представлението.
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
     * Премахва представление по дата и име.
     * След успешно премахване извежда потвърждение към потребителя.
     *
     * @param date датата на представлението.
     * @param name името на представлението.
     */
    @Override
    public void removeEvent(LocalDate date, String name) {
        eventRepo.remove(date, name);
        printer.printMessage("Представлението е свалено успешно.");
    }

    /**
     * Извежда свободните места за конкретно представление.
     * Показва само редовете, в които има поне едно свободно място.
     *
     * @param date датата на представлението.
     * @param name името на представлението.
     */
    @Override
    public void freeSeats(LocalDate date, String name) {
        Event event = eventRepo.findByNameAndDate(name, date);

        if (event == null) {
            throw new RuntimeException("Няма такова представление!");
        }

        Hall hall = event.getHall();
        Map<SeatKey, Ticket> tickets = event.getTickets();

        boolean hasFreeSeats = false;

        printer.printMessage("Представление: " + event.getName());
        printer.printMessage("Дата: " + event.getDate());
        printer.printMessage("Зала " + hall.getHallNumber());
        printer.printMessage("Свободни места: " + event.getFreeSeatsCount());

        // Обхождат се всички редове, за да се намерят свободните места по редове.
        for (Row row : hall.getRows()) {
            StringBuilder line = new StringBuilder();

            line.append("Ред ").append(row.getRowNumber()).append(": ");

            boolean hasFreeSeatsOnRow = false;

            // Обхождат се местата в текущия ред.
            for (Seat seat : row.getSeats()) {
                SeatKey key = new SeatKey(row.getRowNumber(), seat.getSeatNumber());
                Ticket ticket = tickets.get(key);

                // Мястото е свободно, ако няма билет за него или билетът е със статус FREE.
                if (ticket == null || ticket.getStatus() == TicketStatus.FREE) {
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
     * Резервира билет за избрано място в конкретно представление.
     *
     * @param row номерът на реда.
     * @param seat номерът на мястото.
     * @param date датата на представлението.
     * @param name името на представлението.
     * @param note бележка към резервацията.
     */
    @Override
    public void book(int row, int seat, LocalDate date, String name, String note) {
        Event  event = eventRepo.findByNameAndDate(name,date);
        if (event == null) {
            throw new RuntimeException("Няма такова представление");
        }
        event.bookTicket(row,seat,note);
    }

    /**
     * Отменя резервация за избрано място.
     *
     * @param row номерът на реда.
     * @param seat номерът на мястото.
     * @param date датата на представлението.
     * @param name името на представлението.
     */
    @Override
    public void unbook(int row, int seat, LocalDate date, String name) {
        Event event = eventRepo.findByNameAndDate(name,date);
        if (event == null) {
            throw new RuntimeException("Няма такова представление");
        }
        event.unBookTicket(row,seat);
    }

    /**
     * Купува билет за избрано място и връща неговия уникален код.
     *
     * @param row номерът на реда.
     * @param seat номерът на мястото.
     * @param date датата на представлението.
     * @param name името на представлението.
     * @return генерираният код на закупения билет.
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
     * Проверява валидността на билет по неговия код.
     * При валиден код извежда представлението, датата, залата, реда и мястото.
     *
     * @param code кодът на закупения билет.
     */
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

    /**
     * Показва резервациите за точно определено представление.
     *
     * @param date датата на представлението.
     * @param name името на представлението.
     */
    @Override
    public void bookings(LocalDate date, String name) {printBookings(eventRepo.getBookedTickets(name, date));}

    /**
     * Показва резервациите за всички представления с дадено име.
     *
     * @param name името на представлението.
     */
    @Override
    public void bookings(String name) {
        printBookings(eventRepo.getBookedTickets(name));
    }
    /**
     * Показва всички резервации за конкретна дата.
     *
     * @param date датата, по която се филтрират резервациите.
     */
    @Override
    public void bookings(LocalDate date) {
        printBookings(eventRepo.getBookedTickets(date));
    }
    /**
     * Показва всички направени резервации в системата.
     */
    @Override
    public void bookings() { printBookings(eventRepo.getBookedTickets());}

    /**
     * Извежда справка за продадените билети в период и конкретна зала.
     *
     * @param from началната дата на периода, включително.
     * @param to крайната дата на периода, включително.
     * @param hallNumber номерът на залата.
     */
    @Override
    public void report(LocalDate from, LocalDate to, int hallNumber) {
        Map<EventKey, Event> events = eventRepo.getEvents();
        boolean found = false;

        for (Event event : events.values()) {
            boolean isInPeriod = !event.getDate().isBefore(from) && !event.getDate().isAfter(to);
            boolean isInHall = event.getHall().getHallNumber() == hallNumber;

            if (isInPeriod && isInHall) {
                printer.printReport(event, event.getSoldTickets());
                found = true;
            }
        }
        // Ако няма представление, което отговаря на филтъра, се извежда съобщение.
        if (!found) {
            printer.printMessage("Няма продадени билети за този период и зала.");
        }
    }

    /**
     * Извежда справка за продадените билети в зададен период.
     *
     * @param from началната дата на периода, включително.
     * @param to крайната дата на периода, включително.
     */
    @Override
    public void report(LocalDate from, LocalDate to) {
        Map<EventKey, Event> events = eventRepo.getEvents();
        boolean found = false;

        for (Event event : events.values()) {
            boolean isInPeriod = !event.getDate().isBefore(from) && !event.getDate().isAfter(to);

            if (isInPeriod) {
                printer.printReport(event, event.getSoldTickets());
                found = true;
            }
        }
        // Ако няма представление, което отговаря на филтъра, се извежда съобщение.
        if (!found) {
            printer.printMessage("Няма продадени билети за този период.");
        }
    }

    /**
     * Показва представленията в период, подредени по брой продадени билети.
     *
     * @param from началната дата на периода.
     * @param to крайната дата на периода.
     */
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

    /**
     * Показва представленията след дадена дата, подредени по брой продадени билети.
     *
     * @param from началната дата, от която започва справката.
     */
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

    /**
     * Показва всички представления, подредени по брой продадени билети.
     */
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

    /**
     * Извежда представленията с посещаемост под 10% за зададен период.
     *
     * @param from началната дата на периода.
     * @param to крайната дата на периода.
     */
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
        // Ако няма представления с посещаемост под 10%, се извежда съобщение.
        if (!found) {
            printer.printMessage("Няма представления с посещаемост под 10%.");
        }
    }

    /**
     * Извежда всички представления, подредени по дата, име и зала.
     */
    @Override
    public void showEvents() {
        printer.printEvents(eventRepo.getSortedEvents());
    }

    /**
     * Извежда всички налични зали.
     */
    @Override
    public void showHalls() {
        printer.printHalls(hallRepo.getHalls());
    }

    /**
     * Преобразува текущите представления и несвободните билети в текстов формат.
     * Свободните билети не се записват, защото могат да се възстановят по залата.
     *
     * @return текстово съдържание във формат TICKETS_V1.
     */
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
                // Записват се само резервираните и закупените билети.
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

    /**
     * Зарежда представления и билети от текст във формат TICKETS_V1.
     * Преди зареждането изчиства текущите данни от системата.
     *
     * @param content съдържанието, прочетено от файла.
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
        // Всеки ред се разпознава като EVENT или TICKET и се обработва отделно.
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
     * Обработва EVENT ред от файла и създава представление.
     *
     * @param parts стойностите от реда, разделени със символ ';'.
     * @param lineNumber номерът на реда във файла.
     */
    private void importEventLine(String[] parts, int lineNumber) {
        try {
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
     * Обработва TICKET ред от файла и възстановява билет към представление.
     * Ако представлението липсва, редът се пропуска със съобщение.
     *
     * @param parts стойностите от реда, разделени със символ ';'.
     * @param lineNumber номерът на реда във файла.
     */
    private void importTicketLine(String[] parts, int lineNumber) {
        try {
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
            // Билет не може да се възстанови, ако представлението от файла липсва.
            if (event == null) {
                printer.printMessage(
                        "Грешка на ред " + lineNumber +
                                ": билет към несъществуващо представление '" + eventName +
                                "' на дата " + date + ". Редът е пропуснат."
                );
                return;
            }

            event.restoreTicket(row, seat, status, note, code);
        } catch (RuntimeException e) {
            printer.printMessage("Грешка на ред " + lineNumber + ": " + e.getMessage() + " Редът е пропуснат.");
        }
    }
    /**
     * Проверява дали съществува представление с дадени дата и име.
     *
     * @param date датата на представлението.
     * @param name името на представлението.
     * @return true, ако представлението съществува.
     */
    public boolean eventExists(LocalDate date, String name) {
        return eventRepo.eventExists(date, name);
    }

    /**
     * Намира най-близкото име на представление за дадена дата.
     * Използва се при грешно въведено име от потребителя.
     *
     * @param date датата, за която се търси представление.
     * @param input въведеният текст.
     * @return най-близкото име или null, ако няма представления за датата.
     */
    public String findClosestEventName(LocalDate date, String input) {
        return eventRepo.findClosestEventName(date, input);
    }
    /**
     * Изчиства всички представления от системата.
     */
    public void clear() {
        eventRepo.clear();
    }

    /**
     * Сортира и извежда списък с резервирани билети.
     * Ако списъкът е празен, извежда съобщение за липса на резервации.
     *
     * @param tickets списъкът с резервации за извеждане.
     */
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