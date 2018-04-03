package com.example.johnbeckner.buzzshelter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

/**
 * Created by John Beckner on 4/3/18.
 */

@RunWith(AndroidJUnit4.class)
public class FilterListTest {

    Shelter s1 = new Shelter("Test1", 1, "None", 0.0, 0.0, "123 GaTech Drive", "123456789", "Go Jackets!");
    Shelter s2 = new Shelter("Test2", 1, "Anyone", 0.0, 0.0, "123 GaTech Drive", "123456789", "Go Jackets!");
    Shelter s3 = new Shelter("Test3", 1, "0-100000", 0.0, 0.0, "123 GaTech Drive", "123456789", "Go Jackets!");

    @Before
    public void setUp() {
        ShelterList.addShelter(s1);
        ShelterList.addShelter(s2);
        ShelterList.addShelter(s3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void FilterListTestException1() {
        ShelterList.filterShelters(null, "", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void FilterListTestException2() {
        ShelterList.filterShelters(null, "", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void FilterListTestException3() {
        ShelterList.filterShelters(null, "", "");
    }

    @Test
    public void testFilter() {
        ShelterList.filterShelters("Test1", "", "");
        assertEquals(ShelterList.getFilteredList().get(0), s1);

        ShelterList.filterShelters("", "None", "");
        assertEquals(ShelterList.getFilteredList().get(0), s1);

        ShelterList.filterShelters("", "", "0-100000");
        assertEquals(ShelterList.getFilteredList().get(0), s3);
    }
}
