package http;

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
    private final HashMap<String,Object> headersRep = new HashMap<>();
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
     * @param reqHeader Le header de la requête
     */
    public Response(final Header reqHeader) {
        this.statut = "500";
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

        this.headersRep.put("Date", date);
        this.headersRep.put("Server", "CNAM_NFE103/1.0"); // see param
        this.headersRep.put("Content-Type", mimetostring); // Mime type
        //this.headersRep.put("Content-Encoding", "gzip"); // Charset
        this.headersRep.put("Content-Length", pLength); // length of chain
        //this.headersRep.put("Content-Length", Length); // length of chain
        //this.headersRep.put("Connection", "Keep-Alive;timeout=15, max=100");
        // length of chain
        this.headersRep.put("Connection", "close"); // length of chain

        String line = "HTTP/1.1 " + pCode + CRLF;
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
    public final String[] genereResponse(final String pRequest) {

        String request = pRequest;
  //      System.out.println(pRequest.toString());
        String[] response;
        response = new String[2];

        if (request == null || Response.INTERNAL_ERROR != null
                                      || !request.equals(INTERNAL_ERROR)) {
// Si rien on met index.html  (add to param + path recupéré de host + header)
            if (Response.headerQuest.getCible().equals("/")|| Response.headerQuest.getCible().equals("")) {
                request = request + "index.html";
              //  Http.requestlog.trace("Acces racine launch index.html");
            }

            FileContent file = new FileContent();
 //           System.out.println( this.hostpath + request.toString());
            Http.syslog.debug("Response>>>>>>"+this.hostpath + request );
            file.openFile(this.hostpath + request);

        switch (file.getStatus()) {
            case 200 :
                this.statut = "200";
                response[0] = this.headerRep(file.getLength().toString(), OK);
                response[1] = "OK";
                setStream(file.getFileContent());
                break;
            case 404 :
                this.statut = "404";
                response[0] = this.headerRep("400", NOT_FOUND);
                response[1] = "<h1>" + Response.NOT_FOUND + "</h1>";
                break;
            case 403 :
                this.statut = "403";
                response[0] = this.headerRep("400", FORBIDDEN);
                response[1] = "<h1>" + FORBIDDEN + "</h1>";
                break;
            case 500 :
                this.statut = "500";
                response[0] = this.headerRep("400", INTERNAL_ERROR);
                response[1] = "<h1>" + INTERNAL_ERROR + "</h1>";
                break;
            default:
                this.statut = "500";
                response[0] = this.headerRep("400", INTERNAL_ERROR);
                response[1] = "<h1>" + INTERNAL_ERROR + "</h1>";
                Http.requestlog.error("Erreur interne (response)");
            }

        } else {
            this.statut = "500";
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

    /**
     * @return le header de la reponse
     */
    public final HashMap<String,Object> getHeadersRep() {
        return headersRep;
    }

    /**
     * @return le statut de la reponse.
     */
    public final String getStatut() {
        return statut;
    }
}
