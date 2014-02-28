package http;

import java.io.*;

//This product includes software developed by the JDOM Project
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

/**
 *
 * @author Pinatel Morgan
 * @version 1.0
 */
public class Configuration {
    
    private final String pathParametres;
    private static org.jdom2.Document document;
    private int poolThread;
    private int port;
    private String pathLog;

    /**
    * contructeur de la classe. 
    */
    public Configuration(){
        pathParametres="/home/morgan/Documents/NFE103/parametres.xml";
        Element racine = parser();
        String portValue = racine.getChild("port").getText();
        this.port = Integer.parseInt(portValue);
        String poolThreadValue = racine.getChild("threadpool").getText();
        this.poolThread = Integer.parseInt(poolThreadValue);
        this.pathLog = racine.getChild("pathlog").getText();
    }
    
    /**
    * permet de modifier les paramètres
    */
    private void modifier(String param, String child){
        Element racine = parser();
        Element courant = racine.getChild(child);
        courant.setText(param);
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        try
        {
            sortie.output(document, new FileOutputStream(pathParametres));
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
     
    /**
    * permet de modifier les paramètres
    */
    private Element parser(){
        Element racine = new Element("configuration"); 
        document = new Document(racine);
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            document = sxb.build(new File(pathParametres));
        }
        catch(JDOMException e){
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println(e);
        }
        racine = document.getRootElement();
        return racine;
    }

    /**
    * permet de récupérer le nombre de thread défini pour le pool
    * @return un nombre de thread
    */
    public int getPoolThread() 
    {
        return poolThread;
    }

    /**
    * permet de récupérer le port du socket
    * @return le port du socket
    */
    public int getPORT() 
    {
        return port;
    }

    /**
    * permet de récupérer le chemin du fichier de log
    * @return le chemin du fichier de log
    */
    public String getPathLog() 
    {
        return pathLog;
    }

    /**
    * permet de définir le nombre de thread défini pour le pool
    * @param pPoolThread
    */
    public void setPoolThread(int pPoolThread) 
    {
        this.poolThread=pPoolThread;
        modifier(String.valueOf(this.poolThread), "threadpool");
    }
   
    /**
    * permet de définir le port du socket
    * @param pPort
    */
    public void setPort(int pPort)
    {
        this.port=pPort;
        modifier(String.valueOf(this.port), "port");
    }
    
    /**
    * permet de définir le chemin du fichier de log
    * @param pPathLog
    */
    public void setPathLog(String pPathLog) 
    {
        this.pathLog=pPathLog;
        modifier(this.pathLog, "pathlog");
    }
    
}