package http;
import debug.Trace;
import org.apache.tika.Tika;

/**
 *
 * @author Pierre-Emmanuel Pourquier, Benjamin Pierrot
 * @version 2.0
 */
public class Mime {
    
   private static String mediaType;

    public Mime() {
        mediaType = "";
    }
   
    public static String extractTypeMime(String pFichier){
        
        Tika tika = new Tika();
        
       try {
            mediaType = tika.detect(pFichier);
            Trace.trace(mediaType);
        } catch(Exception e) {
            Trace.trace(e.getMessage());
        }
        
       if (mediaType.equals("application/octet-stream")) {// TODO
           mediaType = "text/html";
       }
            return  mediaType;
    }
}
