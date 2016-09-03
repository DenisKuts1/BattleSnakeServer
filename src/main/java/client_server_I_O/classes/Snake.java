package client_server_I_O.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Анатолий on 25.07.2016.
 */
public class Snake implements Serializable {
    static final long serialVersionUID = 9213284845659098448L;
    private ArrayList<Block> body;
    private Card cards[];

    public ArrayList<Block> getBody() {
        return body;
    }

    public void setBody(ArrayList<Block> body) {
        this.body = body;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private Avatar avatar;
    private String name;
    private int rating;
    private String color;
    private String about;
    private Direction direction;

    public Snake() {

    }

    public void startPosition() {
        body.clear();
        switch (direction) {
            case TOP: {
                body.add(new Block(12, 4));
                body.add(new Block(12, 3));
                body.add(new Block(12, 2));
                body.add(new Block(12, 1));
                body.add(new Block(12, 0));
                break;
            }
            case BOTTOM: {
                body.add(new Block(13, 20));
                body.add(new Block(13, 21));
                body.add(new Block(13, 22));
                body.add(new Block(13, 23));
                body.add(new Block(13, 24));
                break;
            }
            case LEFT: {
                body.add(new Block(4, 12));
                body.add(new Block(3, 12));
                body.add(new Block(2, 12));
                body.add(new Block(1, 12));
                body.add(new Block(0, 12));
                break;
            }
            case RIGHT: {
                body.add(new Block(20, 13));
                body.add(new Block(21, 13));
                body.add(new Block(22, 13));
                body.add(new Block(23, 13));
                body.add(new Block(24, 13));
            }
        }
    }

    public void move(int vectorX, int vectorY, boolean hasEaten) {
        int x = body.get(0).getX() + vectorX;
        int y = body.get(0).getY() + vectorY;
        if (hasEaten) {
            body.add(0, new Block(x, y));
        } else {
            body.get(body.size() - 1).setX(x);
            body.get(body.size() - 1).setY(y);
            body.add(0, body.get(body.size() - 1));
            body.remove(body.size() - 1);
        }
    }

    public void gotEaten(){
        body.remove(body.size()-1);
    }

    public enum Direction implements Serializable {
        TOP, BOTTOM, LEFT, RIGHT
    }
}
