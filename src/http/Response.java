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
    protected Hashtable myHeaders = new Hashtable();
   
    
    /**
     * Todo : construction du header + response 
     * @return 
     */

    private String headerRep() {
                        
     Date date = new Date();
         
      //this.myHeaders.put("Status","HTTP/1.0 200 OK");
      //this.myHeaders.put("200","OK");
      this.myHeaders.put("Date",date); 
      this.myHeaders.put("Server","CNAM_NFE103/1.0"); // see param
      this.myHeaders.put("Content-Type","text/HTML"); // Mime type
      this.myHeaders.put("Content-Length","1234"); // length of chain
      
      String line = this.PROTOCOL + this.OK;
      String key;
      Enumeration e = this.myHeaders.keys();
      while(e.hasMoreElements())
      {
         key = (String)e.nextElement();
         line += key + ": " + this.myHeaders.get(key) + CRLF;
      }
      line += CRLF;
      return line;
      
    }  
    
    public String genereResponse(String request) {
        
        return this.headerRep() + request.toString();
    }
 
    public String genererErreur500() {
        return this.genereResponse(INTERNAL_ERROR);
    }
}
