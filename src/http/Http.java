package http;
import java.io.IOException;
import log.Log;
import log.LogLevel;
/**
 * @version 1.1
 * @author Pourquier Pierre-Emmanuel
 */
public class Http {
    /**.
     * log : représente les fichiers de log
     */
    private static Log log;

    /**.
     * config : représente la configuration
     */
    private static Configuration config;

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        debug.Trace.setDebug(true); // true : sortie console utilisable

        //chargement de la configuration
        config = new Configuration();

        try {
            log = new Log();
        } catch (IOException e) {
            e.getMessage();
        }
        Log.ajouterEntree("Démarrage du serveur", LogLevel.SYSTEM);
        Serveur serveur = new Serveur();
        Console console;
        console = new Console(serveur);
        serveur.start();
    }
    /**.
     * getter des fichiers de log
     * @return les fichiers de log
     */
    public static Log getLog() {
        return log;
    }

    /**.
     * Getter de la configuration
     * @return la configuration actuelle
     */
    public static Configuration getConfig() {
        return config;
    }

}
