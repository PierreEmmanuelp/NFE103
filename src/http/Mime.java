package http;
import org.apache.tika.Tika; // Utilisation de la library apache tika pour détecter le type mime

/**
 *
 * @author Pierre-Emmanuel Pourquier, Benjamin Pierrot
 * @version 2.0
 */
public class Mime {
    
   private static String mediaType;

    public Mime() {
        this.mediaType = "";
    }
   
    public static String extractTypeMime(String pFichier){
        
        Tika tika = new Tika();
        
       try {
            mediaType = tika.detect(pFichier);
            System.out.println(mediaType);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        
       if (mediaType.equals("application/octet-stream")) {// TODO
           mediaType = "text/html";
       }
            return  mediaType;
    }
}
