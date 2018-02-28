package com.example.johnbeckner.buzzshelter;
import android.location.Address;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

/**
 *
 */

public class Shelter {
    private String shelterName;
    private String capacity;
    private String Restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNumber;
    private String notes;

    public Shelter(String shelterName, String capacity, String Restrictions,
        double longitude, double latitude, String address, String phoneNumber, String notes) {
        this.shelterName = shelterName;
        this.capacity = capacity;
        this.Restrictions = Restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
    }
    public Shelter() {

    }

    public String getShelterName() {
        return shelterName;
    }
    public void setShelterName(String name) {
        shelterName = name;
    }
    public String getCapacity() {
        return capacity;
    }
    public void setCapacity(String data) {
        capacity = data;
    }
    public String getRestrictions() {
        return Restrictions;
    }
    public void setRestrictions(String data) {
        Restrictions = data;
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

    public String toString() {
        return shelterName;
    }

    public String[] getInfo() {
        return new String[] {
                "Shelter Name: " + shelterName,
                "Capacity: " + capacity,
                "Restrictions: " + Restrictions,
                "Longitude: " + Double.toString(longitude),
                "Latitude:" + Double.toString(latitude),
                "Address: " + address,
                "Phone Number: " + phoneNumber,
                "Notes: " + notes,
        };
    }
}
