package client_server_I_O.classes;

import java.io.Serializable;

/**
 * Created by Анатолий on 25.07.2016.
 */
public class User implements Serializable {
    static final long serialVersionUID = 9213284845659098448L;
    private String login;
    private String password;
    private Snake snake;


    public User() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
