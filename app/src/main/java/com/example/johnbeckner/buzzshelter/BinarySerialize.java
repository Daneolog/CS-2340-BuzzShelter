package com.example.johnbeckner.buzzshelter;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by John Beckner on 3/13/2018.
 */

public class BinarySerialize implements Serializable {

    private ArrayList<Shelter> Shelters;
    private ArrayList<Shelter> FilteredList;
    private ArrayList<User> UserList;
    private BinarySerialize bs;

    public BinarySerialize() {
        Shelters = new ArrayList<Shelter>();
        FilteredList = new ArrayList<Shelter>();
        UserList = new ArrayList<User>();
    }

    public BinarySerialize(ArrayList<Shelter> Shelters, ArrayList<Shelter> FilteredList, ArrayList<User> UserList) {
        this.Shelters = Shelters;
        this.FilteredList = FilteredList;
        this.UserList = UserList;
    }

    public ArrayList<Shelter> getShelters() {
        return Shelters;
    }

    public ArrayList<Shelter> getFilteredList() {
        return FilteredList;
    }

    public ArrayList<User> getUserList() {
        return UserList;
    }

    public void setShelters(ArrayList<Shelter> shelters) {
        Shelters = shelters;
    }

    public void setFilteredList(ArrayList<Shelter> filteredList) {
        FilteredList = filteredList;
    }

    public void setUserList(ArrayList<User> userList) {
        UserList = userList;
    }

    public boolean saveBinary(File file) {
        boolean success = true;
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            //Log.e("File Created", file.getPath());
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            Log.e("UserManagerFacade", "Error writing an entry from binary file",e);
            success = false;
        }
        return success;
    }

    public boolean loadBinary(File file) {
        boolean success = true;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            bs = (BinarySerialize) in.readObject();
            Auth.setUsers(bs.getUserList());
            ShelterList.setShelters(bs.getShelters());
            ShelterList.setFilteredList(bs.getFilteredList());
            in.close();
        } catch (IOException e) {
            Log.e("UserManagementFacade", "Error reading an entry from binary file",e);
            success = false;
        } catch (ClassNotFoundException e) {
            Log.e("UserManagementFacade", "Error casting a class from the binary file",e);
            success = false;
        }
        return success;
    }

}
