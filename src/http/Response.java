package http;

import http.headers.CodeResponse;
import java.io.BufferedInputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Correspond à un réponse HTTP.
 *
 * @author Pierre-Emmanuel Pourquier, Benjamin Pierrot
 * @version 1.1
 */
public class Response {

    /**
     * header.
     */
    private static Header headerQuest;
    /**
     * header.
     */
    private static String headerRep;
    /**
     * methode (Methode GET POST ...).
     */
    private static String methode;
    /**
     * Content.
     */
//    private static Content ContentRep;
    private BufferedInputStream pstream;
    /**
     *
     */
//    private static String request;
    /**
     * CRLF.
     */
    public static final String CRLF = "\r\n";
    /**
     * Protocol.
     */
    public static final String PROTOCOL = "HTTP/1.0 ";
    /**
     * Current header.
     */
    private final HashMap<String, Object> headersRep = new HashMap<>();
    /**
     * hostpath : path du host.
     */
    private String hostpath;
    /**
     * statut de la réponse.
     */
    private String statut;

    /**
     * Constructeur.
     *
     * @param reqHeader Le header de la requête
     */
    public Response(final Header reqHeader) {
        this.statut = "500";
        Response.headerQuest = reqHeader;
        try {
            this.methode = reqHeader.getAction().toString();
            this.hostpath = reqHeader.getHost().getPath(); //ajout du host
        } catch (Exception e) { // TODO
            Http.syslog.error("error ligne 79: " + e);
        }
        this.pstream = null;
    }

    /**
     * Todo : construction du header Recupération du header.
     *
     * @param pCode String
     * @param pLength String
     * @return String
     */
    private String headerRep(final String pLength, final String pCode) {

        Date date = new Date();
        String mimetostring;


        if (!pCode.substring(0, 1).equals("4")
                && !pCode.substring(0, 1).equals("5")) { // si erreur

            mimetostring = Mime.extractTypeMime(hostpath + headerQuest.getCible());
        } else {
            mimetostring = "text/html";
        }
            Http.getConfig().addParam("Serveur","CNAM_NFE103/1.0" );
            Http.getConfig().updateParam();
        this.headersRep.put("Date", date);
        this.headersRep.put("Server","CNAM_NFE103/1.0"); // see param
        this.headersRep.put("Content-Type", mimetostring); // Mime type
        //this.headersRep.put("Content-Encoding", "gzip"); // Charset
        this.headersRep.put("Content-Length", pLength); // length of chain
        //this.headersRep.put("Content-Length", Length); // length of chain
        //this.headersRep.put("Connection", "Keep-Alive;timeout=15, max=100");
        // length of chain
        this.headersRep.put("Connection", "close"); // length of chain

        String line = "HTTP/1.1 " + " " + pCode + CRLF; // TODO RECUP PROTOCOLE
        String key;

        final Enumeration<String> e;
        e = Collections.enumeration(headersRep.keySet());

        while (e.hasMoreElements()) {
            key = e.nextElement();
            line += key + ":" + this.headersRep.get(key) + CRLF;
        }
        line += CRLF;
        return line;

    }

    /**
     *
     * @param pRequest String
     * @return response
     */
    public final String genereResponse(final String pRequest) {

        String request = pRequest;

        FileContent file = new FileContent(); // Instanciation class fileContent

        if (!request.equals(
                CodeResponse.INTERNAL_ERROR.getDescription().toString())) {
            if (Response.methode.equals("GET")) {

// Si rien on met index.html  (add to param + path recupéré de host + header)
                if (Response.headerQuest.getCible().equals("/")
                        || Response.headerQuest.getCible().equals("")) {
                    request = request + "index.html";
                    //  Http.requestlog.trace("Acces racine launch index.html");
                }
                file.openFile(this.hostpath + request);
                this.statut = String.valueOf(file.getStatus());

            } else {
                this.statut = "400";
                file.openFile("./Error.html/HTTP_BAD_REQUEST.html");
            }

            Http.syslog.debug("Response>" + this.hostpath + request);
            
            } else {
                this.statut = "500";
                file.openFile("./Error.html/HTTP_INTERNAL_SERVER_ERROR.html");
            }
            switch (this.statut) {
                case "200":
                    headerRep = this.headerRep(file.getLength().toString(),
                            CodeResponse.OK.getDescription());
                    break;
                case "404":
                    headerRep = this.headerRep(file.getLength().toString(),
                            CodeResponse.NOT_FOUND.getDescription().toString());
                    break;
                case "403":
                    headerRep = this.headerRep(file.getLength().toString(),
                            CodeResponse.FORBIDDEN.getDescription().toString());
                    break;
                case "500":
                    headerRep = this.headerRep(file.getLength().toString(),
                            CodeResponse.INTERNAL_ERROR.getDescription().toString());
                    break;
                case "400":
                    headerRep = this.headerRep(file.getLength().toString(),
                            CodeResponse.BAD_REQUEST.getDescription().toString());
                    break;
                default:
                    headerRep = this.headerRep(file.getLength().toString(),
                            CodeResponse.INTERNAL_ERROR.getDescription().toString());
                    Http.requestlog.error("Erreur interne (response)");
            }
            // Retour du fichier
            setStream(file.getFileContent());

        return headerRep;
    }

    /**
     * Générer erreur 500.
     */
    public final void genererErreur500() {
        this.genereResponse(CodeResponse.INTERNAL_ERROR.getDescription());
    }

    /**
     *
     * @param stream BufferedInputStream
     */
    private void setStream(final BufferedInputStream stream) {
        this.pstream = stream;
    }

    /**
     *
     * @return pstream BufferedInputStream
     */
    public final BufferedInputStream getStream() {
        return pstream;
    }

    /**
     * @return le header de la reponse
     */
    public final HashMap<String, Object> getHeadersRep() {
        return headersRep;
    }

    /**
     * @return le statut de la reponse.
     */
    public final String getStatut() {
        return statut;
    }
}
