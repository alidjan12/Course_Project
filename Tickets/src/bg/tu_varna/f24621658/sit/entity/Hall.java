package bg.tu_varna.f24621658.sit.entity;

import java.util.Arrays;

public class Hall {
    private int number;
    private int rows;
    private int seatsPerRows;
    private boolean[][] seats;

    public Hall(int number, int rows, int seatsPerRows) {
        this.number = number;
        this.rows = rows;
        this.seatsPerRows = seatsPerRows;

        for(boolean[] row : seats){
            Arrays.fill(row, false);
        }
    }

    public int getNumber() {
        return number;
    }

    public int getRows() {
        return rows;
    }

    public int getSeatsPerRows() {
        return seatsPerRows;
    }



    public boolean isSeatFree(int row,int seat){
        validateSeat(row,seat);
        return !seats[row-1][seat-1];

    }

    public void occupySeat(int row,int seat){
        validateSeat(row,seat);
        seats[row-1][seat-1] = true;
    }

    public void freeSeat(int row,int seat){
        validateSeat(row,seat);
        seats[row-1][seat-1] = false;
    }

    private void validateSeat(int row,int seat){
        if(row<1 || row>rows){
            throw new IllegalArgumentException("Invalid row number");
        }else if(seat<1 || seat> seatsPerRows){
            throw new IllegalArgumentException("Invalid seat number");
        }

    }

    public int freeSeatsCount(){
        int count = 0;
        for(boolean[] row : seats){
            for(boolean seat : row){
                if(!seat){
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "Hall " + number + " (" + rows + " rows, " + seatsPerRows + " seats per row)";
    }
}
