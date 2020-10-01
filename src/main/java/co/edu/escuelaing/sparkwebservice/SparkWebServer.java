package co.edu.escuelaing.sparkwebservice;

import static spark.Spark.*;


public class SparkWebServer {
    
    public static void main(String[] args) {
        
        port(getPort());
        secure("keystores/ecikeystore2.p12", "hola123", null, null);
        get("hello", (req, res) -> "Hello Web Services 2");

    }

    static int getPort() {
        if (System.getenv("PORT") != null) { 
            return Integer.parseInt(System.getenv("PORT")); 
        } return 5001; //returns default port if heroku-port isn't set (i.e. on localhost) 
    } 
}