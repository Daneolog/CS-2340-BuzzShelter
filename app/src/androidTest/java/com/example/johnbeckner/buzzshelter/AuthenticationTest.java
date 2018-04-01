package com.example.johnbeckner.buzzshelter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

/**
 * Created by daneolog on 3/31/18.
 */

@RunWith(AndroidJUnit4.class)
public class AuthenticationTest {
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void basicAuthenticationTest() {
        User sample = new User("sample", "/!!/", "password", UserType.GENERAL_USER);
        Auth.addUser(sample);
        Assert.assertEquals(Auth.authenticate("sample", "password"), sample);
        Assert.assertEquals(Auth.authenticate("bad", "bad"), null);
        Auth.removeUser("/!!/");
    }
}
