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

    /**
     * server de ce client.
     */
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
            Http.syslog.error("Err55 -  socket.inputstream " + ex.getMessage());
        }
        try {
            out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException ex) {
            Http.syslog.error("Err60 - socket.OutputStream + "
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
        Request requete = null;

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
            while (in != null && (line = in.readLine()) != null && line.length() > 0) {
                if (line.length() == 0) {
                    Http.syslog.warn("ERR91 - in the break");
                    break;
                }
                header.add(line);
            }
            try {
                requete = new Request(header);
                //lecture du content (en cas de post) :
                if (requete.besoinContent()) {
                    Object strlength = requete.getHeader().getParametres().get("Content-Length");
                    int length = Integer.parseInt(strlength.toString().substring(2));
                    StringBuilder contenu = new StringBuilder();
                    char[] buffer = new char[1];
                    int i;
                    for (i = 0; i < length; i++) {
                        in.read(buffer, 0, 1);
                        contenu.append(buffer);
                    }
                    Content content = new Content();
                    content.setContenu(contenu.toString());
                    requete.setContent(content);
                }
            } catch (Exception ex) {
                Http.syslog.error("Err114 - " + ex.getMessage()
                        + requete.toString());
            }
        } catch (IOException ex) {
                String msg;
                msg = "Lecture socket : " + ex.getMessage()
                        + requete.toString();
                Http.syslog.error("Err121 - " + msg);
        }

           if (requete != null) {

        Response response;
        response = new Response(requete.getProtorequest(), requete.getHeader());
                String cible;
                cible = requete.getHeader().getCible();
                //envoi de la réponse dans le socket
                String data = response.genereResponse(cible);
                if (!data.isEmpty()) {
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
            Http.syslog.error("Err145 - close stream - " + ex.getMessage());
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
    protected final synchronized void envoyer(final String pData,
            final BufferedInputStream stream) throws Exception {
        try {
         byte[] line1 = pData.getBytes();

             final int bufferSize = 2048;
                byte[] buffer;
                buffer = new byte[bufferSize];
                int cpt; // compteur

                out.write(line1);
                while ((cpt = stream.read(buffer, 0, bufferSize)) != -1) {
                    out.write(buffer, 0, cpt);
                    }

                 if (stream.available()<-1){
                     stream.reset();
                 }
                 stream.close();
                 out.flush();
                 out.close();
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
