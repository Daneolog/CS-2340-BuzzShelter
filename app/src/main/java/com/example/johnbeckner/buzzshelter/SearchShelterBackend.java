package com.example.johnbeckner.buzzshelter;

import java.util.ArrayList;

/**
 * Created by Sam on 3/6/18.
 */

public class SearchShelterBackend {
    private static ArrayList<Shelter> shelters;

    /**
     * Add a shelter to the arraylist
     * @param newShelter the shelter to be added to the list
     */
    private static void addShelter(Shelter newShelter) {
        if (shelters == null) {
            shelters = new ArrayList<>();
        }
        shelters.add(newShelter);
    }

    /**
     * Search for a shelter by name and return the shelter
     * @param name the String to compare names to
     * @return shelter the shelter that's found. null if not found
     */
    private static Shelter searchShelterByName(String name) {
        for (Shelter shelter : shelters) {
            if (shelter.getShelterName().equalsIgnoreCase(name)) {
                return shelter;
            }
        }
        return null;
    }

    /**
     * Search for a shelter by gender and return the shelter
     * Using a CharSequence to search
     * @param gender the gender to search for.
     * @return shelter
     */
    private static Shelter searchShelterByGender(String gender) {
        for (Shelter shelter : shelters) {
            if (shelter.getRestrictions().contains(gender)) {
                return shelter;
            }
        }
        return null;
    }

    /**
     * Search for a shelter by age and return the shelter
     * Using a CharSequence to search
     * @param age the age to search for
     * @return shelter
     */
    private static Shelter searchShelterByAge(String age) {
        for (Shelter shelter : shelters) {
            if (shelter.getRestrictions().contains(age)) {
                return shelter;
            }
        }
        return null;
    }
}
