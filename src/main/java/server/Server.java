package server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Анатолий on 13.07.2016.
 */
public class Server {

    private ServerSocket serverSocket;
    private boolean isStopped = false;

    public Server() throws IOException {
        serverSocket = new ServerSocket(12345);
        while (!isStopped) {
            new Thread(new Session(serverSocket.accept())).start();
        }
    }
}
