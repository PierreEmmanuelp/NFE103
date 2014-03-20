package http;
import http.headers.Action;
import http.headers.Version;
import java.util.ArrayList;

/** Requete HTTP.
 * @author Pierre-Emmanuel Pourquier
 * @version 1.1
 */
public class Request {
    /** Header de la requete HTTP.*/
    private final Header header;

    /** Contenu de la requete HTTP.*/
    private Content content;

    /** Requete brute à analyser.*/
    private final ArrayList<String> request;

    /** Constructeur de la requete.
     * @param pRequete requete http à analyser
     */
    public Request(final ArrayList<String> pRequete) {
        this.request = pRequete;
        this.header = new Header();
        this.content = new Content();
        analyseRequest();
    }

    /** Parse request pour créer un header et un content.
     */
    private void analyseRequest() {
        //première ligne : action
        String premierHead = request.get(0);
        Http.syslog.trace(premierHead);
        this.header.setAction(parseActionHTTP(premierHead));
        this.header.setCible(parseCibleHTTP(premierHead));
        this.header.setVersion(parseVersionHTTP(premierHead));

        for (int i = 1; i < request.size(); i++) {
            if (request.get(i).startsWith("Host:")) { //si c'est un host
                String nameHost = this.parseHostHTTP(request.get(i));
                Host host = http.Serveur.getHost().getHost(nameHost);
                this.header.setHost(host);
            } else {
                this.parseArgument(request.get(i));
            }
        }
    }

    /** Vérifie si la requete nécessite du contenu.
     * @return true si besoin d'un content
     */
    public final boolean besoinContent() {
      //if(header.getContentLength > 0){
      //    return true;
      //}else{
        return false; //si il y a du contenu. Je l'ai mis a des fins de tests
      //}
    }

    /** Analyse le contenu.
     * @param pContenu le contenu au format string de la requete http initiale
     */
    public final void analyseContent(final String pContenu) {

    }

    /** Renvoit le Header correspondant à cette requête.
     * @return le Header.
     */
    public final Header getHeader() {
        return header;
    }

    /** Renvoit le Content correspondant à cette requête.
     * @return le content.
     */
    public final Content getContent() {
        return content;
    }

    /** Ajoute le contenu à la requete.
     * @param pcontent le contenu
     */
    public final void setContent(final Content pcontent) {
        this.content = pcontent;
    }

    /** Parse la première ligne http et en extrait l'action.
     * @param pHeaderHTTP la première ligne du header contenant l'action
     * @return le type d'action | null si aucune action
     */
    private Action parseActionHTTP(final String pHeaderHTTP) {
        String headerHTTP = "";
        Action action;
        if (pHeaderHTTP.indexOf(" ") != -1) {
            headerHTTP = pHeaderHTTP.substring(0, pHeaderHTTP.indexOf(" "));
        }
        switch(headerHTTP) {
            case "GET":
                action = Action.GET;
                break;
            case "POST":
                action = Action.POST;
                break;
            case "HEAD":
                action = Action.HEAD;
                break;
            case "CONNECT":
                action = Action.CONNECT;
                break;
            case "DELETE":
                action = Action.DELETE;
                break;
            case "OPTIONS":
                action = Action.OPTIONS;
                break;
            case "PATCH":
                action = Action.PATCH;
                break;
            case "PUT":
                action = Action.PUT;
                break;
            case "TRACE":
                action = Action.TRACE;
                break;
            default:
                //TODO ERROR REQUETE MAL FORMEE
                action = null;
        }
        return action;
    }

    /** Parse la première enête http et extrait la cible de celle-ci.
     * @param pHeaderHTTP la première entête d'une requête HTTP
     * @return la cible de la requête http
     */
    private String parseCibleHTTP(final String pHeaderHTTP) {
        String cible = "";
        String headerHTTP;
        if (pHeaderHTTP.indexOf("/") != -1 && pHeaderHTTP.indexOf(" ") != -1) {
            headerHTTP = pHeaderHTTP.substring(pHeaderHTTP.indexOf("/"));
            cible = headerHTTP.substring(0, headerHTTP.indexOf(" "));
        }
        return cible;
    }

    /** Parse la première entête http et extrait la version de celle-ci.
     * @param pHeaderHTTP la première entête d'une requête HTTP
     * @return la version du protocole de la requête http
     */
    private Version parseVersionHTTP(final String pHeaderHTTP) {
        Version version;
        String lheader = "";
        if (pHeaderHTTP.indexOf("HTTP/") != -1) {
            lheader = pHeaderHTTP.substring(pHeaderHTTP.indexOf("HTTP/"));
        }
        switch (lheader) {
            case "HTTP/1.1" :
                version = Version.HTTP_1_1;
                break;
            case "HTTP/1.0" :
                version = Version.HTTP_1_0;
                break;
            case "HTTP/0.9" :
                version = Version.HTTP_0_9;
                break;
            default:
                version = Version.HTTP_0_9;
        }
        return version;
    }

    /** Parse la première entête http et extrait le host de celle-ci.
     * @param pHeaderHTTP la première entête d'une requête HTTP
     * @return le host de la requête http
     */
    private String parseHostHTTP(final String pHeaderHTTP) {
        String nomHost = "";
        if (pHeaderHTTP.indexOf(":") != -1) {
            nomHost = pHeaderHTTP.substring(pHeaderHTTP.indexOf(":") + 2);
        }
        return nomHost;
    }

    /** Parse une ligne de requête http et l'ajoute au hastable.
     * @param pHeaderHTTP la ligne de requête à parser
    */
    private void parseArgument(final String pHeaderHTTP) {
        String key;
        String value;
        if (pHeaderHTTP.contains(":")) {
        key = pHeaderHTTP.substring(0, pHeaderHTTP.indexOf(":"));
        int index, length;
        index = pHeaderHTTP.indexOf(":");
        length = pHeaderHTTP.length();
        value = pHeaderHTTP.substring(index, length);
        this.header.getParametres().put(key, value);
        }
    }

    @Override
    public final String toString() {
        String msg;
        if (content.getContenu().isEmpty()) {
            msg = "Request{" + header + "}";
        } else {
            msg = "Request{" + header + " content : " + content+"}";
        }
        return msg;
    }

}
