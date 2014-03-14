package http;

import java.io.*;

/**
 *récupère contenue requete
 * @author Pierre-Emmanuel Pourquier,Herve Truchard
 * @version 1.1
 * @return void
 */
public class Content {
    
    protected String contenu;

    public Content() { // Constructeur de Content (enlever null)
        
        contenu = "";
        
    }   
    
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

    @Override
    public String toString() {
        return "Content{" + "contenu=" + contenu + '}';
    }
   
}
