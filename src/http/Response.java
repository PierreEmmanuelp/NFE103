package http;

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
    public static Header header;
    /**
     * 
     */
    public static Content Content;
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
    public final static String OK = "200 OK";
    /**
     * Bad request.
     */
    public final static String BAD_REQUEST = "400 Bad Request";
    /**
     * Forbidden request.
     */
    public final static String FORBIDDEN = "403 Forbidden";
    /**
     * Resource not found.
     */
    public final static String NOT_FOUND = "404 Not Found";
    /**
     * Resource internal system error.
     */
    public final static String INTERNAL_ERROR = "500 Internal Server Error";
    
    public String lireFichier(String var) {

        return null;
        
    }

    public void genereResponse() {
        
    }

    public String pRequest(Request request) {
        
        return request.toString();
    
    }
}
