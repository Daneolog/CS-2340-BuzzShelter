package com.example.johnbeckner.buzzshelter;

import java.io.Serializable;

/**
 * Created by Sam on 2/20/18.
 */

public class User implements Serializable {

    private boolean hasReservation;
    private String name;
    private final String id;
    private final String password;
    private final UserType userType;

    /**
     * Creates new user
     * @param name name of user
     * @param id id (username)
     * @param password password entered
     */
    public User(String name, String id, String password) {
        hasReservation = false;
        this.name = name;
        this.id = id;
        this.password = password;
        this.userType = UserType.GENERAL_USER;
    }

    /**
     * Creates new user
     * @param name name of user
     * @param id id (username)
     * @param password password entered
     * @param userType user type
     */
    public User(String name, String id, String password, UserType userType) {
        hasReservation = false;
        this.name = name;
        this.id = id;
        this.password = password;
        this.userType = userType;
    }

    /**
     * Creates default user
     */
    public User() {
        this("DEFAULT", "DEFAULT", "DEFAULT");
    }

    /**
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name to set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return id of user
     */
    public String getId() {
        return id;
    }

// --Commented out by Inspection START (4/15/2018 9:57 PM):
//    /**
//     * @param id id to set to
//     */
//    public void setId(String id) {
//        this.id = id;
//    }
// --Commented out by Inspection STOP (4/15/2018 9:57 PM)

    /**
     * @return password of user
     */
    public String getPassword() {
        return password;
    }

// --Commented out by Inspection START (4/15/2018 9:57 PM):
//    /**
//     * @param password password to set to
//     */
//    public void setPassword(String password) {
//        this.password = password;
//    }
// --Commented out by Inspection STOP (4/15/2018 9:57 PM)

// --Commented out by Inspection START (4/15/2018 9:57 PM):
//    /**
//     * @return user type
//     */
//    public UserType getUserType() {
//        return userType;
//    }
// --Commented out by Inspection STOP (4/15/2018 9:57 PM)

// --Commented out by Inspection START (4/15/2018 9:57 PM):
//    /**
//     * @param userType user type to set to
//     */
//    public void setUserType(UserType userType) {
//        this.userType = userType;
//    }
// --Commented out by Inspection STOP (4/15/2018 9:57 PM)

    /**
     * @return if current user has reservation
     */
    public boolean isHasReservation() {
        return hasReservation;
    }

    /**
     * @param hasReservation value of reservation
     */
    public void setHasReservation(boolean hasReservation) {
        this.hasReservation = hasReservation;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User u1 = (User) obj;
        String name = this.getName();
        return name.equalsIgnoreCase(u1.getName());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = (47 * hash) + name.hashCode();
        hash = (47 * hash) + id.hashCode();
        hash = (47 * hash) + password.hashCode();
        return hash;
    }

}
