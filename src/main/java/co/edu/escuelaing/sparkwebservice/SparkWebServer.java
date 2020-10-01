package co.edu.escuelaing.sparkwebservice;

import static spark.Spark.*;
import java.util.Random;

import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;
/**
 * @author Ricar8o
 * @version 1.0
 *  java -cp target/classes/:target/dependency/* co.edu.escuelaing.sparkwebservice.SparkWebServer
 */
public class SparkWebServer {

    public static void main(String[] args) {

        port(getPort());
        secure("keystores/ecikeystoreaws.p12", "hola123", null, null);
        get("hello", (req, res) -> "Hello Web Services 2");
        get("randomColor", (req, res) -> getRandomColor(req, res));

    }

    private static JsonObject getRandomColor(Request req, Response res) {
        Random rand = new Random();
        JsonObject rgb = new JsonObject();
        rgb.addProperty("R", rand.nextInt(256));
        rgb.addProperty("G", rand.nextInt(256));
        rgb.addProperty("B", rand.nextInt(256));
        res.type("application/json");
        return rgb;
    }

    static int getPort() {
        if (System.getenv("PORT") != null) { 
            return Integer.parseInt(System.getenv("PORT")); 
        } return 5001; //returns default port if heroku-port isn't set (i.e. on localhost) 
    } 
}