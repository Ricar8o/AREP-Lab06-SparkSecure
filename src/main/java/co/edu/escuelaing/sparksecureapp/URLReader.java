package co.edu.escuelaing.sparksecureapp;

import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * 
 * @author Ricar8o
 */
public class URLReader {
    public static void main(String[] args) {
        try {
            // Create a file and a password representation
            File trustStoreFile = new File("keystores/myTrustStore");
            char[] trustStorePassword = "123456".toCharArray();

            // Load the trust store, the default type is "pkcs12", the alternative is "jks"
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

            // Get the singleton instance of the TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            // Itit the TrustManagerFactory using the truststore object
            tmf.init(trustStore);


            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext); 

            readURL("https://localhost:5000/hello");
            // readURL("https://ldbn.is.escuelaing.edu.co");
            // readURL("https://i.ytimg.com/vi/8PvyIAEfPgE/maxresdefault.jpg");
        } catch (KeyStoreException e) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, e);
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, e);
        } catch (CertificateException e) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, e);
        } catch (FileNotFoundException e) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, e);
        } catch (KeyManagementException e) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private static void readURL(String site) {

        try {
            URL siteURL = new URL(site);
            try {
                // Crea el objeto que URLConnection
                URLConnection urlConnection = siteURL.openConnection();
                // Obtiene los campos del encabezado y los almacena en un estructura Map
                Map<String, List<String>> headers = urlConnection.getHeaderFields();
                // Obtiene una vista del mapa como conjunto de pares <K,V>
                // para poder navegarlo
                Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
                // Recorre la lista de campos e imprime los valores
                for (Map.Entry<String, List<String>> entry : entrySet) {
                    String headerName = entry.getKey();
                    // Si el nombre es nulo, significa que es la linea de estado
                    if (headerName != null) {
                        System.out.print(headerName + ":");
                    }
                    List<String> headerValues = entry.getValue();
                    for (String value : headerValues) {
                        System.out.print(value);
                    }
                    System.out.println("");
                    // System.out.println("");
                }
                System.out.println("\n---------------Message Body---------------");
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    System.out.println("Recibí: " + inputLine);
                    if (!reader.ready()) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
    }
}
