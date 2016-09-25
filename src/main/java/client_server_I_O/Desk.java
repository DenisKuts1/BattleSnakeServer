package client_server_I_O;

import client_server_I_O.classes.Block;
import client_server_I_O.classes.Card;
import client_server_I_O.classes.Role;
import client_server_I_O.classes.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Анатолий on 25.07.2016.
 */
public class Desk implements Serializable {


    public static final int n = 12;
    private Role matrix[][];
    private User mainUser;

    public Desk() {
        matrix = new Role[n][n];
        clear();
    }

    public void clear() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Role.EMPTY;
            }
        }
    }

    public void drawUser(User user) {
        mainUser = user;
        int x, y;
        ArrayList<Block> snake = user.getSnake().getBody();
        x = snake.get(0).getX();
        y = snake.get(0).getY();
        matrix[x][y] = Role.OWN_HEAD;
        for (int i = 1; i < snake.size() - 1; i++) {
            x = snake.get(i).getX();
            y = snake.get(i).getY();
            matrix[x][y] = Role.OWN_BODY;
        }
        x = snake.get(snake.size() - 1).getX();
        y = snake.get(snake.size() - 1).getY();
        matrix[x][y] = Role.OWN_TAIL;
    }

    public void drawEnemy(User user) {
        int x, y;
        ArrayList<Block> snake = user.getSnake().getBody();
        x = snake.get(0).getX();
        y = snake.get(0).getY();
        matrix[x][y] = Role.ENEMY_HEAD;
        for (int i = 1; i < snake.size() - 1; i++) {
            x = snake.get(i).getX();
            y = snake.get(i).getY();
            matrix[x][y] = Role.ENEMY_BODY;
        }
        x = snake.get(snake.size() - 1).getX();
        y = snake.get(snake.size() - 1).getY();
        matrix[x][y] = Role.ENEMY_TAIL;
    }

    public boolean checkCard(Card card) {
        int headX = mainUser.getSnake().getBody().get(0).getX();
        int headY = mainUser.getSnake().getBody().get(0).getY();
        int cardHeadX = 0, cardHEadY = 0;
        Label:
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (Role.role(card.getElements()[i][j].getRole()).equals(Role.OWN_HEAD)) {
                    cardHeadX = i;
                    cardHEadY = j;
                    break Label;
                }
            }
        }
        int startX = headX - cardHeadX;
        int startY = headY - cardHEadY;
        boolean or = false, andOrange = true, andPink = true, allField = true, except = true;
        Label:
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Role deskRole;
                int x = startX + i;
                int y = startY + j;
                if (x < 0 || x > n - 1 || y < 0 || y > n - 1) {
                    deskRole = Role.BARRIER;
                } else {
                    deskRole = matrix[x][y];
                }
                Role cardRole = Role.role(card.getElements()[i][j].getRole());
                if (Role.andOrangeRoles.contains(cardRole)) {
                    if (!deskRole.equals(cardRole.getRole())) {
                        andOrange = false;
                        break Label;
                    }
                } else if (Role.andPinkRoles.contains(cardRole)) {
                    if (!deskRole.equals(cardRole.getRole())) {
                        andPink = false;
                        break Label;
                    }
                } else if (Role.orRoles.contains(cardRole)) {
                    if (deskRole.equals(cardRole.getRole())) {
                        or = true;
                    }
                } else if (Role.exceptRole.contains(cardRole)) {
                    if (!deskRole.equals(cardRole.getRole())) {
                        except = false;
                        break Label;
                    }
                } else {
                    if (!deskRole.equals(cardRole.getRole())) {
                        allField = false;
                        break Label;
                    }
                }
            }
        }
        return or && andOrange && andPink && except && allField;
    }

    public boolean canEat(int vectorX, int vectorY) {
        int headX = mainUser.getSnake().getBody().get(0).getX();
        int headY = mainUser.getSnake().getBody().get(0).getY();
        int x = headX + vectorX;
        int y = headY + vectorY;
        return matrix[x][y].equals(Role.ENEMY_TAIL);
    }

    public Game.Direction getRandomMove() {
       /* System.out.println();
        System.out.println();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(matrix[i][j].equals(Role.ENEMY_HEAD)){
                    System.out.println("EH " + i + j);
                }
                if(matrix[i][j].equals(Role.ENEMY_BODY)){
                    System.out.println("EB " + i + j);
                }
                if(matrix[i][j].equals(Role.ENEMY_TAIL)){
                    System.out.println("ET " + i + j);
                }
                if(matrix[i][j].equals(Role.OWN_HEAD)){
                    System.out.println("OH " + i + j);
                }
                if(matrix[i][j].equals(Role.OWN_BODY)){
                    System.out.println("OB " + i + j);
                }
                if(matrix[i][j].equals(Role.OWN_TAIL)){
                    System.out.println("OT " + i + j);
                }
            }
        }*/
        int headX = mainUser.getSnake().getBody().get(0).getX();
        int headY = mainUser.getSnake().getBody().get(0).getY();
        ArrayList<Game.Direction> directions = new ArrayList<>();
        if (headX > 0) {
            if (matrix[headX - 1][headY].equals(Role.ENEMY_TAIL)) {
                return Game.Direction.LEFT;
            } else if (matrix[headX - 1][headY].equals(Role.EMPTY)) {
                directions.add(Game.Direction.LEFT);
            }
        }
        if (headY > 0) {
            if (matrix[headX][headY - 1].equals(Role.ENEMY_TAIL)) {
                return Game.Direction.TOP;
            } else if (matrix[headX][headY - 1].equals(Role.EMPTY)) {
                directions.add(Game.Direction.TOP);
            }
        }
        if (headX < n - 1) {
            if (matrix[headX + 1][headY].equals(Role.ENEMY_TAIL)) {
                return Game.Direction.RIGHT;
            } else if (matrix[headX + 1][headY].equals(Role.EMPTY)) {
                directions.add(Game.Direction.RIGHT);
            }
        }
        if (headY < n - 1) {
            if (matrix[headX][headY + 1].equals(Role.ENEMY_TAIL)) {
                return Game.Direction.BOTTOM;
            } else if (matrix[headX][headY + 1].equals(Role.EMPTY)) {
                directions.add(Game.Direction.BOTTOM);
            }
        }
        int size;
        if ((size = directions.size()) == 0) {
            return null;
        }
        int random = new Random().nextInt(size);
        return directions.get(random);
    }
}
