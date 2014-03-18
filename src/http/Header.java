package http;
import http.headers.Action;
import http.headers.Version;
import java.util.Hashtable;

/**
 *
 * @author Pierre-Emmanuel Pourquier, Hervé TRUCHARD
 * @version 1.1
 */
public class Header {
    private Action action;
    private Host host;
    private String cible;
    private Version version;
    private Hashtable parametres;

    public Header() {
        this.parametres = new Hashtable();
    }    
    
    public Hashtable getParametres() {
        return parametres;
    }
    
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getCible() {
        return cible;
    }

    public void setCible(String cible) {
        this.cible = cible;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Header{" + "action=" + action + ", host=" + host + ", cible=" + cible + ", version=" + version + '}';
    }
}
