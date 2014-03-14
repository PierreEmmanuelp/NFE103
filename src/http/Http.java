package http;

import java.io.IOException;

/**
 * @version 1.1
 * @author Pourquier Pierre-Emmanuel
 */
public class Http {
    static Log log;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            log = new Log();
        } catch (IOException e) {
            e.getMessage();
        }
        Log.ajouterEntree("Démarrage du serveur", LogLevel.SYSTEM);
        Serveur serveur = new Serveur();
        serveur.start();
            
    }
    
}
