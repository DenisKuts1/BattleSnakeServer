package server;

import client_server_I_O.classes.Snake;
import client_server_I_O.classes.User;

import java.io.*;
import java.util.ArrayList;


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
        if (userExists(user.getLogin())) {
            return false;
        } else {
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
            System.out.println(1);
            if (user.getPassword().equals(password)) {
                return user;
            } else return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
