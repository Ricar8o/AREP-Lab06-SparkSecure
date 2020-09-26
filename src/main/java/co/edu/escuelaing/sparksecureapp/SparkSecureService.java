package co.edu.escuelaing.sparksecureapp;

import static spark.Spark.*;

/**
 * 
 * Hello world!
 * 
 */
public class SparkSecureService 
{
    public static void main( String[] args )
    {
        // keytool -genkeypair -alias ecikeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore ecikeystore.p12 -validity 3650
        // keytool -export -keystore ecikeystore.p12 -alias ecikeypair -file ecicert.cer
        // keytool -import -file ./ecicert.cer -alias firstCA -keystore myTrustStore
        port(getPort());
        secure("keystores/ecikeystore.p12", "password",null,null);
        get("hello", (req,res) -> "Hello Web Services");
    }

    static int getPort() { 
        if (System.getenv("PORT") != null) { 
            return Integer.parseInt(System.getenv("PORT")); 
        } return 5000; //returns default port if heroku-port isn't set (i.e. on localhost) 
    } 
}
