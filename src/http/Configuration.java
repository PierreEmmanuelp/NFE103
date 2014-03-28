package http;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//This product includes software developed by the JDOM Project
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

/**
 * Gère la configuration du serveur http.
 * @author Morgan Pinatel, Pourquier Pierre-Emmanuel
 * @version 1.2
 */
public class Configuration {

    /** Chemin du fichier de paramètres.*/
    private final String pathParametres;
    /** Document Jdom.*/
    private static org.jdom2.Document document;
    /** Nombre de thread.*/
    private int poolThread;
    /** Port du serveur.*/
    private int port;
    /** Chemin du fichier de log.*/
    private String pathLog;
    /** Objet Hosts.*/
    private final Hosts hosts;
    /** Tableau de paramètres.*/
    private final Map detail = new java.util.HashMap();

    /**
     * contructeur de la classe.
     */
    public Configuration() {
        pathParametres = "parametres.xml";
        Element racineConf = parser("configuration");
        String portValue = racineConf.getChild("port").getText();
        this.port = Integer.parseInt(portValue);
        String poolThreadValue = racineConf.getChild("threadpool").getText();
        this.poolThread = Integer.parseInt(poolThreadValue);
        this.pathLog = racineConf.getChild("pathlog").getText();
        this.hosts = new Hosts();
        Element hostsElement = racineConf.getChild("hosts");
        List<Element> hostList = hostsElement.getChildren("host");
        Iterator<Element> i = hostList.iterator();
        while (i.hasNext()) {
            Element element = (Element) i.next();
            Attribute name = element.getAttribute("name");
            Attribute path = element.getAttribute("path");
            File f = new File(path.getValue());
            String pathValue;
            if (f.exists()) {
                pathValue = path.getValue();
            } else {
                pathValue = "error 404";
            }
            Host host = new Host(name.getValue(), pathValue);
            this.hosts.addHost(host);
        }
        Element racineParam = parser("detail");
        List<Element> paramsList = racineParam.getChildren();
        Iterator<Element> y = paramsList.iterator();
        while (y.hasNext()) {
            Element element = (Element) y.next();
            String name = element.getName();
            String value = element.getAttribute("value").getValue();
            detail.put(name, value);
        }
    }

    /**
     * permet de modifier les paramètres.
     * @param param paramètre
     * @param child child jdom
     */
    private void modifier(final String param, final String child) {
        Element racine = parser("configuration");
        Element courant = racine.getChild(child);
        courant.setText(param);
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        try {
            sortie.output(document, new FileOutputStream(pathParametres));
        } catch (IOException e) {
            Http.syslog.error("Err96 - " + e.getMessage());
        }
    }

   /**
     * permet de récupérer la racine.
     * @param englobe racine englobante
     * @return racine
     */
    private Element parser(final String englobe) {
        Element racine = new Element("configuration");
        document = new Document(racine);
        SAXBuilder sxb = new SAXBuilder();
        try {
            document = sxb.build(new File(pathParametres));
        } catch (JDOMException | IOException e) {
            Http.syslog.error(e.getMessage());
        }
        racine = document.getRootElement();
        Element base = racine.getChild(englobe);
        return base;
    }

    /**
     * permet d'enregistrer les hotes dans le fichier de paramètres.
     */
    public final void updateHosts() {
         Element racine = parser("configuration");
        Element hostsElement = racine.getChild("hosts");
        hostsElement.removeChildren("host");
        Iterator<Host> i = this.hosts.getHostList().iterator();
        while (i.hasNext()) {
            Host host = (Host) i.next();
            String hostName = host.getName();
            String hostPath = host.getPath();
            Element theHost = new Element("host");
            hostsElement.addContent(theHost);
            Attribute attName = new Attribute("name", hostName);
            Attribute attPath = new Attribute("path", hostPath);
            theHost.setAttribute(attName);
            theHost.setAttribute(attPath);
        }
        saveXML();
    }

    /**
     * permet d'enregistrer les paramètres dans le fichier de paramètres.
     */
    public final void updateParam() {
        Element racine = parser("detail");
        List<Element> paramList = racine.getChildren();
        Iterator<Element> i = paramList.iterator();
        while (i.hasNext()) {
            Element child = (Element) i.next();
            racine.removeChild(child.getName());
        }
        Set listKeys = this.detail.keySet();
        Iterator it = listKeys.iterator();
        while (it.hasNext()) {
            Object param = it.next();
            String key = (String) param;
            String value = (String) this.detail.get(key);
            Element theParam = new Element(key);
            racine.addContent(theParam);
            Attribute attValue = new Attribute("value", value);
            theParam.setAttribute(attValue);
        }
        saveXML();
    }

    /**
     * permet d'enregistrer le fichier de paramètres.
     */
    private void saveXML() {
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        try {
            sortie.output(document, new FileOutputStream(pathParametres));
        } catch (IOException e) {
            Http.syslog.error("Err174 - " + e.getMessage());
        }
    }

    /**
     * permet de vérifier l'existence d'un paramètre dans la map detail.
     * @param key clef du paramètre
     * @return existe
     */
    public final Boolean existeParam(final String key) {
       Boolean existe = this.detail.containsKey(key);
       return existe;
    }

    /**
     * permet d'enregistrer un paramètre dans la map detail.
     * permet de modifier la valeur d'un paramètre dans la map detail.
     * @param key clef du paramètre
     * @param value valeur du paramètre
     */
    public final void addParam(final String key, final String value) {
        if (existeParam(key)) {
            this.detail.remove(key);
        }
        this.detail.put(key, value);
    }

    /**
     * permet de récupérer le nombre de thread défini pour le pool.
     * @return un nombre de thread
     */
    public final int getPoolThread() {
        return poolThread;
    }

     /**
     * permet de récupérer le port du socket.
     * @return le port du socket
     */
    public final int getPORT() {
        return port;
    }

    /**
     * permet de récupérer le chemin du fichier de log.
     * @return le chemin du fichier de log
     */
    public final String getPathLog() {
        return pathLog;
    }

    /**
     * permet de récupérer la liste des hôtes.
     * @return la liste des hôtes
     */
    public final Hosts getHosts() {
        return hosts;
    }

    /**
     * permet de récupérer la valeur d'un paramètre "détail".
     * @return la valeur du paramètre "détail".
     * @param pKey nom du paramètre
     */
    public final String getParam(final String pKey) {
        return (String) this.detail.get(pKey);
    }

    /**
     * permet de définir le nombre de thread défini pour le pool.
     * @param pPoolThread nombre de thread
     */
    public final void setPoolThread(final int pPoolThread) {
        this.poolThread = pPoolThread;
        modifier(String.valueOf(this.poolThread), "threadpool");
    }

    /**
     * permet de définir le port du socket.
     * @param pPort port du socket
     */
    public final void setPort(final int pPort) {
        this.port = pPort;
        modifier(String.valueOf(this.port), "port");
    }

    /**
     * permet de définir le chemin du fichier de log.
     * @param pPathLog chemin du fichier de log
     */
    public final void setPathLog(final String pPathLog) {
        this.pathLog = pPathLog;
        modifier(this.pathLog, "pathlog");
    }

}
