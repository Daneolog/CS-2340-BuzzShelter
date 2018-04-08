package com.example.johnbeckner.buzzshelter;

/**
 * Created by John Beckner on 2/21/2018.
 */

public enum UserType {
    GENERAL_USER ("General User"),
    SHELTER_EMPLOYEE ("Shelter Employee"),
    ADMIN("Admin");

    private String name;

    UserType(String name) {
        this.name = name;
    }

    /**
     * @return string value of user type
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
