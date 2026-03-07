package bg.tu_varna.f24621658.sit;

public class Hall {
    private int hallNumber;
    private String currentPlay;
    private Seat[][] seats;

    public Hall(int hallNumber, int rows, int seatsPerRow) {
        this.hallNumber = hallNumber;
        this.currentPlay = "";

        this.seats = new SeatsGenerator(rows, seatsPerRow).getSeats();
    }

    public int getSeatsPerRow() {
        return seats[0].length;
    }

    public int getRows() {
        return seats.length;
    }

    public void displayHall() {
        System.out.println("\n=== ЗАЛА " + hallNumber + " ===");
        if (!currentPlay.isEmpty()) {
            System.out.println("Представление: " + currentPlay);
        }

        // Отпечатване на номерата на местата
        System.out.print("    ");
        for (int j = 1; j <= getSeatsPerRow(); j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();

        // Отпечатване на редовете и местата
        for (int i = 0; i < getRows(); i++) {
            System.out.printf("Ред%2d: ", i + 1);
            for (int j = 0; j < getSeatsPerRow(); j++) {
                System.out.print(seats[i][j].isBooked() ? "[X] " : "[ ] ");
            }
            System.out.println();
        }
    }
}
