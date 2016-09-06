package server;

import client_server_I_O.classes.User;
import javafx.application.Application;

import java.io.IOException;

/**
 * Created by Анатолий on 13.07.2016.
 */
public class Main {

    public static void main(String[] args) {
        /*try {
            Server server = new Server();
        } catch (IOException e) {
            System.out.println("fail");
        }*/

        User user = new User();
        user.setPassword("123");
        user.setLogin("123");
        new DBConnector().addUser(user);
    }

}
