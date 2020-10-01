package co.edu.escuelaing.sparksecureapp;

import static spark.Spark.*;

import spark.Request;
import spark.Response;

/**
 * 
 * Hello world!
 * 
 */
public class SparkSecureService {

    private static SparkSecureController secureController;

    public static void main(String[] args) {
        secureController = new SparkSecureController();
        port(getPort());
        staticFiles.location("/public");
        secure("keystores/ecikeystore.p12", "password", null, null);

        post("login", (req, res) -> secureController.login(req, res));
        post("logout", (req, res) -> secureController.logOut(req, res));
        get("hello", (req, res) -> "Hello Web Services");
        get("hello2", (req, res) -> getHelloFromOther(req, res));
        get("randomColor", (req, res) -> getRandomColorFromOther(req, res));

        before((req, res) -> doBefore(req, res));

    }
    
    private static Object getRandomColorFromOther(Request req, Response res) {
        String ans = secureController.getFromOther(req, res,"https://localhost:5001/randomColor");
        res.type("application/json");
        return ans;
    }

    private static String getHelloFromOther(Request req, Response res) {
        String ans = secureController.getFromOther(req, res,"https://localhost:5001/hello");
        return ans;
    }

    private static void doBefore(Request req, Response res) {
        boolean authenticated = req.session().attribute("username") != null;
        boolean isLogin = req.pathInfo().equals("/login") ;
        String url = req.url();
        url = url.replace(req.pathInfo(), "");
        if (!authenticated && !isLogin){
            // Spark.halt(401, "You are not welcome here");
            res.redirect(url + "/login.html");
        }
    }


    static int getPort() {
        if (System.getenv("PORT") != null) { 
            return Integer.parseInt(System.getenv("PORT")); 
        } return 5000; //returns default port if heroku-port isn't set (i.e. on localhost) 
    } 
}
