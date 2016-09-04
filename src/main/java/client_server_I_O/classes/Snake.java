package client_server_I_O.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Анатолий on 25.07.2016.
 */
public class Snake implements Serializable {
    static final long serialVersionUID = 9213284845659098448L;

    private ArrayList<Block> body;
    private Card[][] cards;

    private Avatar avatar;
    private String name;
    private int rating;
    private String color;
    private String about;



    public ArrayList<Block> getBody() {
        return body;
    }

    public void setBody(ArrayList<Block> body) {
        this.body = body;
    }

    public Card[][] getCards() {
        return cards;
    }

    public void setCards(Card[][] cards) {
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





    public Snake() {

    }

}
