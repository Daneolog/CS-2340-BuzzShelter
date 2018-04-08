package com.example.johnbeckner.buzzshelter;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by John Beckner on 2/28/2018.
 */

public class ShelterList {

    private static ArrayList<Shelter> Shelters = new ArrayList<>();
    private static ArrayList<Shelter> FilteredList = new ArrayList<>();

    public static void addShelter (Shelter data) {
        if (data == null) {
            throw new IllegalArgumentException("input shelter is null");
        }
        if (Shelters == null) {
            Shelters = new ArrayList<>();
        }
        Shelters.add(data);
    }

    /*
    Parse data from out database
    Split[0] = unique key
    Split[1] = shelter name
    Split[2] = Capacity
    Split[3] = Restrictions
    Split[4] = Longitude
    Split[5] = Latitude
    Split[6] = Address
    Split[7 - (last - 1)] = Special notes
    Split[last] = Phone Number
     */

    public static void parseDatabase (InputStream database) {

        Shelters = new ArrayList<>();

        String line = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(database, Charset.forName("UTF-8"))
            );

            if ((line = br.readLine()) != null) {

            } else {
                return;
            }
            while ((line = br.readLine()) != null) {

                Log.e("line", line.toString());

                String[] split = line.split(";");
                for (int i = 0; i < split.length; i++) {
                    if ((split[i] == "") || (split[i] == null)) {
                        split[i] = "Not Available";
                    }
                }

                Shelter newShelter = new Shelter();
                newShelter.setShelterName(split[1]);

                if ((split[2] == null) || "".equals(split[2])) {
                    newShelter.setCapacity(0);
                } else {
                    Scanner scanner = new Scanner(split[2]);
                    newShelter.setCapacity(Integer.parseInt(scanner.findInLine("\\d+")));
                }

                Log.e(newShelter.getCapacity() + "", "");

                newShelter.setRestrictions(split[3]);
                newShelter.setLongitude((split[4].matches("([-+]?\\d+(\\.\\d+)?)"))
                        ? Double.parseDouble(split[4]): 0);
                newShelter.setLatitude((split[5].matches("([-+]?\\d+(\\.\\d+)?)"))
                        ? Double.parseDouble(split[5]): 0);
                newShelter.setAddress(split[6]);
                for (int i = 7; i < (split.length - 1); i++) {
                    newShelter.addNotes(split[i]);
                }
                newShelter.setPhoneNumber(split[split.length - 1]);
                addShelter(newShelter);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.e("Shelter list size", Shelters.size() + "");

            }
        }
    }



    public static ArrayList<Shelter> getShelters() {
        return Shelters;
    }

    public static void setShelters(ArrayList<Shelter> shelters) {
        Shelters = shelters;
    }

    public static Shelter findShelter(Shelter find) {
        if (find == null) {
            throw new IllegalArgumentException("input shelter cannot be null");
        }

        for (Shelter s : Shelters) {
            if (s.equals(find)) {
                return s;
            }
        }
        Log.e("Find Shelter", "input shelter not in list");
        return new Shelter();
    }

    public static ArrayList<Shelter> getFilteredList() {
        return FilteredList;
    }

    public static void setFilteredList(ArrayList<Shelter> filteredList) {
        FilteredList = filteredList;
    }

    public static void filterShelters(String name, String gender, String ageRange) {

        FilteredList = new ArrayList<>();

        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (gender == null) {
            throw new IllegalArgumentException("gender cannot be null");
        }
        if (ageRange == null) {
            throw new IllegalArgumentException("age range cannot be null");
        }

        FilteredList = new ArrayList<>(Shelters);

        for (Shelter s : Shelters) {
            // filter by name
            String shelterName = s.getShelterName();
            String restrictions = s.getRestrictions();

            shelterName = shelterName.toLowerCase();
            if (!(shelterName.contains(name.toLowerCase()))) {
                FilteredList.remove(s);
            }
            // filter gender
            if ("Anyone".equals(gender)) {
                gender = "";
            }
            if (!(restrictions.contains(gender))) {
                FilteredList.remove(s);
            }

            // filter age range
            if ("Anyone".equals(ageRange)) {
                ageRange = "";
            }
            restrictions = restrictions.toLowerCase();
            if (!(restrictions.contains(ageRange.toLowerCase()))) {
                FilteredList.remove(s);
            }
        }
    }
}
