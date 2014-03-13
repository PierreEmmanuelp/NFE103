package http;
import org.apache.tika.Tika; // Utilisation de la library apache tika pour détecter le type mime

/**
 *
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
 */
public class Mime {
    
//   private String pFichier;
   private String mediaType;

    public Mime() {
        this.mediaType = "";
    }
   
    public String extractTypeMime(String pFichier){
        
        Tika tika = new Tika();
        
       try {
            mediaType = tika.detect(pFichier);
            System.out.println(mediaType);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        
            return  mediaType;
    }
}
