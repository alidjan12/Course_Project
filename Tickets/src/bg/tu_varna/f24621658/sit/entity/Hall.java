package bg.tu_varna.f24621658.sit.entity;

import java.util.ArrayList;
import java.util.List;

public class Hall {
    private int hallNumber;
    private List<Row> rows;

    public Hall(int hallNumber, int rowsCount, int[] seatsPerRow) {
        if (hallNumber < 1) {
            throw new IllegalArgumentException("Невалиден номер за зала!");
        }
        if (seatsPerRow == null || seatsPerRow.length == 0) {
            throw new IllegalArgumentException("Броят на редовете не може да е 0!");
        }
        if (seatsPerRow.length != rowsCount) {
            throw new IllegalArgumentException("Броят редове трябва да съвпада с броя на подадените места за редовете!");
        }

        this.hallNumber = hallNumber;
        this.rows = new ArrayList<>();

        createRows(rowsCount,seatsPerRow);
    }

    //ред = 3
    //места = 3,4,5
    //ред(1):1,2,3
    //ред(2):1,2,3,4
    //ред(3):1,2,3,4,5
    private void createRows(int rowsCount, int[] seatsPerRow) {
        for (int i = 0; i < rowsCount; i++) {
            rows.add(new Row(i + 1, seatsPerRow[i]));
        }
    }

    public void printHall(){
        System.out.println("Hall number: "+hallNumber);
        for (Row row : rows) {
            System.out.println("row: "+row.getRowNumber()+row.toString());
        }
    }

    public int getHallNumber() {
        return hallNumber;
    }

    public List<Row> getRows() {
        return rows;
    }

    public boolean hasSeat(int rowNumber, int seatNumber) {
        if (rowNumber < 1 || rowNumber > rows.size()) {
            return false;
        }

        Row row = rows.get(rowNumber - 1);

        return seatNumber >= 1 && seatNumber <= row.getSeats().size();
    }

    public void validateSeat(int rowNumber, int seatNumber) {
        if (!hasSeat(rowNumber, seatNumber)) {
            throw new IllegalArgumentException(
                    "Няма такова място в залата: ред " + rowNumber + ", място " + seatNumber);
        }
    }
}
