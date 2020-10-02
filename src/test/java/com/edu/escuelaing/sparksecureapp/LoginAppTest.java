package com.edu.escuelaing.sparksecureapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import com.google.gson.JsonObject;
import co.edu.escuelaing.sparksecureapp.SparkSecureController;

/**
 * Unit test for simple App.
 */
public class LoginAppTest 
{
    private SparkSecureController secureController = new SparkSecureController();

    @Test
    public void deberiaConfirmarUsuario(){
        JsonObject json = new JsonObject();
        json.addProperty("username", "andres");
        json.addProperty("password", "1234567");
        String ans = secureController.login(json);
        assertEquals("Success", ans);
    }
    @Test
    public void noDeberiaConfirmarUsuario(){
        JsonObject json = new JsonObject();
        json.addProperty("username", "andres");
        json.addProperty("password", "53241as");
        String ans = secureController.login(json);
        assertEquals("Fail", ans);
    }

    @Test
    public void deberiaConfirmarUsuario2(){
        JsonObject json = new JsonObject();
        json.addProperty("username", "testUser");
        json.addProperty("password", "9876543");
        String ans = secureController.login(json);
        assertEquals("Success", ans);
    }

    @Test
    public void noDeberiaConfirmarUsuario2(){
        JsonObject json = new JsonObject();
        json.addProperty("username", "testUser");
        json.addProperty("password", "987654");
        String ans = secureController.login(json);
        assertEquals("Fail", ans);
    }

    
}
