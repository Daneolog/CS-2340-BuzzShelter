package com.example.johnbeckner.buzzshelter;

/**
 * Created by John Beckner on 2/21/2018.
 */

public enum UserType {
    GENERAL_USER ("General User"),
    SHELTER_EMPLOYEE ("Shelter Employee"),
    ADMIN("Admin");

    private final String name;

    UserType(String name) {
        this.name = name;
    }

// --Commented out by Inspection START (4/15/2018 9:57 PM):
//    /**
//     * @return string value of user type
//     */
//    public String getName() {
//        return name;
//    }
// --Commented out by Inspection STOP (4/15/2018 9:57 PM)

    @Override
    public String toString() {
        return name;
    }
}
