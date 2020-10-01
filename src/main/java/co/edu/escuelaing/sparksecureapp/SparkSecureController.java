package co.edu.escuelaing.sparksecureapp;

import spark.Request;
import spark.Response;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * @author Ricar8o
 * @version 1.0
 */
public class SparkSecureController {

    /**
     * Constructor
     */
    public SparkSecureController(){

    }

    public String login(Request req, Response res){
        req.session(true); // create and return session
        JsonObject json = (JsonObject) JsonParser.parseString(req.body());
        req.session().attribute("username", json.get("username"));
        return "Success!";
    }

    public String logOut(Request req, Response res){
        req.session().invalidate();;
        return "Log out";
    }
}
