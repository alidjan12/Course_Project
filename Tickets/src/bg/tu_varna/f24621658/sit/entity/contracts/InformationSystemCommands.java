package bg.tu_varna.f24621658.sit.entity.contracts;

import bg.tu_varna.f24621658.sit.entity.Hall;

import java.time.LocalDate;
/**
 * Договор за операциите, които системата за билети трябва да поддържа.
 */
public interface InformationSystemCommands {
    void addEvent(LocalDate date, int hallNumber, String name);
    void freeSeats(LocalDate date, String name);

    void book(int row,int seat,LocalDate date,String name,String note);
    void unbook(int row,int seat,LocalDate date,String name);
    String buy(int row,int seat,LocalDate date,String name);

    void bookings(LocalDate date,String name);
    void bookings(String name);
    void bookings(LocalDate date);
    void bookings();

    void check(String code);

    void report (LocalDate from, LocalDate to);
    void report(LocalDate from, LocalDate to, int hallNumber);

    void mostWatched(LocalDate from, LocalDate to);
    void mostWatched(LocalDate from);
    void mostWatched();

    void lowAttendance(LocalDate from, LocalDate to);
    void removeEvent(LocalDate date, String name);

    void showEvents();
    void showHalls();

}
