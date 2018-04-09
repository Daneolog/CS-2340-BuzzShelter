package com.example.johnbeckner.buzzshelter;
import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

/**
 *
 */

public class Shelter implements Parcelable, Serializable {
    enum ShelterType {
        SINGLE    (1),
        FAMILY    (2),
        APARTMENT (4);

        private final int numBeds;
        ShelterType(int numBeds) {
            this.numBeds = numBeds;
        }
    }

    private String shelterName;
    private int capacity;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNumber;
    private String notes;
    private final HashMap<String, Integer> reservations;

    /**
     * Creates a new Shelter
     * @param shelterName  name of shelter
     * @param capacity     initial max capacity of shelter
     * @param restrictions restrictions of residents of shelter
     * @param longitude    location (longitude) of shelter
     * @param latitude     location (latitude) of shelter
     * @param address      location (street address) of shelter
     * @param phoneNumber  phone number of shelter
     * @param notes        special notes about shelter
     */
    public Shelter(String shelterName, int capacity, String restrictions,
        double longitude, double latitude, String address, String phoneNumber, String notes) {
        this.shelterName = shelterName;
        this.capacity = capacity;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        reservations = new HashMap<>();
    }

    /**
     * Creates a new Shelter
     */
    public Shelter() {
        reservations = new HashMap<>();
    }

    /**
     * Creates a new shelter based on parcelable object
     * @param in parcel
     */
    protected Shelter(Parcel in) {
        String[] data = new String[8];

        in.readStringArray(data);

        this.shelterName = data[0];
        this.capacity = Integer.parseInt(data[1]);
        this.restrictions = data[2];
        this.longitude = Double.parseDouble(data[3]);
        this.latitude = Double.parseDouble(data[4]);
        this.address = data[5];
        this.phoneNumber = data[6];
        this.notes = data[7];
        reservations = (HashMap)in.readSerializable();
    }

    public static final Creator<Shelter> CREATOR = new Creator<Shelter>() {
        @Override
        public Shelter createFromParcel(Parcel in) {
            return new Shelter(in);
        }

        @Override
        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };

    /**
     * @return shelter name
     */
    public String getShelterName() {
        return shelterName;
    }
    /**
     * @param name name to set
     */
    public void setShelterName(String name) {
        shelterName = name;
    }

    /**
     * @return shelter capacity
     */
    public int getCapacity() {
        return capacity;
    }
    /**
     * @param data capacity to set
     */
    public void setCapacity(int data) {
        capacity = data;
    }

    /**
     * @return shelter restrictions
     */
    public String getRestrictions() {
        return restrictions;
    }
    /**
     * @param data restrictions to set
     */
    public void setRestrictions(String data) {
        restrictions = data;
    }

    /**
     * @return shelter longitude
     */
    public double getLongitude() {
        return longitude;
    }
    /**
     * @param data longitude to set
     */
    public void setLongitude(double data) {
        longitude = data;
    }

    /**
     * @return shelter latitude
     */
    public double getLatitude() {
        return latitude;
    }
    /**
     * @param data latitude to set
     */
    public void setLatitude(double data) {
        latitude = data;
    }

    /**
     * @return shelter address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return shelter phone
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * @param data phone to set
     */
    public void setPhoneNumber(String data) {
        phoneNumber = data;
    }

    /**
     * @return shelter notes
     */
    public String getNotes() {
        return notes;
    }
    /**
     * Adds extra notes
     * @param note notes to add
     */
    public void addNotes(String note) {
        if (this.notes == null) {
            this.notes = "";
        }
        this.notes = this.notes  + " " + note;
    }

    /**
     * Checks equality between other Object
     * @param that other object
     * @return equality between objects
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if ((that == null) || !(that instanceof Shelter)) {
            return false;
        }
        Shelter thatShelter = (Shelter) that;
        String shelterName = this.getShelterName();
        String address = this.getAddress();
        String phoneNumber = this.getPhoneNumber()
        return (
                shelterName.equals(thatShelter.getShelterName())
                && address.equals(thatShelter.getAddress())
                && phoneNumber.equals(thatShelter.getPhoneNumber()));
    }

    /**
     * Adds reservation to shelter's HashMap
     * @param user name of user to put reservation under
     * @param count number of reserved spots
     */
    public void addReservation(String user, int count) {
        if (reservations.containsKey(user)) {
            reservations.put(user, reservations.get(user) + count);
        } else {
            reservations.put(user, count);
        }
    }

    /**
     * @return reservations HashMap
     */
    public HashMap<String, Integer> getReservations() { return reservations; }

    /**
     * @return string representation of shelter
     */
    public String toString() {
        return shelterName;
    }

    /**
     * @return shelter information
     */
    public String[] getInfo() {
        return new String[] {
                "Shelter Name: " + shelterName,
                "Capacity: " + capacity,
                "Restrictions: " + restrictions,
                "Longitude: " + Double.toString(longitude),
                "Latitude:" + Double.toString(latitude),
                "Address: " + address,
                "Phone Number: " + phoneNumber,
                "Notes: " + notes,
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {
                this.shelterName,
                "" + this.capacity,
                this.restrictions,
                Double.toString(this.longitude),
                Double.toString(this.latitude),
                this.address,
                this.phoneNumber,
                this.notes});
        parcel.writeSerializable(getReservations());
    }

    /**
     * Reserve (with error handling) a spot for a user
     * @param user user to reserve spots for
     * @param reserve number of spots to reserve
     */
    public void reserve(String user, int reserve) {
        if (capacity < reserve) {
            throw new RuntimeException("Reserved less than was supposed to...");
        } else {
            capacity -= reserve;
            addReservation(user, reserve);
        }
    }

    /**
     * Drop all reservations for a user
     * @param user user to drop reservations for
     * @return whether user has reservations currently
     */
    public boolean dropReservation(String user) {
        if (user == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        if (reservations.containsKey(user)) {
            capacity += reservations.get(user);
            reservations.remove(user);
            return true;
        } else {
            return false;
        }
    }

//    public void cancelReserve(int reserve) {
//        capacity += reserve;
//    }
}
