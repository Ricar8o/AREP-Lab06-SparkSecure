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
        // secure("keystores/ecikeystore.p12", "password", null, null);
        secure("keystores/ecikeystoreaws2.p12", "yesterday", null, null);
        post("login", (req, res) -> secureController.login(req, res));
        post("logout", (req, res) -> secureController.logOut(req, res));
        get("hello", (req, res) -> "Hello Secure Services");
        get("hello2", (req, res) -> getHelloFromOther(req, res));
        get("randomColor", (req, res) -> getRandomColorFromOther(req, res));

        before((req, res) -> doBefore(req, res));

    }
    /**
     *  Obtiene un color RGB desde otro servicio.
     * @param req Spark Request.
     * @param res Spark Response.
     * @return Color RGB en formato JSON
     */
    private static String getRandomColorFromOther(Request req, Response res) {
        String ans = secureController.getFromOther(req, res,"https://ec2-52-91-59-106.compute-1.amazonaws.com:5001/randomColor");
        res.type("application/json");
        return ans;
    }

    /**
     * Obtiene un saludo desde otro servicio Web.
     * @param req Spark Request.
     * @param res Spark Response.
     * @return Un saludo desde otro servicio web.
     */
    private static String getHelloFromOther(Request req, Response res) {
        String ans = secureController.getFromOther(req, res,"https://ec2-52-91-59-106.compute-1.amazonaws.com:5001/hello");
        return ans;
    }

    /**
     * Antes de buscar cualquier ruta que no sea publica verifica si inicio sesion y es un usuario permitido. 
     * @param req Spark Request.
     * @param res Spark Response.
     */
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
        } return 5002; //returns default port if heroku-port isn't set (i.e. on localhost) 
    } 
}
