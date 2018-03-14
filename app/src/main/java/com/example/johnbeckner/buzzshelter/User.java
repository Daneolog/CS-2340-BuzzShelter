package com.example.johnbeckner.buzzshelter;

import java.io.Serializable;

/**
 * Created by Sam on 2/20/18.
 */

public class User implements Serializable {
    private String name;
    private String id;
    private String password;
    private UserType userType;

    public User(String name, String id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.userType = UserType.GENERAL_USER;
    }

    public User(String name, String id, String password, UserType userType) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.userType = userType;
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
        if (this.getId().equalsIgnoreCase(u1.getId())) {
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
