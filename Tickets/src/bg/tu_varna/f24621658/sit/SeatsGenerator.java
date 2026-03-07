package bg.tu_varna.f24621658.sit;

public class SeatsGenerator {
    private int rows;
    private int seatsPerRow;
    private final Seat[][] seats;

    public SeatsGenerator(int rows, int seatsPerRow) {
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.seats = new Seat[rows][seatsPerRow];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                seats[i][j] = new Seat(i + 1, j + 1);
            }
        }
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public int getRows() {
        return rows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }
}