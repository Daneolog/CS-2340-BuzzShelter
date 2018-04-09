package com.example.johnbeckner.buzzshelter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

/**
 * Created by daneolog on 3/31/18.
 */

@RunWith(AndroidJUnit4.class)
public class AuthenticationTest {
    User sample;

    @Before
    public void setup() {
        sample = new User("testName", "testID", "testPassword", UserType.GENERAL_USER);
        Auth.addUser(sample);
    }

    @Test
    public void testExistsCorrectPassword() {
        Assert.assertEquals(Auth.authenticate("testID", "testPassword"), sample);
    }

    @Test
    public void testExistsWrongPassword() {
        Assert.assertEquals(Auth.authenticate("testID", "wrongPassword"), null);
    }

    @Test
    public void testNotExists() {
        Assert.assertEquals(Auth.authenticate("wrongID", "wrongPassword"), null);
    }

    @After
    public void teardown() {
        Auth.removeUser("testID");
    }
}
