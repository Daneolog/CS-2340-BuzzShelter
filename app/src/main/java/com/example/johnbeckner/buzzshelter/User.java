package com.example.johnbeckner.buzzshelter;

import java.io.Serializable;

/**
 * Created by Sam on 2/20/18.
 */

public class User implements Serializable {

    private boolean hasReservation;
    private String name;
    private String id;
    private String password;
    private UserType userType;
    private boolean banned;


    public User(String name, String id, String password) {
        hasReservation = false;
        banned = false;
        this.name = name;
        this.id = id;
        this.password = password;
        this.userType = UserType.GENERAL_USER;
    }

    public User(String name, String id, String password, UserType userType) {
        hasReservation = false;
        banned = false;
        this.name = name;
        this.id = id;
        this.password = password;
        this.userType = userType;
    }

    public User() {
        this("DEFAULT", "DEFAULT", "DEFAULT");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isHasReservation() {
        return hasReservation;
    }

    public void setHasReservation(boolean hasReservation) {
        this.hasReservation = hasReservation;
    }

    public boolean getBanned() { return this.banned; }
    public void setBanned(boolean ban) {this.banned = ban;}

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
        if (this.getName().equalsIgnoreCase(u1.getName())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + name.hashCode();
        hash = 47 * hash + id.hashCode();
        hash = 47 * hash + password.hashCode();
        return hash;
    }

}
