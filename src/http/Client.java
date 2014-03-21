package http;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/** Représente un client et réagit au requêtes reçues par celui-ci.
 * @author Pourquier Pierre-Emmanuel
 * @version 2.1
 */
public class Client implements Runnable {
    /** Le thread.*/
    private final Thread thread;

    /** La socket liante au client.*/
    private Socket socket;

    /** Le stream d'écriture dans la socket.*/
    private DataOutputStream out = null;

    /** Le stream de lecture dans le socket.*/
    private BufferedReader in;

    /** Temp de pause du thread.*/
    private final int tempPause = 100000;

    /** server de ce client.*/
    private final Serveur server;

    /**
     * Créer le client et démarre le thread.
     * @param psrv le serveur de ce client
     */
    public Client(final Serveur psrv) {
        this.server = psrv;
        this.thread = new Thread(this);
        this.thread.start();
    }

    /** Si le client est libre, il peut traiter une requête.
     * Ouverture des flux d'entré et sortie
     * @param psocket le socket serveur*/
    public final void traiteRequete(final Socket psocket) {
        this.socket = psocket;
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(psocket.getInputStream());
        } catch (IOException ex) {
            Http.syslog.error("Err52 -  socket.inputstream " + ex.getMessage());
        }
        in = new BufferedReader(is);
        try {
            out = new DataOutputStream(psocket.getOutputStream());
        } catch (IOException ex) {
            Http.syslog.error("Err58 - socket.OutputStream + "
                    + ex.getMessage());
        }
    }

    /**
     * démarre le thread.
     */
    @Override
    public final void run() {
        String line;
        ArrayList<String> header;
        String strContent;
        Request requete = null;

        while (true) {
            Boolean attendre = true;
            //position d'attente. Seul l'interrupt pourra réveiller le thread
            while (attendre) {
                try {
                    //le thread est libre, retour dans la liste
                    this.server.tailThread(this);
                    Http.syslog.trace(this.thread.getName() + " sleep");
                    Thread.sleep(tempPause);
                } catch (InterruptedException ex) {
                    break; // Sortie de la boucle infinie attendre
                }
            }
            Http.syslog.trace(this.thread.getName() + " a été réveillé");
            try { //lecture dans le socket
                header = new ArrayList<>();
                while (in != null && (line = in.readLine()) != null) {
                    if (line.length() == 0) {
                        break;
                    }
                    header.add(line);
                    try {
                        requete = new Request(header);
                        //lecture du content (en cas de post) :
                        if (requete.besoinContent()) {
                            line = in.readLine();
                            strContent = (line);
                            requete.setContent(new Content());
                        }
                    } catch (Exception ex) {
                        Http.syslog.error("Err103 - " + ex.getMessage()
                                + requete.toString());
                    }
                }
            } catch (IOException ex) {
                    String msg;
                    msg = "Lecture socket : " + ex.getMessage();
                    Http.syslog.error("Err110 - " + msg);
            }

            if (requete != null) {
                Response response = new Response(requete.getHeader());
                String cible;
                cible = requete.getHeader().getCible();
                //envoi de la réponse dans le socket
                String[] data = response.genereResponse(cible);
                if (!data[0].isEmpty()) {
                    try {
                        envoyer(data, response.getStream());
                        Http.requestlog.info(this.socket.getInetAddress()
                                + " - "
                                + requete.toString()
                                + " STATUT : " + response.getStatut());
                    } catch (Exception ex) {
                        Http.syslog.error("Err127 - " + ex.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Ferme les stream in et out.
     * @throws Exception erreur de fermeture du stream
     */
    private void fermeStream() throws Exception {
        this.in.close();
        this.out.close();
    }
    /** Envoie un message sur le socket du client.
     * @param pData texte a envoyer
     * @param stream le stream à envoyer
     * @throws java.lang.Exception erreur
     */
    protected final void envoyer(final String[] pData,
            final BufferedInputStream stream) throws Exception {
        final int bufferSize = 2048;
        try {
            byte[] line1 = pData[0].getBytes();
            out.write(line1);
            if ("OK".equals(pData[1])) { // si content
                byte[] buffer;
                buffer = new byte[bufferSize];
                int cpt; // compteur

                while ((cpt = stream.read(buffer, 0, bufferSize)) != -1) {
                    out.write(buffer);
                }
                stream.close();
            } else {
                if (pData[1] != null) {
                    Http.syslog.trace(pData[1]);
                    out.write(pData[1].getBytes());
                } else {
                out.writeBytes("<h1>404 Not Found</h1>");
                }
            }
            out.flush();
            Http.syslog.trace("envoyé : " + pData);
        } catch (IOException ex) {
            String msg;
            //ex.printStackTrace();
            msg = ex.getMessage();
            Http.syslog.error("Err179 - " + msg);
            out.flush();
        }
    }

    /** Lit les données dans la socket.
     * @return le message reçu sur la socket
     * @throws IOException lecture socket impossible
     */
    protected final String recevoir() throws IOException {
            String message;
            message = in.readLine();
            return message;
    }

    /** @return the socket.
     */
    public final Socket getSocket() {
            return this.socket;
    }

    @Override
    public final String toString() {
            return this.socket.getInetAddress().toString();
    }
    /** @return le thread de ce client.
     */
    public final Thread getThread() {
        return thread;
    }

}
