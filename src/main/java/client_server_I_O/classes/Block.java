package client_server_I_O.classes;

/**
 * Created by Анатолий on 25.07.2016.
 */
public class Block {
    static final long serialVersionUID = 9213284845659098448L;
    private int x,y;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {

        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
