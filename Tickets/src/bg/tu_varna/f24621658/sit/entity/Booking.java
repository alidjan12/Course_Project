package bg.tu_varna.f24621658.sit.entity;

import java.time.LocalDateTime;

public class Booking {
    private int row;
    private int seat;
    private String note;
    private LocalDateTime date;

    public Booking(int row, int seat, String note, LocalDateTime date) {
        this.row = row;
        this.seat = seat;
        this.note = note;
        this.date = LocalDateTime.now();
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getSeatInfo(){
        return "Row: "+this.row+" Seat: "+this.seat;
    }

    @Override
    public String toString() {
        return getSeatInfo()+ " Date: "+getDate()+" note: "+this.note;
    }
}
