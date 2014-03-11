package http;

import java.io.*;

/**
 *récupère contenur requete
 * @author Pierre-Emmanuel Pourquier,Herve Truchard
 * @version 1.0
 * @return void
 */
public class Content {
    
    protected String contenu;
    
    
    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
/**
 * getLength : Retourne la taille du fichier
 * @return String taille du fichier pour construction du header 
 */    
    public String getLength() {
        return String.valueOf(this.contenu.length());
    }

   
   
}
