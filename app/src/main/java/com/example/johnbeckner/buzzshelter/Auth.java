package com.example.johnbeckner.buzzshelter;

import java.util.ArrayList;

/**
 * Created by Sam on 2/20/18.
 */

public class Auth {
    private static ArrayList<User> users;

    /**
     * Searches the list to authenticate user
     * @return boolean value, true = user authenticated
     */
    public static boolean authenticate(String username, String password) {
        if (users == null || users.size() == 0) {
            // no users exist
            return false;
        }
        for(User user : users) {
            if(username.equalsIgnoreCase(user.getId())) {
                if(password.equals(user.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void addUser(String name, String ID, String password, UserType userType) {
        if (users == null) {
            users = new ArrayList<>();
        }
        User temp = new User(name, ID, password, userType);
        users.add(temp);
    }

    public static void addUser(User newUser) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(newUser);
    }

    public static boolean removeUser(String ID) {
        if (users == null || users.size() == 0) {
            return false;
        }
        int index = -1;
        for (User user : users) {
            if (ID.equals(user.getId())) {
                index = users.indexOf(user);
                break;
            }
        }
        if (index == -1) {
            return false; // user not found
        } else {
            users.remove(index);
            return true; // user found and removed
        }
    }
}
