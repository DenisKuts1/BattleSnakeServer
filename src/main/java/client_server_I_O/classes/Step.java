package client_server_I_O.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Step implements Serializable {

    private boolean isGameFinished;
    private Map<Integer, ArrayList<Block>> body;

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
    }

    public Map<Integer, ArrayList<Block>> getBody() {
        return body;
    }

    public void setBody(Map<Integer, ArrayList<Block>> body) {
        this.body = body;
    }
}
