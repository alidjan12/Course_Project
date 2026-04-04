package bg.tu_varna.f24621658.sit.entity;

public class Seat {
    private final int number;
    private boolean occupied;

    public Seat(int number){
        if(number <= 0){
            throw new IllegalArgumentException("Invalid seat number!");
        }

        this.number = number;
        this.occupied = false;
    }

    public int getNumber() {
        return number;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean isFree() {
        return !occupied;
    }

    public void occupy() {
        this.occupied = true;
    }

    public void free() {
        this.occupied = false;
    }
}
