package http;

import java.io.BufferedInputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

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
//    private static Header headerRep;
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
     * All OK.
     */
    private static final String OK = "200 OK";
    /**
     * Bad request.
     */
    private static final String BAD_REQUEST = "400 Bad Request";
    /**
     * Forbidden request.
     */
    private static final String FORBIDDEN = "403 Forbidden";
    /**
     * Resource not found.
     */
    private static final String NOT_FOUND = "404 Not Found";
    /**
     * Resource internal system error.
     */
    private static final String INTERNAL_ERROR = "500 Internal Server Error";
    /**
     * Current header.
     */
    private Hashtable HeadersRep = new Hashtable();
    /**
     * hostpath : path du host.
     */
    private String hostpath;

    /**
     * Constructeur.
     * @param reqHeader Le header de la requête
     */
    public Response(final Header reqHeader) {
        Response.headerQuest = reqHeader;
        try {
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

        this.HeadersRep.put("Date", date);
        this.HeadersRep.put("Server", "CNAM_NFE103/1.0"); // see param
        this.HeadersRep.put("Content-Type", mimetostring); // Mime type
        //this.HeadersRep.put("Content-Encoding", "gzip"); // Charset
        this.HeadersRep.put("Content-Length", pLength); // length of chain
        //this.HeadersRep.put("Content-Length", Length); // length of chain
        //this.HeadersRep.put("Connection", "Keep-Alive;timeout=15, max=100");
        // length of chain
        this.HeadersRep.put("Connection", "close"); // length of chain

        String line = "HTTP/1.1 " + pCode + CRLF;
        String key;
        Enumeration e = this.HeadersRep.keys();
        while (e.hasMoreElements()) {
            key = (String) e.nextElement();
            line += key + ":" + this.HeadersRep.get(key) + CRLF;
        }
        line += CRLF;
        return line;

    }

    /**
     *
     * @param pRequest String
     * @return response
     */
    public final String[] genereResponse(final String pRequest) {

        String request = pRequest;
        String[] response;
        response = new String[2];

        if (request == null || Response.INTERNAL_ERROR != null
                                      || !request.equals(INTERNAL_ERROR)) {
// Si rien on met index.html  (add to param + path recupéré de host + header)
            if (Response.headerQuest.getCible().equals("/")) {
                request = request + "index.html";
                Http.requestlog.trace("Acces racine launch index.html");
            }

            FileContent file = new FileContent();

            file.openFile(this.hostpath + request);

        switch (file.getStatus()) {
            case 200:
                response[0] = this.headerRep(file.getLength().toString(), OK);
                response[1] = "OK";
                setStream(file.getFileContent());
                break;
            case 404:
                response[0] = this.headerRep("400", NOT_FOUND);
                response[1] = "<h1>" + Response.NOT_FOUND + "</h1>";
                break;
            case 403:
                response[0] = this.headerRep("400", FORBIDDEN);
                response[1] = "<h1>" + FORBIDDEN + "</h1>";
                break;
            case 500:
                response[0] = this.headerRep("400", INTERNAL_ERROR);
                response[1] = "<h1>" + INTERNAL_ERROR + "</h1>";
                break;
            default:
                response[0] = this.headerRep("400", INTERNAL_ERROR);
                response[1] = "<h1>" + INTERNAL_ERROR + "</h1>";
                Http.requestlog.error("Erreur interne (response)");
            }

        } else {
            response[0] = this.headerRep("400", INTERNAL_ERROR);
            response[1] = "<h1>" + INTERNAL_ERROR + "</h1>";
        }

        return response;
    }

    /**
     * Générer erreur 500.
     */
    public final void genererErreur500() {
        this.genereResponse(INTERNAL_ERROR);
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
}
