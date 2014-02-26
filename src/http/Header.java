package http;

/**
 *
 * @author Pierre-Emmanuel Pourquier, Hervé TRUCHARD
 * @version 1.0
 */
public class Header {
    private Action action;
    private Host host;
    private String cible;

    /*private int port;
    private Version version;
    private String path;
    private String query;
    private String file;
    private String reference; */
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

    
    
    
    
}
