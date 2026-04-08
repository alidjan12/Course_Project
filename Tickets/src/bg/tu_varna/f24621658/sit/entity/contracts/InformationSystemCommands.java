package bg.tu_varna.f24621658.sit.entity.contracts;

import bg.tu_varna.f24621658.sit.entity.Hall;

import java.time.LocalDate;

public interface InformationSystemCommands {
    void addEvent(LocalDate date, Hall hall, String name);
    void freeSeats(LocalDate date, String name);

    void book(int row,int seat,LocalDate date,String name,String note);
    void unbook(int row,int seat,LocalDate date,String name);
    void buy(int row,int seat,LocalDate date,String name);

    // void bookings
    // void check(String code);
    //report (<from> <to>,Hall hall);

}
