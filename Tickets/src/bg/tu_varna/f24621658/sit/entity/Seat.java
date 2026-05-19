package bg.tu_varna.f24621658.sit.entity;
/**
 * Модел на място в ред от зала.
 */
public class Seat {
    private int seatNumber;
    /**
     * Създава нов обект от тип Seat.
     * @param seatNumber номерът на седалката, която се проверява или съхранява.
     */
    public Seat(int seatNumber) {
        if(seatNumber < 1) {
            throw new IllegalArgumentException("Невалиден номер на място!");
        }
        this.seatNumber = seatNumber;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public int getSeatNumber() {
        return seatNumber;
    }
    /**
     * @return номера на седалката като текст
     */
    @Override
    public String toString() {
        return "Seat: "+ seatNumber + " ";
    }


}
