package bg.tu_varna.f24621658.sit.entity;

import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;
/**
 * Модел на билет за конкретно място.
 */
public class Ticket {
    private int row;
    private int seat;
    private TicketStatus status;
    private String note;
    private String code;

    /**
     * Създава нов обект от тип Ticket.
     * @param row номерът на реда в залата.
     * @param seat номерът на мястото в реда.
     * @param status статусът, който трябва да се зададе или възстанови за билета.
     * @param note бележката, която се записва към резервацията.
     * @param code уникалният код на закупен билет.
     */
    public Ticket(int row, int seat, TicketStatus status, String note, String code) {
        this.row = row;
        this.seat = seat;
        this.status = status;
        this.note = note;
        this.code = code;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public int getRow() {
        return row;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public int getSeat() {
        return seat;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public TicketStatus getStatus() {
        return status;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public String getNote() {
        return note;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public String getCode() {
        return code;
    }
    /**
     * Задава нова стойност на съответното поле.
     * @param code уникалният код на закупен билет.
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * Задава нова стойност на съответното поле.
     * @param status статусът, който трябва да се зададе или възстанови за билета.
     */
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
    /**
     * Задава нова стойност на съответното поле.
     * @param note бележката, която се записва към резервацията.
     */
    public void setNote(String note) {
        this.note = note;
    }
    /**
     * Резервира билет за избраното място.
     * @param note бележката, която се записва към резервацията.
     */
    public void book(String note){
        if(status == TicketStatus.FREE){
            this.status = TicketStatus.BOOKED;
            this.note = note;
        }else{
            throw new RuntimeException("Билетът вече е резервиран");
        }
    }
    /**
     * Купува билет за избраното място и връща генерирания код.
     * @param code уникалният код на закупен билет.
     */
    public void buy(String code){
        if(status == TicketStatus.BOOKED || status == TicketStatus.FREE){
            this.status = TicketStatus.SOLD;
            this.code = code;
        }else{
            throw new RuntimeException("Билета е вече купен");
        }
    }
    /**
     * Отменя направена резервация за избраното място.
     */
    public void unbook(){
        if(status == TicketStatus.BOOKED){
            this.status = TicketStatus.FREE;
            this.note = "";
            this.code = "";
        }else{
            throw new RuntimeException("Билета е не е запазен");
        }
    }
}
