package http;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Collections d'host.
 * @author Morgan Pinatel, Pierre-Emmanuel Pourquier
 * @version 1.0
 */
public class Hosts {

    /**
     * tableau d'host.
     */
    private final ArrayList<Host> hostList;

    /**
     * contructeur de la classe.
     */
    public Hosts() {
        this.hostList = new ArrayList();
    }

    /**
     * permet d'ajouter un hôte dans la liste s'il n'existe pas déjà.
     * @param pHost host à ajouter.
     */
    public final void addHost(final Host pHost) {
        if (!this.existe(pHost)) {
            this.hostList.add(pHost);
        }
    }

    /**
     * permet de supprimer un hôte dans la liste.
     * @param phost host à supprimer.
     */
    public final void removeHost(final Host phost) {
        this.hostList.remove(phost);
    }

    /**
     * permet de récupérer un hôte avec son nom.
     * @param pName nom de l'hote recherché.
     * @return l'hôte recherché.
     */
    public final Host getHost(final String pName) {
        Iterator<Host> i = hostList.iterator();
        while (i.hasNext()) {
            Host host = (Host) i.next();
            if (host.getName().equals(pName)) {
                return host;
            }
        }
        return null;
    }

    /**
     * permet de récupérer le chemin d'un hôte avec son nom.
     * @param pName nom de l'hote recherché.
     * @return le chemin de l'hôte.
     */
    public final String getPath(final String pName) {
        Iterator<Host> i = hostList.iterator();
        while (i.hasNext()) {
            Host host = (Host) i.next();
            if (host.getName().matches(pName)) {
                return host.getPath();
            }
        }
        return null;
    }

    /**
     * permet de vérifier l'existence du nom d'un hôte dans les paramètres.
     * @param pHost hote recherché.
     * @return existe ou non.
     */
    public final boolean existe(final Host pHost) {
        boolean existe;
        existe = false;
        Iterator<Host> i = this.getHostList().iterator();
        while (i.hasNext()) {
            Host host = (Host) i.next();
            if (pHost.getName().equals(host.getName())) {
                existe = true;
            }
        }
        return existe;
    }

    /**
     * permet de récupérer la liste des hôtes.
     * @return la liste des hôtes.
     */
    public final List<Host> getHostList() {
        return this.hostList;
    }

}
