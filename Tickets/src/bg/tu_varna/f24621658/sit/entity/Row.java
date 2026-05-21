package bg.tu_varna.f24621658.sit.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Модел на ред в зала, съдържащ конкретните места.
 * Пази номера на реда и списък с всички места в него.
 */
public class Row {
    private final int rowNumber;
    private final List<Seat> seats;

    /**
     * Създава нов ред с подаден номер и брой места.
     * При невалиден номер на ред или невалиден брой места се хвърля изключение.
     *
     * @param rowNumber номерът на реда
     * @param seatsCount броят места в реда
     */
    public Row(int rowNumber, int seatsCount) {
        if (rowNumber < 1) {
            throw new IllegalArgumentException("Невалиден номер на ред!");
        }
        if (seatsCount < 1) {
            throw new IllegalArgumentException("Невалиден брой места!");
        }
        this.rowNumber = rowNumber;
        this.seats = new ArrayList<>();
        createRow(seatsCount);
    }

    /**
     * Създава всички места за реда.
     * Местата се номерират последователно от 1 до подадения брой.
     *
     * @param seatsCount броят места, които трябва да бъдат създадени
     */
    private void createRow(int seatsCount) {
        for (int i = 0; i < seatsCount; i++) {
            seats.add(new Seat(rowNumber, i + 1));
        }
    }

    /**
     * Връща номера на реда.
     *
     * @return номерът на реда
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * Връща всички места в реда.
     *
     * @return списък с местата в реда
     */
    public List<Seat> getSeats() {
        return seats;
    }

    /**
     * Връща конкретно място от реда по неговия номер.
     * При невалиден номер на място се хвърля изключение.
     *
     * @param seatNumber номерът на мястото
     * @return намереното място
     */
    public Seat getSeat(int seatNumber) {
        if (seatNumber < 1 || seatNumber > seats.size()) {
            throw new IllegalArgumentException("Няма такова място в ред " + rowNumber + ": " + seatNumber);
        }
        return seats.get(seatNumber - 1);
    }

    /**
     * Връща текстово представяне на всички места в реда.
     *
     * @return текстово представяне на списъка с места
     */
    @Override
    public String toString() {
        return seats.toString();
    }
}