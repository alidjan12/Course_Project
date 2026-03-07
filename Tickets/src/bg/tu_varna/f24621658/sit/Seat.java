package bg.tu_varna.f24621658.sit;

public class Seat {
    private int rowNumber;
    private int seatNumber;
    private boolean isBooked;
    private Booker booker;

    public Seat(int rowNumber, int seatNumber) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.isBooked = false;
        this.booker = null;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public Booker getBooker() {
        return booker;
    }
}
