package http;

/**
 * représente un host sur le serveur http.
 * @author Morgan Pinatel, Pourquier Pierre-Emmanuel
 * @version 1.1
 */
public class Host {

    private String name;
    private String path;

    /**
    * contructeur de la classe.
    * @param pName
    * @param pPath
    */
    public Host(String pName, String pPath){
        this.name = pName;
        this.path = pPath;
    }

    /**
    * permet de récupérer le nom de l'hôte.
    * @return le nom de l'hôte
    */
    public String getName()
    {
        return name;
    }

     /**
    * permet de récupérer le chemin de l'hôte.
    * @return le chemin de l'hôte
    */
    public String getPath()
    {
        return path;
    }

     /**
    * permet de définir le nom de l'hôte.
    * @param pName
    */
    public void setName(String pName)
    {
        this.name=pName;
    }

     /**
    * permet de définir le chemin de l'hôte.
    * @param pPath
    */
    public void setPath(String pPath)
    {
        this.path=pPath;
    }

    @Override
    public final String toString() {
        return name;
    }


}
