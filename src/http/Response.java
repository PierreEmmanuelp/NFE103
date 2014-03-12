package http;

import java.util.Date;
import java.util.*;

/**
 * Correspond à un réponse HTTP
 *
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
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
    private String hostpath = "c:\\temp\\"; // TODO depuis host

    /**
     * Constructeur
     */
    public Response() {
    }

    /**
     * Todo : construction du header
     * Recupération du header
     * @return
     */
    private String headerRep(String Length, String Code) {

        Date date = new Date();

        this.HeadersRep.put("Date", date);
        this.HeadersRep.put("Server", "CNAM_NFE103/1.0"); // see param
        this.HeadersRep.put("Content-Type", "text/HTML; charset=iso-8859-1"); // Mime type
        this.HeadersRep.put("Content-Length", Length); // length of chain

        String line = http.headers.Version.HTTP_1_0 + " " + Code + CRLF;
        String key;
        Enumeration e = this.HeadersRep.keys();
        while (e.hasMoreElements()) {
            key = (String) e.nextElement();
            line += key + ": " + this.HeadersRep.get(key) + CRLF;
        }
        line += CRLF;
        return line;

    }

    public String genereResponse(String request) {

        String response = "";
        if (request != this.INTERNAL_ERROR) {
            
          // Si rien on met index.html  (add to param + path recupéré de host + header)
          if (request.substring(request.length()-1).equals("/")) {
              request = request + "index.html";
          }
            
        FileContent file = new FileContent();
        
        file.openFile(this.hostpath + request);
        
        switch (file.getStatus()) {
            case 200:
                response = this.headerRep(file.getLength(),this.OK) + request + file.getContenu();
                break;
            case 404:
                response = this.headerRep("400",this.NOT_FOUND) + "<h1>" + this.NOT_FOUND + "</h1>";
                break;
            case 403:
                response = this.headerRep("400",this.FORBIDDEN) + "<h1>" + this.FORBIDDEN + "</h1>";
                break;                
            case 500:
                response = this.headerRep("400",this.INTERNAL_ERROR) + "<h1>" + this.INTERNAL_ERROR + "</h1>";
                break;
            default:
                response = this.headerRep("400",this.INTERNAL_ERROR) + "<h1>" + this.INTERNAL_ERROR + "</h1>";
                System.out.println("Il faut davantage travailler.");
        }
        
        } else {
                response = this.headerRep("400",this.INTERNAL_ERROR) + "<h1>" + this.INTERNAL_ERROR + "</h1>";
        }

        return response;
    }

    public String genererErreur500() {
        return this.genereResponse(INTERNAL_ERROR);
    }
}
