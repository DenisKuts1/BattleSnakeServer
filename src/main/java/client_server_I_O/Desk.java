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

    private Role matrix[][];
    private User mainUser;

    public Desk() {
        matrix = new Role[25][25];
        clear();
    }

    public void clear() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
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
        for (int i = 0; i < snake.size() - 2; i++) {
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
        for (int i = 0; i < snake.size() - 2; i++) {
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
                if (card.getCell(i, j).equals(Role.OWN_HEAD)) {
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
                if (x < 0 || x > 24 || y < 0 || y > 24) {
                    deskRole = Role.BARRIER;
                } else {
                    deskRole = matrix[x][y];
                }
                Role cardRole = card.getCell(i, j);
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

    public Card.Direction getRandomMove() {
        int headX = mainUser.getSnake().getBody().get(0).getX();
        int headY = mainUser.getSnake().getBody().get(0).getY();
        ArrayList<Card.Direction> directions = new ArrayList<>();
        if (headX > 0) {
            if (matrix[headX - 1][headY].equals(Role.ENEMY_TAIL)) {
                return Card.Direction.LEFT;
            } else {
                directions.add(Card.Direction.LEFT);
            }
        }
        if (headY > 0) {
            if (matrix[headX][headY - 1].equals(Role.ENEMY_TAIL)) {
                return Card.Direction.FORWARD;
            } else {
                directions.add(Card.Direction.FORWARD);
            }
        }
        if (headX < 24) {
            if (matrix[headX + 1][headY].equals(Role.ENEMY_TAIL)) {
                return Card.Direction.RIGHT;
            } else {
                directions.add(Card.Direction.RIGHT);
            }
        }
        if (headY < 24) {
            if (matrix[headX][headY + 1].equals(Role.ENEMY_TAIL)) {
                return Card.Direction.BACKWARD;
            } else {
                directions.add(Card.Direction.BACKWARD);
            }
        }
        int size;
        if ((size = directions.size()) == 0) {
            return null;
        }
        int random = new Random().nextInt(directions.size());
        return directions.get(random);
    }





    /*private Card.Cell[][] matrix;

    public Desk() {
        matrix = new Card.Cell[20][20];
    }

    public void draw(Snake... snakes) {
        for (Card.Cell[] row : matrix) {
            for (Card.Cell cell : row) {
                cell = Card.Cell.EMPTY;
            }
        }
        for (Snake snake : snakes) {
            matrix[snake.getBlock(0).getX()][snake.getBlock(0).getY()] = Card.Cell.HEAD;
            for (int i = 1; i < snake.getSize() - 2; i++) {
                matrix[snake.getBlock(i).getX()][snake.getBlock(i).getY()] = Card.Cell.BODY;
            }
            matrix[snake.getBlock(snake.getSize() - 1).getX()][snake.getBlock(snake.getSize() - 1).getY()] = Card.Cell.TAIL;
        }
    }

    public boolean compare(Snake snake, Card card) {
        Card.Cell[][] subMatrix = getSubMatrix(snake.getBlock(0).getX(), snake.getBlock(0).getY());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (!subMatrix[i][j].equals(card.getCell(i, j))) {
                    return false;
                }

            }
        }
        return true;

    }

    public Card.Cell[][] getSubMatrix(int x, int y) {
        Card.Cell[][] subMatrix = new Card.Cell[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                int xx = x - 3 + i;
                int yy = y - 3 + j;
                if (xx < 0 || xx > 19 || yy < 0 || yy > 19) {
                    subMatrix[i][j] = Card.Cell.BORDER;
                } else {
                    subMatrix[i][j] = matrix[xx][yy];
                }
            }
        }
        return subMatrix;
    }

    public boolean canEat(Snake snake) {
        int x = snake.getBlock(0).getX();
        int y = snake.getBlock(0).getY();
        int xx = snake.getBlock(snake.getSize() - 1).getX();
        int yy = snake.getBlock(snake.getSize() - 1).getY();
        if (y != yy) {
            if (x >= 0 && matrix[x - 1][y].equals(Card.Cell.TAIL)) {
                return true;
            }

            if (x < 20 && matrix[x + 1][y].equals(Card.Cell.TAIL)) {
                return true;
            }
        }
        if (x != xx) {
            if (y >= 0 && matrix[x][y - 1].equals(Card.Cell.TAIL)) {
                return true;
            }
            if (y < 20 && matrix[x][y + 1].equals(Card.Cell.TAIL)) {
                return true;
            }
        }
        return false;
    }

    public void randomMove(Snake snake){

    }

*/
}
