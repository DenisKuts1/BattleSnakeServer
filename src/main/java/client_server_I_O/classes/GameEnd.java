package client_server_I_O.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by anato on 11.09.2016.
 */
public class GameEnd implements Serializable {

    private int snakeWinner;
    private ArrayList<Integer> newRatings;

    public int getSnakeWinner() {
        return snakeWinner;
    }
    public void setSnakeWinner(int snakeWinner) {
        this.snakeWinner = snakeWinner;
    }

    public ArrayList<Integer> getNewRatings() {
        return newRatings;
    }
    public void setNewRatings(ArrayList<Integer> newRatings) {
        this.newRatings = newRatings;
    }
}
