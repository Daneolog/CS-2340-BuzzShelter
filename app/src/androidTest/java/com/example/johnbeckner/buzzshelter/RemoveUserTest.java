package com.example.johnbeckner.buzzshelter;

import com.example.johnbeckner.buzzshelter.Auth;
import com.example.johnbeckner.buzzshelter.User;
import com.example.johnbeckner.buzzshelter.UserType;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Wendell-Surface on 4/9/2018.
 */

public class RemoveUserTest {
    private User test;


    @Before
    public void setUp() {
        test = new User("john", "abcd", "1234", UserType.GENERAL_USER);


    }

    @Test
    public void testRemoveUser() {
        assertFalse(Auth.removeUser(null));
        Auth.addUser(test);

        assertTrue(Auth.removeUser(test.getId()));
        assertFalse(Auth.removeUser("8901"));


    }



}
