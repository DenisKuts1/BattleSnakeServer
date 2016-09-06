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
    private static ArrayList<Game> games = new ArrayList<>();
    private static ArrayList<Integer> freePorts = new ArrayList<>();

    static {
        for (int i = 12346; i < 13346; i++) {
            freePorts.add(i);
        }
    }

    public static void stopGame(Game game, int port) {
        synchronized (freePorts) {
            freePorts.add(port);
        }
        synchronized (games) {
            games.remove(game);
        }

    }

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
                            getUsers();
                            break;
                        }
                        case "games": {
                            getGames();
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
        int port;
        synchronized (freePorts) {
            port = freePorts.get(0);
            freePorts.remove(0);
        }
        Game game = new Game(port,
                connector.getUser(user1),
                connector.getUser(user2),
                connector.getUser(user3),
                connector.getUser(user4));
        Message message = new Message(port);
        synchronized (games) {
            games.add(game);
        }
        sendMessage(message);
    }


    private void updateUser(User user) {
        connector.updateUser(user);
    }

    private void getUsers() {
        Message message = new Message(connector.getUsers());
        sendMessage(message);
    }

    private void getGames() {
        HashMap<Integer, String> names = new HashMap<>();
        synchronized (games) {
            for (Game game : games) {
                String name = "";
                for (User user : game.getUsers()) {
                    name += user.getLogin() + " ";
                }
                names.put(game.getPort(), name);
            }
        }
        Message message = new Message(names);
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
