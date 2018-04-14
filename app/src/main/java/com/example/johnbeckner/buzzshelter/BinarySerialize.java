package com.example.johnbeckner.buzzshelter;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by John Beckner on 3/13/2018.
 */

class BinarySerialize implements Serializable {

    private ArrayList<Shelter> Shelters;
    private ArrayList<Shelter> FilteredList;
    private ArrayList<User> UserList;
    private BinarySerialize bs;

    /**
     * Creates new BinarySerialize object
     */
    public BinarySerialize() {
        Shelters = new ArrayList<>();
        FilteredList = new ArrayList<>();
        UserList = new ArrayList<>();
    }

    /**
     * Creates new BinarySerialize object
     * @param Shelters list of shelters
     * @param FilteredList filtered list of shelters
     * @param UserList list of users
     */
    public BinarySerialize(ArrayList<Shelter> Shelters, ArrayList<Shelter> FilteredList,
                           ArrayList<User> UserList) {
        this.Shelters = new ArrayList<>(Shelters);
        this.FilteredList = new ArrayList<>(FilteredList);
        this.UserList = new ArrayList<>(UserList);
    }

    /**
     * Getter for shelters list
     * @return shelters list
     */
    private ArrayList<Shelter> getShelters() {
        return Shelters;
    }

    /**
     * Getter for filtered shelters list
     * @return filtered shelters list
     */
    private ArrayList<Shelter> getFilteredList() {
        return FilteredList;
    }

    /**
     * Getter for users list
     * @return users list
     */
    private ArrayList<User> getUserList() {
        return UserList;
    }

    /**
     * Setter for shelters list
     * @param shelters ArrayList to set shelters to
     */
    public void setShelters(ArrayList<Shelter> shelters) {
        Shelters = new ArrayList<>(shelters);
    }

    /**
     * Setter for filtered shelters list
     * @param filteredList ArrayList to set filtered shelters to
     */
    public void setFilteredList(ArrayList<Shelter> filteredList) {
        FilteredList = filteredList;
    }

    /**
     * Setter for users list
     * @param userList ArrayList to set users to
     */
    public void setUserList(ArrayList<User> userList) {
        UserList = userList;
    }

    /**
     * Saves data to binary file
     * @param file binary file to save data to
     * @return success of binary file serialization
     */
    public void saveBinary(File file) {
        boolean success = true;
        try {
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(file));
            //Log.e("File Created", file.getPath());
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            Log.e("UserManagerFacade", "Error writing an entry from binary file",e);
            success = false;
        }
    }

    /**
     * Loads data from binary file
     * @param file binary file to load data from
     * @return success of binary file load
     */
    public void loadBinary(File file) {
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
    }

}
