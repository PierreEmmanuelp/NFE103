package http;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import debug.Trace;
import log.Log;
import log.LogLevel;
/**.
 * représente le processus serveur qui attend les connexion sur le port PORT
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
 *
 */
public class Serveur {
        /**.
         * port du server
         */
	private final int port;

        /**.
         * nombre de thread démarrer sur le serveur
         */
        private final int poolThread;
        /**.
         * socket du serveur
         */
        private ServerSocket servSocket;

        /**.
         * tableau de clients (thread)
         */
	private final ArrayList<Client> clients;

        /**.
         * contient les différent host gérés par ce serveur
         */
        private static Hosts hosts;

        /**.
         * représente le dernier thread utilisé
         */
        private int lastIndex;

        /**.
         * Constructeur de la classe serveur
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
                Log.ajouterEntree("impossible de créer le socket" + ex.getMessage(), LogLevel.ERROR);
                Trace.trace("Erreur interne : socket indisponible");
            }
            int i;
            //creation du pool de thread
            for (i = 0; i <= this.poolThread; i++) {
                Client client = new Client();
                clients.add(client);
            }
            Log.ajouterEntree("pool de thread créé", LogLevel.SYSTEM);
	}

	/**.
	 * démarre le mode écoute du serveur
	 */
	public final void start() {
            try {
                Log.ajouterEntree("serveur en ligne", LogLevel.SYSTEM);
                Trace.trace("http server online");
                while (true) { // attente en boucle d'une connexion
                    Client client;
                    // un client se connecte, on le renvoit sur un thread libre
                    client = this.getFreeClient();
                    client.traiteRequete(servSocket.accept());
                    client.getThread().interrupt();
                }
            } catch (IOException e) {
                Trace.trace("Serveur indisponible : socket libre? ");
                Log.ajouterEntree("socket indisponible"+e.getMessage(),LogLevel.SYSTEM);
            }
    }

    /**.
    * arrête le serveur
    */
    public final void stop() {
        try {
            this.servSocket.close();
            Log.ajouterEntree("extinction du serveur", LogLevel.SYSTEM);
        } catch (IOException ex) {
            Log.ajouterEntree("Impossible de stopper le serveur", LogLevel.SYSTEM);
        }
    }

    /**.
    *  return le premier thread disponible
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

    /**.
    * @return le port utilisé par le serveur
    */
    public final int getPORT() {
        return this.port;
    }

    /**.
     * accesseurs des hosts géré par le serveur
     * @return hosts les hosts
     */
    public static Hosts getHost() {
        return hosts;
    }

    /**.
     * accesseur du nombre de thread lancés sur le serveur.
     * @return  le nombre de threads utilisés
     */
    public final int getPoolThread() {
        return poolThread;
    }
}