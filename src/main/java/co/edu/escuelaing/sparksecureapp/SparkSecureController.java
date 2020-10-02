package co.edu.escuelaing.sparksecureapp;

import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * @author Ricar8o
 * @version 1.0
 */
public class SparkSecureController {

    SecureURLReader secureURLReader;
    Map<String, String> users;

    /**
     * Constructor
     */
    public SparkSecureController(){
        secureURLReader = new SecureURLReader();
        users = new HashMap<>();
        users.put("andres", "20eabe5d64b0e216796e834f52d61fd0b70332fc"); // 1234567
        users.put("testUser", "3c776dacfd82b327a679bdf2339cc9477299ebb6"); // 9876543

    }
    /**
     * Servicio de login.
     * @param req Spark Request.
     * @param res Spark Response.
     * @return mensaje si hizo login.
     */
    public String login(Request req, Response res){
        req.session(true); // create and return session
        JsonObject json = (JsonObject) JsonParser.parseString(req.body());
        String user = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        String hashedPassword = Hash.sha1(password);

        if(users.get(user).equals(hashedPassword)){
            req.session().attribute("username", user);
            return "Success";
        }else{
            return "Fail";
        }

        
    }
    /**
     * Servicio de logout.
     * @param req Spark Request.
     * @param res Spark Response.
     * @return mensaje si hizo logout.
     */
    public String logOut(Request req, Response res){
        req.session().invalidate();;
        return "Logout";
    }

    /**
     * Obtiene el cuerpo de algun contenido http.
     * @param req
     * @param res
     * @param url
     * @return
     */
    public String getFromOther(Request req, Response res, String url){
        return secureURLReader.readURL(url);
    }
}
