package bg.tu_varna.f24621658.sit.entity.keys;

import java.util.Objects;
/**
 * Ключ за идентифициране на място по ред и номер.
 */
public class SeatKey {
    private int row;
    private int seat;

    /**
     * Създава нов обект от тип SeatKey.
     * @param row номерът на реда в залата.
     * @param seat номерът на мястото в реда.
     */
    public SeatKey(int row, int seat) {
        if(row <1){
            throw new IllegalArgumentException("Невалидна редица!");
        }
        if(seat<1){
            throw new IllegalArgumentException("Невалидно място");
        }
        this.row = row;
        this.seat = seat;
    }
    /**
     * Стандартна реализация на метода equals.
     * @param o обектът, с който се сравнява текущият ключ.
     * @return true, ако двата ключа имат еднакъв ред и място; за hashCode - hash стойност по тези полета
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SeatKey seatKey = (SeatKey) o;
        return row == seatKey.row && seat == seatKey.seat;
    }
    /**
     * Стандартна реализация на метода hashCode.
     * @return true, ако двата ключа имат еднакъв ред и място; за hashCode - hash стойност по тези полета
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, seat);
    }
}
