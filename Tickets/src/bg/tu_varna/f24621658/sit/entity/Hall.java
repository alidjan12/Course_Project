package bg.tu_varna.f24621658.sit.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hall {
    private final int number;
    private final List<Row> rows;

    public Hall(int number) {
        if(number <=0) {
            throw new IllegalArgumentException("Invalid hall number");
        }
        this.number = number;
        this.rows = new ArrayList<Row>();
    }

    public int getNumber() {
        return number;
    }

    public void addRow(int seatsCount) {
        int nextRowNumber = rows.size() + 1;
        rows.add(new Row(nextRowNumber, seatsCount));
    }

    public int getRowsCount() {
        return rows.size();
    }

    public Row getRow(int rowNumber) {
        validateRow(rowNumber);
        return rows.get(rowNumber - 1);
    }

    public boolean isSeatFree(int rowNumber, int seatNumber) {
        return getRow(rowNumber).isSeatFree(seatNumber);
    }

    public void occupySeat(int rowNumber, int seatNumber) {
        getRow(rowNumber).occupySeat(seatNumber);
    }

    public void freeSeat(int rowNumber, int seatNumber) {
        getRow(rowNumber).freeSeat(seatNumber);
    }

    public int freeSeatsCount() {
        int count = 0;
        for (Row row : rows) {
            count += row.freeSeatsCount();
        }
        return count;
    }

    private void validateRow(int rowNumber) {
        if (rowNumber < 1 || rowNumber > rows.size()) {
            throw new IllegalArgumentException("Invalid row number");
        }
    }

    @Override
    public String toString() {
        return "Hall " + number + " with " + rows.size() + " rows";
    }
}
