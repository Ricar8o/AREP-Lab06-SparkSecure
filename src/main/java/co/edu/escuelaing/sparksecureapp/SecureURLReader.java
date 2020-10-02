package co.edu.escuelaing.sparksecureapp;

import java.io.*;
import java.net.*;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * 
 * @author Ricar8o
 */
public class SecureURLReader {

    public SecureURLReader() {
        loadTrustStore();
    }

    private void loadTrustStore() {
        try {
            // Create a file and a password representation
            File trustStoreFile = new File("keystores/myTrustStore"); // keystore path
            char[] trustStorePassword = "123456".toCharArray(); // password

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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lee un url
     * 
     * @param site
     * @return
     */
    public String readURL(String site) {

        try {
            URL siteURL = new URL(site);
            try {
                URLConnection urlConnection = siteURL.openConnection();
                // Map<String, List<String>> headers = urlConnection.getHeaderFields();
                // Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                String consulta = "";
                while ((inputLine = reader.readLine()) != null) {
                    consulta += inputLine;
                    if (!reader.ready()) {
                        break;
                    }
                }
                reader.close();
                // System.out.println(consulta);
                return consulta;

            } catch (IOException e) {
                return "Consulta fallida";
            }
        } catch (Exception e) {
            return "Consulta fallida";
        }
    }

}
