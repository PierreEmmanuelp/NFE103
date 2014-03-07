package http;
import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
 */
public class Mime {
    
   private String pCheminCible;
   
    public String extractTypeMime(){
        
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();

        String mimeType = mimeTypesMap.getContentType(pCheminCible);

            return  mimeType;
            }
    
}
