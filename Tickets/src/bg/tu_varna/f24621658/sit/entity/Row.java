package bg.tu_varna.f24621658.sit.entity;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private final int number;
    private final List<Seat> seats;

    public Row(int number, int seatsCount) {
         if (number <= 0) {
            throw new IllegalArgumentException("Invalid row number");
        }
        if (seatsCount <= 0) {
            throw new IllegalArgumentException("Seats count must be positive");
        }

        this.number = number;
        this.seats = new ArrayList<>();

        for (int i = 1; i <= seatsCount; i++) {
            seats.add(new Seat(i));
        }
    }

    public int getNumber() {
        return number;
    }

    public int getSeatsCount() {
        return seats.size();
    }

    public Seat getSeat(int seatNumber) {
        validateSeat(seatNumber);
        return seats.get(seatNumber - 1);
    }

    public boolean isSeatFree(int seatNumber) {
        return getSeat(seatNumber).isFree();
    }

    public void occupySeat(int seatNumber) {
        getSeat(seatNumber).occupy();
    }

    public void freeSeat(int seatNumber) {
        getSeat(seatNumber).free();
    }

    public int freeSeatsCount() {
        int count = 0;
        for (Seat seat : seats) {
            if (seat.isFree()) {
                count++;
            }
        }
        return count;
    }

    private void validateSeat(int seatNumber) {
        if (seatNumber < 1 || seatNumber > seats.size()) {
            throw new IllegalArgumentException("Invalid seat number");
        }
    }
}
