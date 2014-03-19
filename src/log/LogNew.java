package log;

import java.io.IOException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Gère les fichier et messages de log.
 *
 * @author Pourquier Pierre-Emmanuel
 * @version 1.0
 */
public final class LogNew {

    /**
     * Log d'erreur system.
     */
    private final Logger syslog;

    /**
     * Log des requete.
     */
    private final Logger requestLog;

    /**
     * Level de log pour la console.
     */
    private final Level lvlconsole = Level.ALL;

    /**
     * Constructeur.
     */
    public LogNew() {
        this.syslog = Logger.getLogger("system");
        syslog.setLevel(Level.ALL);
        StringBuilder motifConsole = new StringBuilder();
        motifConsole.append("%d{HH:mm:ss} - [%p] - %m %n");

        StringBuilder motifFichier = new StringBuilder();
        motifFichier.append("%d{yyyy-MM-dd HH:mm:ss:SSS}"
                + " - [%p] - [%C] - %m %n");
        //paramétrage de ce qui sera visible sur la console
        ConsoleAppender stdout = new ConsoleAppender();
        stdout.setName("Console");
        stdout.setLayout(new PatternLayout(motifConsole.toString()));
        stdout.activateOptions();
        stdout.setThreshold(lvlconsole);
        syslog.addAppender(stdout);

        //paramétrage de ce qui sera visible dans le fichier de log
        try {
            PatternLayout layout = new PatternLayout(motifFichier.toString());
            String path = http.Http.getConfig().getPathLog() + "/SYSTEM.log";
            FileAppender fileout = new FileAppender(layout, path, true);
            fileout.setName("SYSTEM.LOG");
            fileout.activateOptions();
            fileout.setThreshold(Level.INFO);
            syslog.addAppender(fileout);
        } catch (IOException ex) {
            syslog.fatal("error log" + ex.getMessage());
        }

        requestLog = Logger.getLogger("request");
    }

    /**
     * Renvoit le log system.
     *
     * @return le log
     */
    public Logger getSyslog() {
        return syslog;
    }
}