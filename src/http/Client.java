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
 * @version 2.0
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

    /** Si le thread est libre.*/
    private Boolean free = true;

    /** Temp de pause du thread.*/
    private final int tempPause = 100000;

    /** Créer le client et démarre le thread.*/
    public Client() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    /** Si le client est libre, il peut traiter une requête.
     * @param psocket le socket serveur*/
    public final void traiteRequete(final Socket psocket) {
        this.free = false; // le client va être occupé
        this.socket = psocket;
    }

    /** démarre le thread.
     */
    @Override
    public final void run() {
        String line;
        ArrayList<String> header = new ArrayList();
        String strContent;
        Request requete = null;

        while (true) {
            this.socket = null;
            Boolean attendre = true;
            //position d'attente. Seul l'interrupt pourra réveiller le thread
            while (attendre) {
                try {
                    this.free = true; //le thread est libre, il dort
                    Http.syslog.trace(this.thread.getName() + "a été endormi");
                    Thread.sleep(tempPause);
                } catch (InterruptedException ex) {
                    this.free = false; //le client devient occupé
                    break; // Sortie de la boucle infinie attendre
                }
            }
            Http.syslog.trace(this.thread.getName() + " a été réveillé");
            //le thread est réveillé
            try { //création des reader et writer
                InputStreamReader is;
                is = new InputStreamReader(this.socket.getInputStream());
                in = new BufferedReader(is);
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                String msg;
                msg = "imposible de créer le stream in/out " +  e.getMessage();
                Http.syslog.error(msg);
            }
            try { //lecture dans le socket
                while ((line = in.readLine())!= null && line.length() > 0 ) {
                    header.add(line);
                }
                requete = new Request(header);
                header = null;
                //lecture du content (en cas de post) :
                if (requete.besoinContent()) {
                    line = in.readLine();
                    strContent = (line);
                    requete.setContent(new Content());
                }
            } catch (IOException ex) {
                    String msg;
                    msg = "Err lecture socket" + ex.getMessage();
                    Http.syslog.error(msg);
            }

            //construction de la reponse (en fonction du header de la requete)
            Response response = new Response(requete.getHeader());
            String cible;
            cible = requete.getHeader().getCible();
            //envoi de la réponse dans le socket
            envoyer(response.genereResponse(cible), response.getStream());

            //il n'y a plus de traitements a exécuter => fermeture des streams
            try {
                in.close();
                out.close();
                this.socket.close();
                this.socket = null;
            } catch (IOException ex) {
                String msg;
                msg = "erreur de fermeture stream" + ex.getMessage();
                Http.syslog.fatal(msg);
            }
        }
    }


    /** Envoie un message sur le socket du client.
     * @param data texte a envoyer
     * @param stream le stream à envoyer
     */
    protected final void envoyer(final String[] data,
            final BufferedInputStream stream) {
        final int bufferSize = 1024;
        try {
            // ecriture du header
            out.writeBytes(data[0]);
            if ("OK".equals(data[1])) { // si content
                byte[] buffer;
                buffer = new byte[bufferSize];
                int cpt; // compteur

                while ((cpt = stream.read(buffer, 0, bufferSize)) != -1) {
                    out.write(buffer);
                }
                stream.close();
            } else {
                if (data[1] != null) {
                    Http.syslog.trace(data[1]);
                    out.writeBytes(data[1]);
                } else {
                out.writeBytes("<h1>404 Not Found</h1>");
                }
            }
            out.flush();
            Http.syslog.trace("envoyé : " + data);
        } catch (IOException ex) {
            String msg;
            msg = "erreur envoyer socket" + ex.getMessage();
            Http.syslog.fatal(msg);
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

    /** @return si le socket est libre.
     */
    public final Boolean getFree() {
        return this.free;
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
