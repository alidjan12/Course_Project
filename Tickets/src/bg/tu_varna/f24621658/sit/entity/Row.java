package bg.tu_varna.f24621658.sit.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * Модел на ред в зала, съдържащ конкретните места.
 */
public class Row {
    private int rowNumber;
    private List<Seat> seats;
    /**
     * Създава нов обект от тип Row.
     * @param rowNumber номерът на реда, който се проверява или съхранява.
     * @param seatsCount броят седалки, които трябва да се създадат в реда.
     */
    public Row(int rowNumber,int seatsCount) {
        if(rowNumber<1){
            throw new IllegalArgumentException("Невалиден номер на ред!");
        }
        this.rowNumber = rowNumber;
        seats = new ArrayList<Seat>();

        createRow(seatsCount);
    }

    /**
     * Създава нов обект според подадените данни, като номерацията започва от 1.
     * @param seatsCount броят седалки, които трябва да се създадат в реда.
     */
    private void createRow(int seatsCount) {
        for (int i = 0; i < seatsCount; i++) {
            seats.add(new Seat(i + 1));
        }
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public int getRowNumber() {
        return rowNumber;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public List<Seat> getSeats() {
        return seats;
    }
    /**
     * Стандартна реализация на метода toString.
     * @return текстово описание на реда и броя седалки
     */
    @Override
    public String toString() {
        return seats.toString();
    }
}
