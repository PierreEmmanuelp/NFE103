package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Serveur attend les requetes d'un port et les dispatche à un pool de worker.
 * @author Pourquier Pierre-Emmanuel
 * @version 1.0
 */
public class Dispatcher {
     /**
     * Port d'écoute du server.
     */
    private final int port;
    /**
     * Nombre de thread démarrés sur le serveur.
     */
    private final int poolThread;
    /**
     * Socket du serveur.
     */
    private ServerSocket servSocket;
    /**
     * Contient les différent host gérés par ce serveur.
     */
    private static Hosts hosts;
    /**
     * Pool de Thread du serveur.
     */
    private ExecutorService pool;
    /**
     * si le serveur est en ligne.
     */
    private Boolean online;
    /**
     * Constructeur.
     */
    public Dispatcher() {
        this.port = http.Http.getConfig().getPORT();
        this.poolThread = http.Http.getConfig().getPoolThread();
        hosts = http.Http.getConfig().getHosts();
        try {
            //creation de la socket
            servSocket = new ServerSocket(this.port);
            pool = Executors.newFixedThreadPool(this.poolThread);
        } catch (IOException ex) {
            String msg;
            msg = "impossible de créer le socket " + ex.getMessage();
            Http.syslog.fatal(msg);
            System.exit(-1);
        }
    }

    /**
     * démarre le thread.
     */
    public final void start() {
        Http.syslog.info("server online");
        online = true;
        while (online) {
            try {
                JobRequest job = new JobRequest(this, servSocket.accept());
                this.pool.execute(job);
            } catch (IOException ex) {
                Http.syslog.error(ex.getMessage());
            }
        }
    }

     /**
     * Arrête le serveur.
     */
    public final void stop() {
            online = false;
            this.pool.shutdown();
            Http.syslog.warn("extinction du serveur");
            System.exit(0);
    }

    /**
     * Obtient les hosts de ce serveur.
     * @return hosts les hotes
     */
    public static Hosts getHosts() {
        return hosts;
    }

    /**
     * Obtient le port d'écoute du sevreur.
     * @return le port
     */
    public final synchronized int getPort() {
        return port;
    }

     /**
     * Obtient le nombres de thread du pool.
     * @return le nombre de thread
     */
    public final synchronized int getPoolThread() {
        return poolThread;
    }

    /**
     * Obtient l'état du serveur.
     * @return true si en ligne
     */
    public Boolean isOnline() {
        return online;
    }
}
