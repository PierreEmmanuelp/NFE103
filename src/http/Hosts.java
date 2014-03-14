package http;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * @author morgan
 * @version 1.0
 */
public class Hosts {
    
    private final ArrayList<Host> hostList;

    /**
    * contructeur de la classe
    */
    public Hosts(){
        this.hostList = new ArrayList();
    }
    
    public void addHost(Host pHost){
         this.hostList.add(pHost);
    }
    
    public void removeHost(Host host)
    {
        this.hostList.remove(host);
    }
    
    public Host getHost(String pName)
    {
        Iterator<Host> i = hostList.iterator();
        while(i.hasNext())
        {
            Host host = (Host)i.next();
            if(host.getName().equals(pName))
            {
                return host;
            }
        }
        return null;
    }
    
     /**
    * permet de récupérer le nom de l'hôte
    * @param pName
    * @return le nom de l'hôte
    */
    public String getPath(String pName){
        Iterator<Host> i = hostList.iterator();		
        while(i.hasNext())
        {
            Host host = (Host)i.next();
            if(host.getName().matches(pName)){
                return host.getPath();
            }
       }
       return null;
    }
    
    /**
    * permet de récupérer la liste des hôtes
    * @return la liste des hôtes
    */
    public List<Host> getHostList() 
    {
        return hostList;
    }
    
}
