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
    
    /**
    * permet d'ajouter un hôte dans la liste s'il n'existe pas déjà
    * @param pHost
    */
    public void addHost(Host pHost){
         if(this.existe(pHost)==false){
            this.hostList.add(pHost);
        } 
    }
    
    /**
    * permet de supprimer un hôte dans la liste
    * @param phost
    */
    public void removeHost(Host phost)
    {
        this.hostList.remove(phost);
    }
    
    /**
    * permet de récupérer un hôte avec son nom
    * @param pName
    * @return l'hôte recherché
    */
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
    * permet de récupérer le chemin d'un hôte avec son nom
    * @param pName
    * @return le chemin de l'hôte
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
    * permet de vérifier l'existence du nom d'un hôte dans les paramètres
    * @param pHost
    * @return existe ou non
    */
    public boolean existe(Host pHost) {
        boolean existe;
        existe = false;
        Iterator<Host> i = this.getHostList().iterator();		
        while(i.hasNext()){
            Host host = (Host)i.next();
            if(pHost.getName().equals(host.getName())){              
                 existe = true;
            }  
        } 
        return existe;
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
