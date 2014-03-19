package http;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
/** Serveur http qui attend les connexions.
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
 */
public class Serveur {
        /** Port d'écoute du server.*/
	private final int port;

        /** Nombre de thread démarrés sur le serveur.*/
        private final int poolThread;

        /** Socket du serveur.*/
        private ServerSocket servSocket;

        /** Tableau de clients (thread).*/
	private final ArrayList<Client> clients;

        /** Contient les différent host gérés par ce serveur.*/
        private static Hosts hosts;

        /** Dernier thread utilisé*/
        private int lastIndex;

        /** onstructeur de la classe serveur.
         */
	public Serveur() {
            this.lastIndex = 0;
            clients = new ArrayList();

            // récupération de la configuration depuis le fichier XML
            this.port = http.Http.getConfig().getPORT();
            this.poolThread = http.Http.getConfig().getPoolThread();
            hosts = http.Http.getConfig().getHosts();

            try {
                //creation de la socket
                servSocket = new ServerSocket(this.port);
            } catch (IOException ex) {
                String msg;
                msg = "impossible de créer le socket" + ex.getMessage();
                Http.syslog.fatal(msg);
            }
            int i;
            //creation du pool de thread
            for (i = 0; i <= this.poolThread; i++) {
                Client client = new Client();
                clients.add(client);
            }
            Http.syslog.debug("pool de thread créé");
	}

	/** Démarre le mode écoute du serveur.
	 */
	public final void start() {
            try {
                Http.syslog.info("server online");
                while (true) { // attente en boucle d'une connexion
                    Client client;
                    // un client se connecte, on le renvoit sur un thread libre
                    client = this.getFreeClient();
                    client.traiteRequete(servSocket.accept());
                    client.getThread().interrupt();
                }
            } catch (IOException e) {
                String msg;
                msg = "socket indisponible" + e.getMessage();
                Http.syslog.fatal(msg);
            }
    }

    /** Arrête le serveur.
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

    /** Renvoit le premier thread disponible.
    * @return le thread
    */
    private Client getFreeClient() {
        int i, index;
        index = -1;
        boolean trouve = false;
        i = this.lastIndex + 1;
        while (!trouve && i < this.clients.size()) {
            if (clients.get(i).getFree()) {
                trouve = true;
                index = i;
                    this.lastIndex = i;
            }
            i = i + 1;
            if (i >= this.clients.size()) {
                this.lastIndex = 0;
                i = 0;
            }
        }
        return clients.get(index);
    }

    /** Renvoit le port d'écoute du serveur.
    * @return le port 
    */
    public final int getPORT() {
        return this.port;
    }

    /** Renvoit les hosts gérer par ce serveur.
     * @return les hosts.
     */
    public static Hosts getHost() {
        return hosts;
    }

    /** Renvoit le nombre de thread utilisés par ce serveur.
     * @return  le nombre de threads.
     */
    public final int getPoolThread() {
        return poolThread;
    }
}