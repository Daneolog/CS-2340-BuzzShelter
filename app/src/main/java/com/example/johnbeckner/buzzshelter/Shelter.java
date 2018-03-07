package com.example.johnbeckner.buzzshelter;
import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

/**
 *
 */

public class Shelter implements Parcelable{
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

    protected Shelter(Parcel in) {
        String[] data = new String[8];

        in.readStringArray(data);

        this.shelterName = data[0];
        this.capacity = data[1];
        this.Restrictions = data[2];
        this.longitude = Double.parseDouble(data[3]);
        this.latitude = Double.parseDouble(data[4]);
        this.address = data[5];
        this.phoneNumber = data[6];
        this.notes = data[7];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {
                this.shelterName,
                this.capacity,
                this.Restrictions,
                Double.toString(this.longitude),
                Double.toString(this.latitude),
                this.address,
                this.phoneNumber,
                this.notes});
    }
}
