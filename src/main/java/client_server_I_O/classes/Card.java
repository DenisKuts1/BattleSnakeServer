package client_server_I_O.classes; /**
 * Created by Анатолий on 25.07.2016.
 */

import java.io.Serializable;

/**
 * This class represents card, which contains a matrix 7*7.
 * There are few kinds of cells:
 * 1. client_server_I_O.classes.User's snake's tale.
 * 2. client_server_I_O.classes.User's snake's body.
 * 3. client_server_I_O.classes.User's snake's head.
 * 4. Empty place.
 * 5. Enemy snake.
 * And card also has a direction of next move, such as 'left', 'right' and 'forward'
 */
public class Card implements Serializable {


    CardElement[][] elements = new CardElement[7][7];
    private Direction direction;

    public Card(Direction direction) {
        getDirection();
    }

    public CardElement[][] getElements() {
        return elements;
    }
    public void setElements(CardElement[][] elements) {
        this.elements = elements;
    }



    public enum Direction implements Serializable {
        LEFT, RIGHT, FORWARD, BACKWARD
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
