package bg.tu_varna.f24621658.sit.entity;

import bg.tu_varna.f24621658.sit.entity.enums.TicketStatus;

public class Ticket {
    private int row;
    private int seat;
    private TicketStatus status;
    private String note;
    private String code;

    public Ticket(int row, int seat, TicketStatus status, String note, String code) {
        this.row = row;
        this.seat = seat;
        this.status = status;
        this.note = note;
        this.code = code;
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void book(String note){
        if(status == TicketStatus.FREE){
            this.status = TicketStatus.BOOKED;
            this.note = note;
        }else{
            throw new RuntimeException("Билета е вече закупен");
        }
    }
    public void buy(String code){
        if(status == TicketStatus.BOOKED || status == TicketStatus.FREE){
            this.status = TicketStatus.SOLD;
            this.code = code;
        }else{
            throw new RuntimeException("Билета е вече купен");
        }
    }
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
