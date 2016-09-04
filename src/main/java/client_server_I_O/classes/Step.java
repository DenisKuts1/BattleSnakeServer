package client_server_I_O.classes;

import java.util.ArrayList;
import java.util.Map;

public class Step {

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
