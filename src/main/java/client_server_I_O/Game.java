package client_server_I_O;

import client_server_I_O.classes.*;
import server.Message;
import server.Session;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Анатолий on 25.07.2016.
 */
public class Game {
    ArrayList<User> users;
    ArrayList<User> allUsers;
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

    private void sendProgress(boolean isLast) {
        Message message;

        Map<Integer, ArrayList<Block>> body = new HashMap<>();
        for(int i = 0 ; i < 4; i++){
            body.put(i,allUsers.get(i).getSnake().getBody());
        }

        Step step = new Step();
        step.setBody(body);
        step.setGameFinished(isLast);
        message = new Message(step);

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
        allUsers = (ArrayList<User>) users.clone();
        startPosition(users.get(0).getSnake(), Direction.TOP);
        startPosition(users.get(1).getSnake(), Direction.RIGHT);
        startPosition(users.get(2).getSnake(), Direction.BOTTOM);
        startPosition(users.get(3).getSnake(), Direction.LEFT);

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
                for (Card []cc : user.getSnake().getCards()) {
                    for (Card c : cc) {
                        for (int i = 0; i < 3; i++) {
                            if (desk.checkCard(c)) {
                                card = c;
                                break Label;
                            }

                            c = rotate(c);
                        }
                    }
                }
                if (card != null) {
                    moveSnake(desk, user, Direction.TOP);
                } else {
                    Direction direction = desk.getRandomMove();
                    if (direction != null) {
                        moveSnake(desk, user, direction);
                    }
                }
                sendProgress(false);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }

            users.stream().filter(user -> user.getSnake().getBody().size() < 3).forEach(user -> users.remove(user));
            if (users.size() == 1) {
                stop = true;
                for(User user : allUsers){
                    if(user.equals(users.get(0))){
                        user.getSnake().setRating(user.getSnake().getRating() + 10);
                    } else {
                        user.getSnake().setRating(user.getSnake().getRating() - 5);
                    }
                }
            }
        }
        sendProgress(true);
        for (Socket socket : sockets) {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
        Session.stopGame(this, port);

    }

    private void moveSnake(Desk desk, User user, Direction direction) {
        int vectorX = 0;
        int vectorY = 0;
        switch (direction) {
            case LEFT: {
                vectorX = -1;
                break;
            }
            case TOP: {
                vectorY = -1;
                break;
            }
            case RIGHT: {
                vectorX = 1;
                break;
            }
            case BOTTOM: {
                vectorY = 1;
            }
        }
        boolean canEat = desk.canEat(vectorX, vectorY);
        move(user.getSnake(), vectorX, vectorY, canEat);
        if (canEat) {
            for (User enemy : users) {
                if (!enemy.equals(user)) {
                    int x = enemy.getSnake().getBody().get(enemy.getSnake().getBody().size()).getX();
                    int y = enemy.getSnake().getBody().get(enemy.getSnake().getBody().size()).getY();
                    int userX = user.getSnake().getBody().get(0).getX();
                    int userY = user.getSnake().getBody().get(0).getY();
                    if (userX + vectorX == x && userY + vectorY == y) {
                        gotEaten(enemy.getSnake());
                    }
                }
            }
        }
    }

    //private Direction direction;
    public void startPosition(Snake snake, Direction direction) {
        snake.getBody().clear();
        switch (direction) {
            case TOP: {
                snake.getBody().add(new Block(12, 4));
                snake.getBody().add(new Block(12, 3));
                snake.getBody().add(new Block(12, 2));
                snake.getBody().add(new Block(12, 1));
                snake.getBody().add(new Block(12, 0));
                break;
            }
            case BOTTOM: {
                snake.getBody().add(new Block(13, 20));
                snake.getBody().add(new Block(13, 21));
                snake.getBody().add(new Block(13, 22));
                snake.getBody().add(new Block(13, 23));
                snake.getBody().add(new Block(13, 24));
                break;
            }
            case LEFT: {
                snake.getBody().add(new Block(4, 12));
                snake.getBody().add(new Block(3, 12));
                snake.getBody().add(new Block(2, 12));
                snake.getBody().add(new Block(1, 12));
                snake.getBody().add(new Block(0, 12));
                break;
            }
            case RIGHT: {
                snake.getBody().add(new Block(20, 13));
                snake.getBody().add(new Block(21, 13));
                snake.getBody().add(new Block(22, 13));
                snake.getBody().add(new Block(23, 13));
                snake.getBody().add(new Block(24, 13));
            }
        }
    }

    public void move(Snake snake, int vectorX, int vectorY, boolean hasEaten) {
        int x = snake.getBody().get(0).getX() + vectorX;
        int y = snake.getBody().get(0).getY() + vectorY;
        if (hasEaten) {
            snake.getBody().add(0, new Block(x, y));
        } else {
            snake.getBody().get(snake.getBody().size() - 1).setX(x);
            snake.getBody().get(snake.getBody().size() - 1).setY(y);
            snake.getBody().add(0, snake.getBody().get(snake.getBody().size() - 1));
            snake.getBody().remove(snake.getBody().size() - 1);
        }
    }

    public void gotEaten(Snake snake){
        snake.getBody().remove(snake.getBody().size()-1);
    }

    public enum Direction implements Serializable {
        TOP, BOTTOM, LEFT, RIGHT
    }


    public Card rotate(Card card) {
        Card c = new Card();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                c.getElements()[i][j].role = card.getElements()[j][7 - i - 1].role;
            }
        }
        return card;
    }
}

