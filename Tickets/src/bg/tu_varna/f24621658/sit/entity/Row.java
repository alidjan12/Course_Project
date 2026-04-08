package bg.tu_varna.f24621658.sit.entity;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private int rowNumber;
    private List<Seat> seats;

    public Row(int rowNumber,int seatsCount) {
        if(rowNumber<1){
            throw new IllegalArgumentException("Невалиден номер на ред!");
        }
        this.rowNumber = rowNumber;
        seats = new ArrayList<Seat>();

        createRow(seatsCount);
    }

    //създавам редица от места като [0] номерата започват от 1
    private void createRow(int seatsCount) {
        for (int i = 0; i < seatsCount; i++) {
            seats.add(new Seat(i + 1));
        }
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return seats.toString();
    }
}
