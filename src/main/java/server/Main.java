package server;

import java.io.IOException;

/**
 * Created by Анатолий on 13.07.2016.
 */
public class Main {

    public static void main(String[] args) {
        try {
            Server server = new Server();
        } catch (IOException e) {
            System.out.println("fail");
        }

    }

}
