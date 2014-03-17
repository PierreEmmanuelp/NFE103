package http;

import java.io.IOException;

/**
 * @version 1.1
 * @author Pourquier Pierre-Emmanuel
 */
public class Http {
    static Log log;
    static Configuration config;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        debug.Trace.debug = false;//mode débug activé : sortie console utilisable
        
        //chargement de la configuration
        config = new Configuration();
        

        try {
            log = new Log();
        } catch (IOException e) {
            e.getMessage();
        }
        Log.ajouterEntree("Démarrage du serveur", LogLevel.SYSTEM);
        Serveur serveur = new Serveur();
        Console console=new Console(serveur);
        serveur.start();  
    }
    
}
