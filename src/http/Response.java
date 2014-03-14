package http;

import java.io.BufferedInputStream;
import java.util.*;

/**
 * Correspond à un réponse HTTP
 *
 * @author Pierre-Emmanuel Pourquier, Benjamin Pierrot
 * @version 1.1
 */
public class Response {

    /**
     * header
     */
    private static Header headerQuest;
    /**
     * header
     */
//    private static Header headerRep;    
    /**
     * Content
     */
//    private static Content ContentRep;
    
    private BufferedInputStream pstream;
    /**
     * Content
     */
    private static Content ContentQuest;
    /**
     *
     */
    public static String request;
    /**
     * CRLF
     */
    public final static String CRLF = "\r\n";
    /**
     * Protocol.
     */
    public final static String PROTOCOL = "HTTP/1.0 ";
    /**
     * All OK.
     */
    private final static String OK = "200 OK";
    /**
     * Bad request.
     */
    private final static String BAD_REQUEST = "400 Bad Request";
    /**
     * Forbidden request.
     */
    private final static String FORBIDDEN = "403 Forbidden";
    /**
     * Resource not found.
     */
    private final static String NOT_FOUND = "404 Not Found";
    /**
     * Resource internal system error.
     */
    private final static String INTERNAL_ERROR = "500 Internal Server Error";
    /**
     * Current header.
     */
    private Hashtable HeadersRep = new Hashtable();
    private String hostpath = "/var/www/"; // TODO depuis host

    /**
     * Constructeur
     */
    public Response(Header reqHeader) {
        this.headerQuest = reqHeader;
        this.pstream = null;
    }

    /**
     * Todo : construction du header Recupération du header
     *
     * @return
     */
    private String headerRep(String Length, String Code) {

        Date date = new Date();
        String mimetostring;
        
        
        if (!Code.substring(0,1).equals("4") && !Code.substring(0,1).equals("5")) { // si erreur
               
            Mime Mime = new Mime();
            mimetostring = Mime.extractTypeMime(hostpath + headerQuest.getCible());
        } else {
            mimetostring = "text/html";
        }

        this.HeadersRep.put("Date", date);
        this.HeadersRep.put("Server", "CNAM_NFE103/1.0"); // see param
        this.HeadersRep.put("Content-Type", mimetostring); // Mime type
        //this.HeadersRep.put("Content-Encoding", "gzip"); // Charset
        this.HeadersRep.put("Content-Length", Length); // length of chain
        //this.HeadersRep.put("Content-Length", Length); // length of chain
    //    this.HeadersRep.put("Connection", "Keep-Alive;timeout=15, max=100"); // length of chain
        this.HeadersRep.put("Connection", "close"); // length of chain

        String line = "HTTP/1.1 " + Code + CRLF;
        String key = "";
        Enumeration e = this.HeadersRep.keys();
        while (e.hasMoreElements()) {
            key = (String) e.nextElement();
            line += key + ":" + this.HeadersRep.get(key) + CRLF;
        }
        line += CRLF;
        return line;

    }

    public String[] genereResponse(String request) { // TODO virer String request

        //String response = "";
         String[] response;
         response = new String[2];
         
        if (request == null ? Response.INTERNAL_ERROR != null : !request.equals(Response.INTERNAL_ERROR)) {

            // Si rien on met index.html  (add to param + path recupéré de host + header)
            if (Response.headerQuest.getCible().equals("/")) {
                request = request + "index.html";
                //System.out.println("req -->" +request);
            }

            FileContent file = new FileContent();

            file.openFile(this.hostpath + request);

            switch (file.getStatus()) {
                case 200:
                    response[0] = this.headerRep(file.getLength().toString(),this.OK) ;
                    response[1] = "OK";
                    setStream(file.getFileContent());
                    break;
                case 404:
                    response[0] = this.headerRep("400", this.NOT_FOUND); 
                    response[1] = "<h1>" + this.NOT_FOUND + "</h1>";
                    break;
                case 403:
                    response[0] = this.headerRep("400", this.FORBIDDEN);
                    response[1] =  "<h1>" + this.FORBIDDEN + "</h1>";
                    break;
                case 500:
                    response[0] = this.headerRep("400", this.INTERNAL_ERROR);
                    response[1] = "<h1>" + this.INTERNAL_ERROR + "</h1>";
                    break;
                default:
                    response[0] = this.headerRep("400", this.INTERNAL_ERROR);
                    response[1] = "<h1>" + this.INTERNAL_ERROR + "</h1>";
                    System.out.println("Il faut davantage travailler.");
            }

        } else {
            response[0] = this.headerRep("400", this.INTERNAL_ERROR);
            response[1] = "<h1>" + this.INTERNAL_ERROR + "</h1>";
        }

        return response;
    }

    public void genererErreur500() {
        this.genereResponse(INTERNAL_ERROR);
    }
    
    private void setStream(BufferedInputStream stream) {
       this.pstream = stream;
    }
    
    public BufferedInputStream getStream() {
        return pstream;
     }
} 
            

