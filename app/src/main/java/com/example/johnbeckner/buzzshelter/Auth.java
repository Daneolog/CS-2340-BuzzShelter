package com.example.johnbeckner.buzzshelter;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sam on 2/20/18.
 */

class Auth implements Serializable {
    private static ArrayList<User> users;

    /**
     * Getter for users array
     * @return ArrayList of users
     */
    public static ArrayList<User> getUsers() {
        return (users);
    }

    /**
     * Setter for users array
     * @param users ArrayList to set users to
     */
    public static void setUsers(ArrayList<User> users) {
        Auth.users = new ArrayList<>(users);
    }

    /**
     * Searches the list to authenticate user
     * @param username username of user to authenticate
     * @param password password of user to authenticate
     * @return boolean value, true = user authenticated
     */
    public static User authenticate(String username, String password) {
        if ((users == null) || (users.isEmpty())) {
            // no users exist
            return null;
        }
        for(User user : users) {
            if(username.equalsIgnoreCase(user.getId())) {
                if(password.equals(user.getPassword())) {
                    return user;
                }
            }
        }
        return null;
    }

// --Commented out by Inspection START (4/15/2018 9:57 PM):
//    /**
//     * Adds a user to database
//     * @param name name of user to add
//     * @param ID id to add
//     * @param password password of user to add
//     * @param userType user type (general user, shelter employee, or admin)
//     */
//    public static void addUser(String name, String ID, String password, UserType userType) {
//        if (users == null) {
//            users = new ArrayList<>();
//        }
//        User temp = new User(name, ID, password, userType);
//        users.add(temp);
//    }
// --Commented out by Inspection STOP (4/15/2018 9:57 PM)

    /**
     * Adds new user to database
     * @param newUser user to add
     */
    public static void addUser(User newUser) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(newUser);
    }

// --Commented out by Inspection START (4/15/2018 9:57 PM):
//    /**
//     * Removes user from database
//     * @param ID id of user to remove
//     * @return whether user was removed
//     */
//    public static boolean removeUser(String ID) {
//        if ((users == null) || (users.isEmpty())) {
//            return false;
//        }
//        int index = -1;
//        for (User user : users) {
//            if (ID.equals(user.getId())) {
//                index = users.indexOf(user);
//                break;
//            }
//        }
//        if (index == -1) {
//            return false; // user not found
//        } else {
//            users.remove(index);
//            return true; // user found and removed
//        }
//    }
// --Commented out by Inspection STOP (4/15/2018 9:57 PM)

    /**
     * Finds and returns user
     * @param find user to find
     * @return found user, null if not found
     */
    public static User findUser(User find) {
        if ((users == null) || users.isEmpty()) {
            return new User();
        }
        if (find == null) {
            throw new IllegalArgumentException("input cannot be null");
        }
        for (User u : users) {
            if (u.equals(find)) {
                return u;
            }
        }
        Log.e("Find Shelter", "input shelter not in list");
        return null;
    }
}
