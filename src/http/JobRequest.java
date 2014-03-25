package http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Job qui lie la requete http et génère la réponse adéquate.
 * @author Pourquier Pierre-Emmanuel
 * @version 1.0
 */
public class JobRequest implements Runnable {
    /**
     * La socket liante au client.
     */
    private final Socket socket;

    /**
     * Le stream d'écriture dans la socket.
     */
    private DataOutputStream out = null;

    /**
     * Le stream de lecture dans le socket.
     */
    private BufferedReader in;

    /**
     * Temp de pause du thread.
     */
    private final int tempPause = 100000;

    /** server de ce client.*/
    private final Dispatcher server;

    /**
     * Créer le client et démarre le thread.
     * @param psrv le serveur de ce client
     * @param psocket le socket server
     */
    public JobRequest(final Dispatcher psrv, final Socket psocket) {
        this.server = psrv;
        this.socket = psocket;
        try {
            InputStreamReader is;
            is = new InputStreamReader(this.socket.getInputStream());
            in = new BufferedReader(is);
        } catch (IOException ex) {
            Http.syslog.error("Err59 -  socket.inputstream " + ex.getMessage());
        }
        try {
            out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException ex) {
            Http.syslog.error("Err64 - socket.OutputStream + "
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

        Http.syslog.trace(Thread.currentThread().getName() + " réveillé");
            try { //lecture dans le socket
                header = new ArrayList<>();
                Http.syslog.debug(">>>in>>>" + Thread.currentThread().getName()
                        + socket.toString());
                while (!in.ready()) {
                    in = new BufferedReader(new InputStreamReader(
                            this.socket.getInputStream()));
                }
                if (!in.ready()) {
                    Http.syslog.debug("Not ready");
                } else {
                    Http.syslog.debug("Ready "
                            + Thread.currentThread().getName());
                }

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
                            in.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Http.syslog.error("Err103 - " + ex.getMessage()
                                + requete.toString());
                    }
                }
            } catch (IOException ex) {
                    String msg;
                    msg = "Lecture socket : " + ex.getMessage() + requete.toString();
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
        Http.syslog.debug(">>>retour OK>>>" + Thread.currentThread().getName());
        try {
            fermeStream();
        } catch (Exception ex) {
            Http.syslog.error(ex.getMessage());
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
    protected final synchronized void envoyer(final String[] pData,
            final BufferedInputStream stream) throws Exception {
        try {
            byte[] line1 = pData[0].getBytes();
            if ("OK".equals(pData[1])) { // si content
                final int bufferSize = 2048;
                byte[] buffer;
                buffer = new byte[bufferSize];
                int cpt = 0; // compteur
                Http.syslog.debug(">>out>>" + Thread.currentThread().getName()
                        + socket.toString());
                out.write(line1);
                while ((cpt = stream.read(buffer, 0, bufferSize)) != -1) {
                    out.write(buffer, 0, cpt);
                }
                 if (stream.available() < -1) {
                     stream.reset();
                 }
                 stream.close();
                 out.flush();
            } else {
                if (pData[1] != null) {
                    out.write(line1);
                    out.flush();
                    Http.syslog.trace(pData[1]);
                    out.writeBytes(pData[1]);
                    out.flush();
                } else {
                    out.write(line1);
                    out.writeBytes("<h1>404 Not Found</h1>");
                }
            }
              out.flush();
             // in.close();
              out.close();
              Http.syslog.debug(">>>Out%%%%>>>" 
                      + Thread.currentThread().getName() + socket.toString());
        //    out.close();
            Http.syslog.trace("envoyé : " + pData);
        } catch (IOException ex) {
            String msg;
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
            in.close();
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
}
