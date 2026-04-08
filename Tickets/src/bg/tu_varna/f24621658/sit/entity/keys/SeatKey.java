package bg.tu_varna.f24621658.sit.entity.keys;

import java.util.Objects;

public class SeatKey {
    private int row;
    private int seat;

    public SeatKey(int row, int seat) {
        if(row <0){
            throw new IllegalArgumentException("Невалидна редица!");
        }
        if(seat<0){
            throw new IllegalArgumentException("Невалидно място");
        }
        this.row = row;
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SeatKey seatKey = (SeatKey) o;
        return row == seatKey.row && seat == seatKey.seat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, seat);
    }
}
