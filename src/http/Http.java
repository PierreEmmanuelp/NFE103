package http;
import java.io.IOException;
import log.Log;
import log.LogNew;
import org.apache.log4j.Logger;
/** Charge la configuration, démarre les log puis le serveur.
 * @version 1.1
 * @author Pourquier Pierre-Emmanuel
 */
public final class Http {
    /**représente les fichiers de log.*/
    private static Log log;

    /** fichier de log system.*/
    public static Logger syslog;

    /** fichier de log system.*/
    public static Logger requestlog;

    /** représente la configuration.*/
    private static Configuration config;

    /** Contructeur par défaut private pour éviter les erreurs.
     */
    private Http() {
        throw new AssertionError("Instanciation d'une classe utilitaire");
    }

    /** Fonction de démarrage de l'application.
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        debug.Trace.setDebug(true); // true : sortie console utilisable

        //chargement de la configuration
        config = new Configuration();

        try {
            LogNew logn = new LogNew();
            requestlog = logn.getRequestLog();
            syslog = logn.getSyslog();
            log = new Log();
        } catch (IOException e) {
            e.getMessage();
        }
        syslog.info("Démarrage du serveur");
        //Log.ajouterEntree("Démarrage du serveur", LogLevel.SYSTEM);
        Serveur serveur = new Serveur();
        Console console;
        console = new Console(serveur);
        serveur.start();
    }
    /** @return les fichiers de log.
     */
    public static Log getLog() {
        return log;
    }

    /** @return la configuration actuelle.
     */
    public static Configuration getConfig() {
        return config;
    }

}
