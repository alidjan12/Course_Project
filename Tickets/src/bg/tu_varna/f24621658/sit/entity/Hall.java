package bg.tu_varna.f24621658.sit.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * Модел на зала с номер и редове от места.
 */
public class Hall {
    private int hallNumber;
    private List<Row> rows;

    /**
     * Създава нов обект от тип Hall.
     * @param hallNumber номерът на залата.
     * @param rowsCount броят редове, които трябва да бъдат създадени.
     * @param seatsPerRow масив с броя седалки за всеки ред.
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

        createRows(rowsCount,seatsPerRow);
    }
    /**
     * Създава нов обект според подадените данни.
     * @param rowsCount броят редове, които трябва да бъдат създадени.
     * @param seatsPerRow масив с броя седалки за всеки ред.
     */
    private void createRows(int rowsCount, int[] seatsPerRow) {
        for (int i = 0; i < rowsCount; i++) {
            rows.add(new Row(i + 1, seatsPerRow[i]));
        }
    }
    /**
     * Връща залата, капацитета и всички места на съответния модел.
     * @return текущата съхранена стойност
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
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public int getHallNumber() {
        return hallNumber;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public List<Row> getRows() {
        return rows;
    }
    /**
     * Връща капацитета на съответния модел, като бреброява всички места.
     * @return текущата съхранена стойност
     */
    public int getCapacity(){
        int capacity = 0;
        for(Row row: rows){
            capacity += row.getSeats().size();
        }

        return capacity;
    }
    /**
     * Проверява дали съществува подадената комбинация от ред и място в залата.
     * @param rowNumber номерът на реда, който се проверява или съхранява.
     * @param seatNumber номерът на седалката, която се проверява или съхранява.
     * @return true, ако мястото съществува в залата; false, ако редът или мястото са извън обхвата
     */
    public boolean hasSeat(int rowNumber, int seatNumber) {
        if (rowNumber < 1 || rowNumber > rows.size()) {
            return false;
        }

        Row row = rows.get(rowNumber - 1);

        return seatNumber >= 1 && seatNumber <= row.getSeats().size();
    }

    /**
     * Валидира ред и място и хвърля грешка, ако мястото не съществува.
     * @param rowNumber номерът на реда, който се проверява или съхранява.
     * @param seatNumber номерът на седалката, която се проверява или съхранява.
     */
    public void validateSeat(int rowNumber, int seatNumber) {
        if (!hasSeat(rowNumber, seatNumber)) {
            throw new IllegalArgumentException(
                    "Няма такова място в залата: ред " + rowNumber + ", място " + seatNumber);
        }
    }
}
