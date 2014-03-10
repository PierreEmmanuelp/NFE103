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
    private static Header headerQuest;
    /**
     * header
     */
    private static Header headerRep;    
    /**
     * Content
     */
    private static Content ContentRep;
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
//    /**
//     * Protocol.
//     */
//    public final static String PROTOCOL = "HTTP/1.0 ";
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
    

    public String genereResponse(String request) {
        
        return request.toString();
    }
    
    public String genererErreur500() {
        return this.genereResponse(INTERNAL_ERROR);
    }

}
