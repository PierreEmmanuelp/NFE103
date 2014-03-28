package http;

/**
 * représente un host sur le serveur http.
 * @author Morgan Pinatel, Pourquier Pierre-Emmanuel
 * @version 1.1
 */
public class Host {
    /**
     * Nom du Host.
     */
    private String name;
    /**
     * Chemin du Host.
     */
    private String path;

    /**
    * contructeur de la classe.
    * @param pName nom du host.
    * @param pPath chemin du host.
    */
    public Host(final String pName, final String pPath) {
        this.name = pName;
        this.path = pPath;
    }

    /**
    * permet de récupérer le nom de l'hôte.
    * @return le nom de l'hôte.
    */
    public final String getName() {
        return name;
    }

   /**
    * permet de récupérer le chemin de l'hôte.
    * @return le chemin de l'hôte.
    */
    public final String getPath() {
        return path;
    }

    /**
    * permet de définir le nom de l'hôte.
    * @param pName nom de l'hote.
    */
    public final void setName(final String pName) {
        this.name = pName;
    }

    /**
     * permet de définir le chemin de l'hôte.
     * @param pPath chemin de l'hote.
     */
    public final void setPath(final String pPath) {
        this.path = pPath;
    }

    @Override
    public final String toString() {
        return name;
    }


}
