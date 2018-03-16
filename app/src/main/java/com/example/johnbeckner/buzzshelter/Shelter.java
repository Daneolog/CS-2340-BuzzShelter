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

        private int numBeds;
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
    private HashMap<String, Integer> reservations;

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
    public Shelter() {
        reservations = new HashMap<>();
    }

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

    public String getShelterName() {
        return shelterName;
    }
    public void setShelterName(String name) {
        shelterName = name;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int data) {
        capacity = data;
    }
    public String getRestrictions() {
        return restrictions;
    }
    public void setRestrictions(String data) {
        restrictions = data;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double data) {
        longitude = data;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double data) {
        latitude = data;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String data) {
        phoneNumber = data;
    }
    public void addNotes(String note) {
        if (this.notes == null) {
            this.notes = "";
        }
        this.notes = this.notes  + " " + note;
    }
    public String getNotes() {
        return notes;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null || !(that instanceof Shelter)) {
            return false;
        }
        Shelter thatShelter = (Shelter) that;

        return (
                this.getShelterName().equals(thatShelter.getShelterName())
                && this.getAddress().equals(thatShelter.getAddress())
                && this.getPhoneNumber().equals(thatShelter.getPhoneNumber()));
    }

    public void addReservation(String user, int count) {
        if (reservations.containsKey(user)) {
            reservations.put(user, reservations.get(user) + count);
        } else {
            reservations.put(user, count);
        }
    }
    public HashMap<String, Integer> getReservations() { return reservations; }

    public String toString() {
        return shelterName;
    }

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

    public void reserve(String user, int reserve) {
        if (capacity < reserve) {
            throw new RuntimeException("Reserved less than was supposed to...");
        } else {
            capacity -= reserve;
            addReservation(user, reserve);
        }
    }

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

    public void cancelReserve(int reserve) {
        capacity += reserve;
    }
}
