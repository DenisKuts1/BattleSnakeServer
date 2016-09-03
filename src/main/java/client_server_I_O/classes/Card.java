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


    private Role[][] matrix;
    private Direction direction;

    private Card(Direction direction) {
        matrix = new Role[7][7];
        this.direction = direction;
    }

    public Role getCell(int i, int j) {
        return matrix[i][j];
    }

    public void setCell(Role role, int i, int j) {
        matrix[i][j] = role;
    }

    public Card rotate() {
        Direction d;
        switch (direction) {
            case LEFT: {
                d = Direction.FORWARD;
                break;
            }
            case FORWARD: {
                d = Direction.RIGHT;
                break;
            }
            case RIGHT: {
                d = Direction.BACKWARD;
                break;
            }
            default: {
                d = Direction.LEFT;
            }

        }
        Card card = new Card(d);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                card.matrix[i][j] = this.matrix[j][7 - i - 1];
            }
        }
        return card;
    }

    public enum Direction implements Serializable {
        LEFT, RIGHT, FORWARD, BACKWARD
    }

    public Direction getDirection() {
        return direction;
    }

}
