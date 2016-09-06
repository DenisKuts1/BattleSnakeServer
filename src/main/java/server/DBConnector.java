package server;

import client_server_I_O.classes.*;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Анатолий on 21.07.2016.
 */
public class DBConnector {


    File usersFile;


    public DBConnector() {
        usersFile = new File("db\\users.txt");
        if (!usersFile.exists()) {
            try {
                usersFile.createNewFile();
            } catch (IOException e) {
                System.out.println("wer");
            }
        }
    }

    public boolean addUser(User user) {
        System.out.println(user.getPassword());
        if (userExists(user.getLogin())) {
            return false;
        } else {
            user.setSnake(new Snake());
            user.getSnake().setRating(1000);
            user.getSnake().setAbout("");
            user.getSnake().setAvatar(new Avatar());
            Random random = new Random();
            String path = Paths.get("").toAbsolutePath().toUri().normalize().toString() + "resources/snake" + (random.nextInt(4) + 1) + ".png";
            System.out.println(path);
            user.getSnake().getAvatar().setImageBytes(getBytesFromImage(path ));
            user.getSnake().setBody(new ArrayList<>());
            user.getSnake().setColor(getRandomColor());
            user.getSnake().setName("");
            user.getSnake().setCards(new Card[3][3]);
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    user.getSnake().getCards()[i][j] = new Card();
                    user.getSnake().getCards()[i][j].setElements(new CardElement[7][7]);
                    for(int k = 0; k < 7; k++){
                        for(int l = 0; l < 7; l++){
                            user.getSnake().getCards()[i][j].getElements()[k][l] = new CardElement();
                            user.getSnake().getCards()[i][j].getElements()[k][l].setRole(8);
                        }
                    }
                }
            }
            File userFile = new File("db\\users\\" + user.getLogin() + ".txt");
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(userFile))) {
                outputStream.writeObject(user);
                outputStream.flush();

            } catch (IOException e) {
                return false;
            }
            try (FileWriter writer = new FileWriter(usersFile, true)) {
                writer.write(user.getLogin() + "\n");
                writer.flush();
            } catch (IOException e) {
            }

            return true;
        }
    }

    private static String getRandomColor() {
        double red = Math.random();
        double green = Math.random();
        double blue = Math.random();

        return new Color(red,green,blue, 1.0).toString();
    }

    public static byte[] getBytesFromImage(String path) {
        try {
            BufferedImage image = ImageIO.read(new URL(path));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean userExists(String username) {
        boolean flag = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            String name;
            while ((name = reader.readLine()) != null) {
                if (name.equals(username)) {
                    flag = true;
                    break;
                }
            }

        } catch (IOException e) {
            flag = false;
        }
        return flag;
    }

    public User getUser(String username, String password) {
        if (!userExists(username)) {
            return null;
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("db\\users\\" + username + ".txt"))) {
            System.out.println("db\\users\\" + username + ".txt");
            User user = (User) inputStream.readObject();
            System.out.println(user.getPassword());
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(1);
            return null;
        }

    }

    public User getUser(String username) {
        if (!userExists(username)) {
            return null;
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("db\\users\\" + username + ".txt"))) {
            User user = (User) inputStream.readObject();
            return user;
        } catch (Exception e) {
            return null;
        }

    }

    public boolean updateUser(User user) {
        if (!userExists(user.getLogin())) {
            return false;
        }
        File userFile = new File("db\\users\\" + user.getLogin() + ".txt");
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(userFile))) {
            outputStream.writeObject(user);
            outputStream.flush();

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public ArrayList<Snake> getUsers() {
        String name;
        ArrayList<Snake> arrayList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            while ((name = reader.readLine()) != null) {
                User user = getUser(name);
                arrayList.add(user.getSnake());
            }
        } catch (IOException e) {
        }
        return arrayList;
    }


}
