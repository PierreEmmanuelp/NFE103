package http;
import log.Log;
import org.apache.log4j.Logger;

/** 
 * Charge la configuration, démarre les log puis le serveur.
 * @version 1.1
 * @author Pourquier Pierre-Emmanuel
 */
public final class Http {
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

        //chargement de la configuration
        config = new Configuration();

        Log logn = new Log();
        requestlog = logn.getRequestLog();
        syslog = logn.getSyslog();

        syslog.info("Démarrage du serveur");
        //Log.ajouterEntree("Démarrage du serveur", LogLevel.SYSTEM);
        Dispatcher serveur = new Dispatcher();
        Console console;
        console = new Console(serveur);
        serveur.start();
    }

    /** @return la configuration actuelle.
     */
    public static Configuration getConfig() {
        return config;
    }

}
