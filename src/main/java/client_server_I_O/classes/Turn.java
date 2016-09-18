package client_server_I_O.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Turn implements Serializable {

    static final long serialVersionUID = 4566843898265039363l;

    private GameEnd gameEnd;
    private Map<Integer, ArrayList<Block>> body;

    public GameEnd getGameEnd() {
        return gameEnd;
    }
    public void setGameEnd(GameEnd gameEnd) {
        this.gameEnd = gameEnd;
    }

    public Map<Integer, ArrayList<Block>> getBody() {
        return body;
    }
    public void setBody(Map<Integer, ArrayList<Block>> body) {
        this.body = body;
    }

}

