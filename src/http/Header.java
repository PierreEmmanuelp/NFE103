package http;

import http.headers.Action;
import http.headers.Version;
import java.util.HashMap;

/**
 *
 * @author Pierre-Emmanuel Pourquier, Hervé TRUCHARD
 * @version 1.1
 */
public class Header {
    /**action http du header.*/
    private Action action;

    /**Host de ce header.*/
    private Host host;

    /**Cible (page) demandé pour ce header.*/
    private String cible;

    /**Version préconisée par el header.*/
    private Version version;

    /**Paramètres de ce header.*/
    private final HashMap parametres;

    /**Constructeur de la classe.*/
    public Header() {
        this.parametres = new HashMap();
    }

    /**Renvoit les paramètres de ce header.
     * @return les paramètres.
     */
    public final HashMap getParametres() {
        return parametres;
    }

     /**Renvoit l'action de ce header.
     * @return l'action.
     */
    public final Action getAction() {
        return action;
    }

     /**Enregistre l'action dans ce header.
      *@param paction l'action à enrigstrer.
     */
    public final void setAction(final Action paction) {
        this.action = paction;
    }

     /**Renvoit le host de ce header.
     * @return le host.
     */
    public final Host getHost() {
        return host;
    }

    /** Enregistre le host de ce header.
     * @param phost l'host à enregistrer
     */
    public final void setHost(final Host phost) {
        this.host = phost;
    }

     /**Renvoit la cible de ce header.
     * @return la cible.
     */
    public final String getCible() {
        return cible;
    }

    /** Enregistre la cible de ce header.
     * @param pcible la cible à enregistrer
     */
    public final void setCible(final String pcible) {
        this.cible = pcible;
    }

    /**Enregistre la version de ce header.
     * @param pversion la version à enregistrer
     */
    public final void setVersion(final Version pversion) {
        this.version = pversion;
    }
     /**Renvoit la version de ce header.
     * @return la version.
     */
    public final Version getVersion() {
        return version;
    }

    @Override
    public final String toString() {
        return "Header{" + action + " "
                + host + " " + cible + " " + version + '}';
    }
}
