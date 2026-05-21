package bg.tu_varna.f24621658.sit.entity;

import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;

/**
 * Модел на билет за конкретно място.
 * Пази информация за представление, място, статус,
 * бележка към резервацията и код при закупуване.
 */
public class Ticket {
    private final Event event;
    private final Seat seat;
    private TicketStatus status;
    private String note;
    private String code;

    /**
     * Създава нов билет за конкретно представление и място.
     * При липсващи задължителни данни се хвърля изключение.
     * Ако бележката или кодът са null, се заменят с празен текст.
     *
     * @param event представлението, за което е билетът
     * @param seat мястото, за което е билетът
     * @param status текущият статус на билета
     * @param note бележка към билета или резервацията
     * @param code кодът на билета
     */
    public Ticket(Event event, Seat seat, TicketStatus status, String note, String code) {
        if (event == null) {
            throw new IllegalArgumentException("event is null");
        }
        if (seat == null) {
            throw new IllegalArgumentException("seat is null");
        }
        if (status == null) {
            throw new IllegalArgumentException("status is null");
        }
        this.event = event;
        this.seat = seat;
        this.status = status;
        this.note = note == null ? "" : note;
        this.code = code == null ? "" : code;
    }

    /**
     * Връща представлението, към което принадлежи билетът.
     *
     * @return представлението на билета
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Връща мястото, за което е създаден билетът.
     *
     * @return мястото на билета
     */
    public Seat getSeat() {
        return seat;
    }

    /**
     * Връща текущия статус на билета.
     *
     * @return статусът на билета
     */
    public TicketStatus getStatus() {
        return status;
    }

    /**
     * Връща бележката към билета.
     *
     * @return бележката към билета
     */
    public String getNote() {
        return note;
    }

    /**
     * Връща кода на билета.
     *
     * @return кодът на билета
     */
    public String getCode() {
        return code;
    }

    /**
     * Задава код на билета.
     * Ако подаденият код е null, се записва празен текст.
     *
     * @param code новият код на билета
     */
    public void setCode(String code) {
        this.code = code == null ? "" : code;
    }

    /**
     * Променя статуса на билета.
     * При подаден null статус се хвърля изключение.
     *
     * @param status новият статус на билета
     */
    public void setStatus(TicketStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("status is null");
        }
        this.status = status;
    }

    /**
     * Задава бележка към билета.
     * Ако подадената бележка е null, се записва празен текст.
     *
     * @param note новата бележка към билета
     */
    public void setNote(String note) {
        this.note = note == null ? "" : note;
    }

    /**
     * Закупува билета и записва неговия код.
     * При успешно закупуване статусът се променя на SOLD.
     *
     * @param code кодът на закупения билет
     */
    public void buy(String code) {
        if (status == TicketStatus.SOLD) {
            throw new RuntimeException("Билетът за това място е вече купен");
        }
        this.status = TicketStatus.SOLD;
        this.code = code == null ? "" : code;
    }

    /**
     * Отменя резервацията на билета.
     * Билетът отново става свободен, а бележката и кодът се изчистват.
     */
    public void unbook() {
        if (status != TicketStatus.BOOKED) {
            throw new RuntimeException("Билетът не е запазен");
        }
        this.status = TicketStatus.FREE;
        this.note = "";
        this.code = "";
    }
}