package http;

/**
 *récupère contenue requete
 * @author Pierre-Emmanuel Pourquier,Herve Truchard, Benjamin Pierrot
 * @version 1.2
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

    @Override
    public String toString() {
        return "Content{" + "contenu=" + contenu + '}';
    }
   
}
