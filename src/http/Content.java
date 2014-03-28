package http;

/** récupère le contenu d'une requete HTTP.
 * @author Pierre-Emmanuel Pourquier,Herve Truchard, Benjamin Pierrot
 * @version 1.2
 */
public class Content {

    /**Le contenu d'une requete http.*/
    private String contenu;

    /** Constructeur.*/
    public Content() {
        contenu = "";
    }

    /** Obtient le contenu.
     * @return le contenu
     */
    public final String getContenu() {
        return contenu;
    }

    /** Enregistre le contenu de la requete.
     * @param pcontenu le contenu
     */
    public final void setContenu(final String pcontenu) {
        this.contenu = pcontenu;
    }

    @Override
    public final String toString() {
        return "Content{" + contenu + "}";
    }

}
