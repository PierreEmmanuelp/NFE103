package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;

/**
 * Serveur http qui attend les connexions.
 *
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
 */
public class Serveur {

    private final Object verrou;
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
     * file d'attente des threads.
     */
    private final LinkedList<Client> threads;
    /**
     * Contient les différent host gérés par ce serveur.
     */
    private static Hosts hosts;

    /**
     * Constructeur de la classe serveur.
     */
    public Serveur() {
        // clients = new ArrayList();
        threads = new LinkedList<>();
        verrou = new Object();
        // récupération de la configuration depuis le fichier XML
        this.port = http.Http.getConfig().getPORT();
        this.poolThread = http.Http.getConfig().getPoolThread();
        hosts = http.Http.getConfig().getHosts();

        try {
            //creation de la socket
            servSocket = new ServerSocket(this.port);
        } catch (IOException ex) {
            String msg;
            msg = "impossible de créer le socket " + ex.getMessage();
            Http.syslog.fatal(msg);
            System.exit(-1);
        }
        int i;
        //creation du pool de thread
        for (i = 0; i <= this.poolThread; i++) {
            Client client = new Client(this);
            threads.add(client);
        }
        Http.syslog.debug("pool de thread créé");
    }

    /**
     * Démarre le mode écoute du serveur.
     */
    public final void start() {
        try {
            Http.syslog.info("server online");
            while (true) { // attente en boucle d'une connexion
                Client client;
                // un client se connecte, on le renvoit sur un thread libre
                client = this.getThread();
                client.traiteRequete(servSocket.accept());
                client.getThread().interrupt();
                Http.syslog.debug(">>>Server>>>" + client.getThread().getName());
            }
        } catch (IOException e) {
            String msg;
            msg = "socket indisponible" + e.getMessage();
            Http.syslog.fatal(msg);
        }
    }

    /**
     * Arrête le serveur.
     */
    public final void stop() {
        try {
            this.servSocket.close();
            Http.syslog.info("extinction du serveur");
        } catch (IOException ex) {
            String msg;
            msg = "Impossible de stopper le serveur" + ex.getMessage();
            Http.syslog.error(msg);
        }
    }

    /**
     * Renvoit le premier thread disponible.
     *
     * @return le thread
     */
    private synchronized Client getThread() {

        Client client; //= null;
        synchronized (this.verrou) {
            client = this.threads.poll();
        }
       // try {    
            while (client == null) {    
                //   .sleep(1000);//sleep for 1000 ms
                synchronized (this.verrou){                    
                client = this.threads.poll();
                }
            }
      // } catch (InterruptedException e) {
      //         }
        
        return client;
    }

    /**
     * Renvoit le port d'écoute du serveur.
     *
     * @return le port.
     */
    public final int getPORT() {
        return this.port;
    }

    /**
     * Renvoit les hosts gérer par ce serveur.
     *
     * @return les hosts.
     */
    public static Hosts getHost() {
        return hosts;
    }

    /**
     * Renvoit le nombre de thread utilisés par ce serveur.
     *
     * @return le nombre de threads.
     */
    public final synchronized int getPoolThread() {
        return poolThread;
    }

    /**
     * Ajoute un thread libre à la fin de la queue.
     *
     * @param pclient le thread libre
     */
    public final synchronized void tailThread(final Client pclient) {
        this.threads.add(pclient);
    }
}