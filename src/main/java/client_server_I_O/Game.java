package client_server_I_O;

import client_server_I_O.classes.*;
import client_server_I_O.classes.Message;
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
    Socket socket;
    boolean stop = false;


    public ArrayList<User> getUsers() {
        return users;
    }

    public Game(Socket socket,User... users) {
        this.socket = socket;
        this.users = new ArrayList<>();
        for (User user : users) {
            this.users.add(user);
        }
        new Thread(this::loop).start();


    }

    private void sendGame(ArrayList<Step> steps) {
        Message message = new Message(steps);
        send(socket, message);

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
        int n = users.size();
        startPosition(users.get(0).getSnake(), Direction.TOP);
        startPosition(users.get(1).getSnake(), Direction.RIGHT);
        if(n > 2) {
            startPosition(users.get(2).getSnake(), Direction.BOTTOM);
            if(n > 3) {
                startPosition(users.get(3).getSnake(), Direction.LEFT);
            }
        }

        ArrayList<Step> steps = new ArrayList<>();
        int time = 0;
        while (!stop) {
            time++;
            Step step = new Step();
            step.setBody(new HashMap<>());
            for(int i = 0; i < n; i++)
            {
                User user = users.get(i);
                if(user == null){
                    continue;
                }
                desk.clear();
                for (User enemy : users) {
                    if (!enemy.equals(user)) {
                        desk.drawEnemy(enemy);
                    } else {
                        desk.drawUser(user);
                    }
                }
                Card card = null;
                int jj = 0;
                Label:
                for (Card []cc : user.getSnake().getCards()) {
                    for (Card c : cc) {
                        jj = 0;
                        for (int j = 0; j < 3; j++) {
                            if (desk.checkCard(c)) {
                                card = c;
                                break Label;
                            }

                            c = rotate(c);
                            jj++;
                        }
                    }
                }
                if (card != null) {
                    switch (jj){
                        case 0:{
                            moveSnake(desk, user, Direction.TOP);
                            break;
                        }
                        case 1:{
                            moveSnake(desk, user, Direction.RIGHT);
                            break;
                        }
                        case 2:{
                            moveSnake(desk, user, Direction.BOTTOM);
                            break;
                        }
                        default:{
                            moveSnake(desk, user, Direction.LEFT);
                            break;
                        }
                    }

                } else {
                    Direction direction;
                    if((direction = desk.getRandomMove()) != null) {
                        moveSnake(desk, user, direction);
                    }
                }
                step.getBody().put(i,user.getSnake().getBody());
            }
            steps.add(step);

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
            if(time == 200){
                stop = true;
                for(User user : allUsers){
                    if(users.contains(user)){
                        user.getSnake().setRating(user.getSnake().getRating() + 5);
                    } else {
                        user.getSnake().setRating(user.getSnake().getRating() - 5);
                    }
                }
            }
        }
        sendGame(steps);

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

