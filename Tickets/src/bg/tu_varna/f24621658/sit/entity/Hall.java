package bg.tu_varna.f24621658.sit.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Модел на зала с номер и редове от места.
 * Пази номера на залата и списък с редовете,
 * които съдържат местата в залата.
 */
public class Hall {
    private final int hallNumber;
    private final List<Row> rows;

    /**
     * Създава нова зала с подаден номер, брой редове и брой места за всеки ред.
     * При невалидни данни се хвърля изключение.
     *
     * @param hallNumber номерът на залата
     * @param rowsCount броят редове в залата
     * @param seatsPerRow масив с броя места за всеки ред
     */
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
        createRows(rowsCount, seatsPerRow);
    }

    /**
     * Създава копие на подадена зала.
     * Копира номера на залата и структурата на редовете и местата,
     * без да прехвърля билетите към местата.
     *
     * @param hall залата, която трябва да бъде копирана
     */
    public Hall(Hall hall) {
        if (hall == null) {
            throw new IllegalArgumentException("hall is null");
        }
        this.hallNumber = hall.getHallNumber();
        this.rows = new ArrayList<>();
        for (Row row : hall.getRows()) {
            this.rows.add(new Row(row.getRowNumber(), row.getSeats().size()));
        }
    }

    /**
     * Създава редовете в залата според подадения брой места за всеки ред.
     *
     * @param rowsCount броят редове в залата
     * @param seatsPerRow масив с броя места за всеки ред
     */
    private void createRows(int rowsCount, int[] seatsPerRow) {
        for (int i = 0; i < rowsCount; i++) {
            rows.add(new Row(i + 1, seatsPerRow[i]));
        }
    }

    /**
     * Връща текстово представяне на разположението на залата.
     * Включва номера на залата, капацитета и местата по редове.
     *
     * @return текстово представяне на залата
     */
    public String getLayout() {
        StringBuilder sb = new StringBuilder();
        sb.append("Зала ").append(hallNumber).append(System.lineSeparator());
        sb.append("Капацитет: ").append(getCapacity()).append(" места").append(System.lineSeparator());
        for (Row row : rows) {
            sb.append("Ред ").append(row.getRowNumber()).append(": ");
            for (Seat seat : row.getSeats()) {
                sb.append("[").append(seat.getSeatNumber()).append("]");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Връща номера на залата.
     *
     * @return номерът на залата
     */
    public int getHallNumber() {
        return hallNumber;
    }

    /**
     * Връща всички редове в залата.
     *
     * @return списък с редовете в залата
     */
    public List<Row> getRows() {
        return rows;
    }

    /**
     * Връща всички места в залата.
     * Обединява местата от всички редове в един списък.
     *
     * @return списък с всички места в залата
     */
    public List<Seat> getSeats() {
        List<Seat> result = new ArrayList<>();
        for (Row row : rows) {
            result.addAll(row.getSeats());
        }
        return result;
    }

    /**
     * Изчислява общия капацитет на залата.
     * Капацитетът представлява общия брой места във всички редове.
     *
     * @return броят на всички места в залата
     */
    public int getCapacity() {
        int capacity = 0;
        for (Row row : rows) {
            capacity += row.getSeats().size();
        }
        return capacity;
    }

    /**
     * Проверява дали в залата съществува място с подадените ред и номер.
     *
     * @param rowNumber номерът на реда
     * @param seatNumber номерът на мястото
     * @return true, ако мястото съществува; false в противен случай
     */
    public boolean hasSeat(int rowNumber, int seatNumber) {
        if (rowNumber < 1 || rowNumber > rows.size()) {
            return false;
        }
        Row row = rows.get(rowNumber - 1);
        return seatNumber >= 1 && seatNumber <= row.getSeats().size();
    }

    /**
     * Намира конкретно място в залата по номер на ред и номер на място.
     * Преди връщане проверява дали мястото съществува.
     *
     * @param rowNumber номерът на реда
     * @param seatNumber номерът на мястото
     * @return намереното място
     */
    public Seat findSeat(int rowNumber, int seatNumber) {
        validateSeat(rowNumber, seatNumber);
        return rows.get(rowNumber - 1).getSeat(seatNumber);
    }

    /**
     * Проверява дали подадените ред и място съществуват в залата.
     * При невалидни стойности се хвърля изключение.
     *
     * @param rowNumber номерът на реда
     * @param seatNumber номерът на мястото
     */
    public void validateSeat(int rowNumber, int seatNumber) {
        if (!hasSeat(rowNumber, seatNumber)) {
            throw new IllegalArgumentException("Няма такова място в залата: ред " + rowNumber + ", място " + seatNumber);
        }
    }
}