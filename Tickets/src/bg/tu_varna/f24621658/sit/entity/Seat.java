package bg.tu_varna.f24621658.sit.entity;

public class Seat {
    private int seatNumber;

    public Seat(int seatNumber) {
        if(seatNumber < 1) {
            throw new IllegalArgumentException("Невалиден номер на място!");
        }
        this.seatNumber = seatNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public String toString() {
        return "Seat: "+ seatNumber + " ";
    }


}
