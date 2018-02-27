package com.example.johnbeckner.buzzshelter;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

/**
 *
 */

public class Shelter {
    private String shelterName;
    private int capacity;
    private String gender;
    private double longitude;
    private double latitude;
    private String address;
    private int phoneNumber;

    public Shelter(String shelterName, int capacity, String gender,
        double longitude, double latitude, String address, int phoneNumber) {
        this.shelterName = shelterName;
        this.capacity = capacity;
        this.gender = gender;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;

    }
    public Shelter() {

    }

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
    public String getGender() {
        return gender;
    }
    public void setGender(String data) {
        gender = data;
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
    public String getAddress() {
        return address;
    }
    public int getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(int data) {
        phoneNumber = data;
    }
}
