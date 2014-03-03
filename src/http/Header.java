package http;

import http.headers.*;

/**
 *
 * @author Pierre-Emmanuel Pourquier, Hervé TRUCHARD
 * @version 1.0
 */
public class Header {
    private Action action;
    private Host host;
    private String cible;
    private Version version;
    private String User_Agent;
    /*private int port;
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

    public void setVersion(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return version;
    }

    public String getUser_Agent() {
        return User_Agent;
    }

    public void setUser_Agent(String User_Agent) {
        this.User_Agent = User_Agent;
    }

    
    
    
    
}
