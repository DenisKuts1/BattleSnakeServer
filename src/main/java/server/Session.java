package server;

import client_server_I_O.Game;
import client_server_I_O.classes.Message;
import client_server_I_O.classes.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Анатолий on 13.07.2016.
 */
public class Session implements Runnable {

    private Socket socket;
    private DBConnector connector = new DBConnector();

    public Session(Socket socket) throws IOException {
        this.socket = socket;

    }

    private Message readMessage() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return (Message) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("can't read message");
            return null;
        }
    }


    public void run() {
        Message message;
        while ((message = readMessage()) != null) {
            String firstUnit = (String) message.getUnit();
            System.out.println(firstUnit);
            switch (firstUnit) {
                case "add": {
                    addUser((User) message.getUnit());
                    break;
                }
                case "get": {
                    switch ((String) message.getUnit()) {
                        case "user": {
                            getUser((String) message.getUnit(), (String) message.getUnit());
                            break;
                        }
                        case "users": {
                            getUsers((String) message.getUnit());
                            break;
                        }
                    }
                    break;
                }
                case "update": {
                    updateUser((User) message.getUnit());
                    break;
                }
                case "start": {
                    startGame((String) message.getUnit(), (String) message.getUnit(), (String) message.getUnit(), (String) message.getUnit());
                    break;
                }
            }
        }

    }

    private void startGame(String user1, String user2, String user3, String user4) {
        Game game;
        if(user3 == null){
            game = new Game(socket, connector.getUser(user1), connector.getUser(user2));

        } else if(user4 == null){
            game = new Game(socket, connector.getUser(user1), connector.getUser(user2), connector.getUser(user3));
        } else {
            game = new Game(socket, connector.getUser(user1), connector.getUser(user2), connector.getUser(user3), connector.getUser(user4));
        }


    }


    private void updateUser(User user) {
        Message message = new Message(connector.updateUser(user));
        sendMessage(message);
    }

    private void getUsers(String login) {
        Message message = new Message(connector.getUsers(login));
        sendMessage(message);
    }

    private void getUser(String login, String password) {
        System.out.println(login);
        System.out.println(password);
        User user = connector.getUser(login, password);
        Message message = new Message(user);
        sendMessage(message);
    }

    private void addUser(User user) {
        Message message = new Message(connector.addUser(user));
        sendMessage(message);


    }

    private void sendMessage(Message message) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {

        }
    }
}
