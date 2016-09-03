package client_server_I_O;

import client_server_I_O.classes.Card;
import client_server_I_O.classes.Snake;
import client_server_I_O.classes.User;
import server.Message;
import server.Session;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Анатолий on 25.07.2016.
 */
public class Game {
    ArrayList<User> users;
    ArrayList<Socket> sockets;
    boolean stop = false;
    int port;

    public int getPort() {
        return port;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public Game(int port, User... users) {
        this.port = port;
        this.users = new ArrayList<>();
        sockets = new ArrayList<>();
        for (User user : users) {
            this.users.add(user);
        }
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            new Thread(() -> {
                while (!stop) {
                    try {
                        sockets.add(serverSocket.accept());
                    } catch (IOException e) {

                    }
                }

            });
        } catch (IOException e) {

        }


        new Thread(this::loop).start();


    }

    private void sendProgress(Desk desk, boolean isLast) {
        Message message;
        ArrayList<Snake> snakes = new ArrayList<>();

        if (!isLast) {
            message = new Message("snakes");
        } else {
            message = new Message("gameOver");
        }

        for (User user : users) {
            snakes.add(user.getSnake());
        }

        message.addUnit(snakes);

        for (Socket socket : sockets) {
            send(socket, message);
        }
    }

    private void send(Socket socket, Message message) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {

        }
    }

    private void loop() {
        Desk desk = new Desk();
        users.get(0).getSnake().setDirection(Snake.Direction.TOP);
        users.get(1).getSnake().setDirection(Snake.Direction.RIGHT);
        users.get(2).getSnake().setDirection(Snake.Direction.BOTTOM);
        users.get(3).getSnake().setDirection(Snake.Direction.LEFT);

        for (User user : users) {
            user.getSnake().startPosition();
        }
        while (!stop) {
            for (User user : users) {
                desk.clear();
                for (User enemy : users) {
                    if (!enemy.equals(user)) {
                        desk.drawEnemy(enemy);
                    } else {
                        desk.drawUser(user);
                    }
                }
                Card card = null;
                Label:
                for (Card c : user.getSnake().getCards()) {
                    for (int i = 0; i < 3; i++) {
                        if (desk.checkCard(c)) {
                            card = c;
                            break Label;
                        }
                        c = c.rotate();
                    }
                }
                if (card != null) {
                    moveSnake(desk, user, card.getDirection());
                } else {
                    Card.Direction direction = desk.getRandomMove();
                    if (direction != null) {
                        moveSnake(desk, user, direction);
                    }
                }
                sendProgress(desk, false);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }

            users.stream().filter(user -> user.getSnake().getBody().size() < 3).forEach(user -> users.remove(user));
            if (users.size() == 1) {
                stop = true;
            }
        }
        sendProgress(desk, true);
        for (Socket socket : sockets) {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
        Session.stopGame(this, port);

    }

    private void moveSnake(Desk desk, User user, Card.Direction direction) {
        int vectorX = 0;
        int vectorY = 0;
        switch (direction) {
            case LEFT: {
                vectorX = -1;
                break;
            }
            case FORWARD: {
                vectorY = -1;
                break;
            }
            case RIGHT: {
                vectorX = 1;
                break;
            }
            case BACKWARD: {
                vectorY = 1;
            }
        }
        boolean canEat = desk.canEat(vectorX, vectorY);
        user.getSnake().move(vectorX, vectorY, canEat);
        if (canEat) {
            for (User enemy : users) {
                if (!enemy.equals(user)) {
                    int x = enemy.getSnake().getBody().get(enemy.getSnake().getBody().size()).getX();
                    int y = enemy.getSnake().getBody().get(enemy.getSnake().getBody().size()).getY();
                    int userX = user.getSnake().getBody().get(0).getX();
                    int userY = user.getSnake().getBody().get(0).getY();
                    if (userX + vectorX == x && userY + vectorY == y) {
                        enemy.getSnake().gotEaten();
                    }
                }
            }
        }
    }
}

