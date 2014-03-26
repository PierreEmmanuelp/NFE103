package log;

import java.io.IOException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Gère les fichiers et messages de log.
 * @author Pourquier Pierre-Emmanuel
 * @version 1.0
 */
public final class Log {

    /**
     * Log d'erreur system.
     */
    private final Logger syslog;

    /**
     * Log des requêtes.
     */
    private final Logger requestLog;

    /**
     * Level de log pour la console system.
     */
    private final Level lvlconsole;

    /**
     * Level de log pour la console Request.
     */
    private final Level lvlRequest;

    /**
     * Constructeur.
     */
    public Log() {
        this.lvlconsole = Level.ALL;
        this.lvlRequest = Level.ERROR;
        this.syslog = Logger.getLogger("system");

        //Motif (format) des log dans la sortie console
        StringBuilder motifConsole = new StringBuilder();
        motifConsole.append("%d{HH:mm:ss} - [%p] - %m %n");

        //Motif (format) des log dans la sortie fichier
        StringBuilder motifFichier = new StringBuilder();
        motifFichier.append("%d{yyyy-MM-dd HH:mm:ss:SSS}");
        motifFichier.append(" - [%p] - [%C] - %m %n");

        //paramétrage de ce qui sera visible sur la console
        ConsoleAppender sysOut = new ConsoleAppender();
        sysOut.setName("SysConsole");
        sysOut.setLayout(new PatternLayout(motifConsole.toString()));
        sysOut.activateOptions();
        sysOut.setThreshold(lvlconsole);
        syslog.addAppender(sysOut);

        //paramétrage de ce qui sera visible dans le fichier de log
        PatternLayout layout = new PatternLayout(motifFichier.toString());
        String syspath = http.Http.getConfig().getPathLog() + "/system.log";
        try {
            FileAppender sysfileout = new FileAppender(layout, syspath, true);
            sysfileout.setName("system.log");
            sysfileout.activateOptions();
            sysfileout.setThreshold(Level.INFO);
            syslog.addAppender(sysfileout);
        } catch (IOException ex) {
            syslog.fatal("error log" + ex.getMessage());
        }

        //Paramétrage de la sortie dans la console des evènements requêtes
        requestLog = Logger.getLogger("request");
        String reqpath = http.Http.getConfig().getPathLog() + "/request.log";
        ConsoleAppender reqOut = new ConsoleAppender();
        reqOut.setName("ReqConsole");
        reqOut.setLayout(new PatternLayout(motifConsole.toString()));
        reqOut.activateOptions();
        //niveau nécessaire pour affichage console :
        reqOut.setThreshold(lvlRequest);
        requestLog.addAppender(reqOut); //ajout de la console au logger
        try {
            FileAppender reqfileout = new FileAppender(layout, reqpath, true);
            reqfileout.setName("request.log");
            reqfileout.activateOptions();
            //level nécessaire pour log dans fichier :
            reqfileout.setThreshold(Level.INFO);
            requestLog.addAppender(reqfileout); //ajout du fichier au logger
        } catch (IOException e) {
            requestLog.error("error request log " + e.getMessage());
        }
    }

    /**
     * Renvoit le log system.
     * @return le log system
     */
    public Logger getSyslog() {
        return syslog;
    }

    /**
     * Obtient le log de requete.
     * @return le log requete
     */
    public Logger getRequestLog() {
        return requestLog;
    }

    /**
     * Obtient le niveau actuel de log dans la console.
     * @return le lvl de log
     */
    public Level getLvlconsole() {
        return lvlconsole;
    }
}