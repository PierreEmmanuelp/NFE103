package http;
import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
 */
public class Mime {
    
   private String pFichier;
   
    public String extractTypeMime(){
        
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();

        String mimeType = mimeTypesMap.getContentType(pFichier);

            return  mimeType;
            }
    
}
