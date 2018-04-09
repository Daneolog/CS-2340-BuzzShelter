package com.example.johnbeckner.buzzshelter;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;


/**
 * Created by Sam on 4/9/18.
 */
@RunWith(AndroidJUnit4.class)
public class FindShelterTest {
    Shelter shel1 = new Shelter("Alpha", 1, "None", 0.0, 0.0, "711 Techwood Dr", "123456789", "tester1");
    Shelter shel2 = new Shelter("Bravo", 5, "Anyone", 0.0, 0.0, "848 Spring ST NW", "987654321", "tester2");
    Shelter shel3 = new Shelter("Charlie", 1, "0-100", 0.0, 0.0, "CULC GATECH", "1234554321", "tester3");


    @Before
    public void initialization() {
        ShelterList.addShelter(shel1);
        ShelterList.addShelter(shel2);
        ShelterList.addShelter(shel3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findShelterException1() { ShelterList.findShelter(null);}

    @Test
    public void testFindShelter() {
        Shelter returnedShel1 = ShelterList.findShelter(shel1);
        assertEquals(returnedShel1, shel1);
        //
        Shelter returnedShel2 = ShelterList.findShelter(shel2);
        assertEquals(returnedShel2, shel2);
        //
        Shelter returnedShel3 = ShelterList.findShelter(shel3);
        assertEquals(returnedShel3, shel3);
    }
}
