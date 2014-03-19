package http;

import debug.Trace;
import log.Log;
import log.LogLevel;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marine Nogier
 * @version 1.0
 */
public class Console implements Runnable {

    /**.
     *
     * Le thread
     */
    private final Thread thread;
    /**.
     * Le serveur
     */
    private final Serveur serveur;
    /**.
     *
     * Temps de sleep du thread
     */
    private final int tempPause = 100;

    /**.
     *
     * Constructeur de la classe console
     * @param pServeur
     *      Le serveur
     */
    public Console(final Serveur pServeur) {
        thread = new Thread(this);
        thread.start();
        this.serveur = pServeur;
        Http.syslog.info("Console en attente d'intructions");
    }

    @Override
    public final void run() {
        try {
            Thread.sleep(tempPause);
        } catch (InterruptedException ex) {
            Logger.getLogger(
                    Console.class.getName()).log(Level.SEVERE, null, ex);
        }
        String commande;
        Trace.trace(
                "tapez /? pour une liste des commandes supportées \n");
        Scanner sconsole;
        sconsole = new Scanner(System.in);
        try {
            while ((commande = sconsole.nextLine()) != null) {
                switch (commande) {
                    case "/?":
                        lsCommande();
                        break;
                    case "/getHost":
                        listerHost();
                        break;
                    case "/addHost":
                        ajouterHost();
                        break;
                    case "/rmHost":
                        supprimerHost();
                        break;
                    case "/getPort":
                        listerPort();
                        break;
                    case "/setPort":
                        modifierPort();
                        break;
                    case "/getThread":
                        listerThread();
                        break;
                    case "/setThread":
                        modifierThread();
                        break;
                    case "/author":
                        lsAuthor();
                        break;
                    case "/start":
                        start();
                        break;
                    case "/stop":
                        stop();
                        break;
                    case "/restart":
                        restart();
                        break;
                    default:
                        Trace.trace(
                                "Ce que vous venez de saisir "
                                        + "n'est pas une commande valide ! "
                                        + "Veuillez réessayer en saisissant "
                                        + "une de ces commandes ! \n");
                        lsCommande();
                        break;
                }
            }

        } catch (Exception e) {
            Log.ajouterEntree(
                    "Le lecteur de la console ne fonctionne pas",
                    LogLevel.SYSTEM);
        }
    }

    /**.
     * liste les commandes disponible sur le serveur
     */
    private void lsCommande() {
        Trace.trace("/? : Liste des commandes");
        Trace.trace("/getHost : Liste des hosts du système");
        Trace.trace("/addHost : Créer un host");
        Trace.trace("/rmHost : Supprimer un host");
        Trace.trace("/getPort : Affiche le port d'écoute du serveur");
        Trace.trace("/setPort : Modifier le port d'écoute du serveur");
        Trace.trace("/getThread : Affiche le nombre de thread démarré");
        Trace.trace("/setThread : Modifier le nombre de thread démarré");
        Trace.trace("/quit : Quitter le serveur");
        Trace.trace("/author : Auteurs de l'application");
    }

    /**.
     * liste les hosts du système
     */
    private void listerHost() {
        try {
            Trace.trace("Voici les hosts déjà connus :");
            if (Serveur.getHost().getHostList() != null
                    && Serveur.getHost().getHostList().size() > 0) {
                int i;

                Trace.trace("Il y a actuellement "
                        + Serveur.getHost().getHostList().size()
                        + " host(s) dans le système.\n");

                for (i = 0; i < Serveur.getHost().getHostList().size(); i++) {
                    Trace.trace(i
                            + ": Nom : "
                            + Serveur.getHost().getHostList().get(i).getName()
                            + " - Chemin : "
                            + Serveur.getHost().getHostList().get(i).getPath()
                            + "\n");
                }
            } else {
                Trace.trace("Il n'y a aucun host sur le système");
            }
        } catch (Exception e) {
            Log.ajouterEntree(e.toString(), LogLevel.SYSTEM);
        }
    }

    /**.
     * Ajoute un host sur le système
     */
    private void ajouterHost() {
        Scanner sc = new Scanner(System.in);
        try {
            Trace.trace("Veuillez saisir le nom du host à ajouter");
            String nomHost = sc.nextLine();
            Trace.trace("Le nom du host que vous aller créer est : "
                    + nomHost + "\n");
            Trace.trace("Veuillez saisir le chemin du host");
            String pathHost = sc.nextLine();
            Trace.trace("Le chemin du host que vous aller créer est : "
                    + pathHost
                    + "\n");

            Host host = new Host(nomHost, pathHost);
            Serveur.getHost().addHost(host);

            if (Serveur.getHost().getHostList().size() > 0) {
                Trace.trace("Le host "
                        + nomHost
                        + " qui a comme chemin "
                        + pathHost
                        + " a bien été ajouté \n ");
            }
        } catch (Exception e) {
            Log.ajouterEntree("Le lecteur de la console ne fonctionne pas",
                    LogLevel.SYSTEM);
            Log.ajouterEntree("L'ajout du host n'a donc pas fonctionné",
                    LogLevel.SYSTEM);
        }
    }

    /**.
     * Supprime un host sur le système
     */
    private void supprimerHost() {
        Scanner sc = new Scanner(System.in);
        try {
            Trace.trace("Quel host souhaitez-vous supprimer : taper son nom ");
            String nomHost = sc.nextLine();

            // on recherche si le nom saisi appartient bien a un host
            Host host = Serveur.getHost().getHost(nomHost);
            if (host != null) {
                Trace.trace("\nEst ce que vous êtes sur "
                        + "de vouloir supprimer le host "
                        + nomHost + " ? o/n ");
                switch (sc.nextLine()) {
                    case "o":
                        // si le systeme a bien un host pour le nom saisi
                        //on supprime le host
                        Serveur.getHost().removeHost(host);
                        Trace.trace("Le host "
                                + nomHost
                                + " a bien été supprimé \n");
                        break;
                    case "n":
                        Trace.trace("Suppression du host "
                                + nomHost
                                + " a été annulée \n");
                        break;
                    default:
                        Trace.trace("Ce caractère n'est pas pris en compte ! "
                                + "Veuillez recommencez !  \n");
                        supprimerHost();
                        break;
                }
            } else {
                Trace.trace("Le nom que vous avez saisi ne correspond "
                        + "à aucun des hosts du système \n");
                supprimerHost();
            }
        } catch (Exception e) {
            Log.ajouterEntree("La lecture de la console ne fonctionne pas",
                    LogLevel.SYSTEM);
        }
    }

    /**.
     * Affiche le port d'écoute du système
     */
    private void listerPort() {
        try {
            int port = serveur.getPORT();
            Trace.trace("Actuellement le serveur est connecté "
                    + "sur le port d'écoute "
                    + port
                    + "\n");
        } catch (Exception e) {
            Log.ajouterEntree("Récupération du port d'écoute impossible",
                    LogLevel.ERROR);
        }
    }

    /**.
     * Modifier le port d'écoute du système
     */
    private void modifierPort() {
        Scanner sc = new Scanner(System.in);
        try {
            int portActuel = Http.getConfig().getPORT();
            Trace.trace("Le port d'écoute du serveur est actuellement le "
                    + portActuel);
            Trace.trace("Sur quel port d'écoute voulez vous connecté "
                    + "le serveur pour les connexions futures ? ");
            String port = sc.nextLine();

            Http.getConfig().setPort(Integer.parseInt(port));
            Trace.trace("Le nouveau port d'écoute est "
                    + port
                    + " : enregistrement dans la configuration OK !");
        } catch (NumberFormatException e) {
            Log.ajouterEntree(
                    "L'enregistrement du nouveau port "
                            + "d'écoute n'as pas fonctionné",
                    LogLevel.SYSTEM);
        }
    }

    /**.
     * Afficher le nombre de thread en marche sur le systeme
     */
    private void listerThread() {
        try {
            int poolThread = serveur.getPoolThread();
            Trace.trace("Actuellement il y a "
                    + poolThread
                    + " thread de démarrés sur le serveur. \n");
        } catch (Exception e) {
            Log.ajouterEntree("Récupération du nombre de thread "
                    + "démarré sur le serveur n'a pas fonctionné",
                    LogLevel.ERROR);
        }
    }

    /**.
     * Modifier les threads en marche sur le systeme
     */
    private void modifierThread() {
        Scanner sc = new Scanner(System.in);
        try {
            int nbThreadActuel = Http.getConfig().getPoolThread();
            Trace.trace("Le nombre de thread qui se démarre "
                    + "sur le serveur actuellement est de "
                    + nbThreadActuel);
            Trace.trace("Combien voulez vous démarré de thread "
                    + "pour les connexions futures ? ");
            String nbThread = sc.nextLine();

            Http.getConfig().setPoolThread(Integer.parseInt(nbThread));
            Trace.trace("Le nombre de thread qui se démarrera "
                    + "à la connexion du serveur sera dorénavant de "
                    + nbThread
                    + " : enregistrement dans la configuration OK !");
        } catch (NumberFormatException e) {
            Log.ajouterEntree("L'enregistrement du nouveau nombre "
                    + "de thread à démarré n'as pas fonctionné",
                    LogLevel.SYSTEM);
        }
    }

    /**.
     * Afficher les auteurs de l'application
     */
    private void lsAuthor() {
        Trace.trace("Author :"
                + "\nPinatel Morgan"
                + "\nTruchard Hervé"
                + "\nPourquier Pierre-Emmanuel"
                + "\nPierrot Benjamin"
                + "\nNogier Marine");
    }

    /**.
     * Démmaré le serveur
     */
    private void start() {
        try {
            if (serveur == null) {
                serveur.start();
                Trace.trace("Le serveur vient d'être démarré");
            } else {
                Trace.trace("Le serveur est déjà démarré");
            }
        } catch (Exception e) {
            Log.ajouterEntree("La connexion du serveur "
                    + "n'a pas fonctionné",
                    LogLevel.SYSTEM);
        }
    }

    /**.
     * Redémarré le serveur
     */
    private void restart() {
        try {
            serveur.stop();
            serveur.start();
        } catch (Exception e) {
            Log.ajouterEntree("Le redémarrage du serveur "
                    + "n'a pas fonctionné",
                    LogLevel.SYSTEM);
        }
    }

    /**.
     * Stoppé le serveur
     */
    private void stop() {
        try {
            if (serveur != null) {
                serveur.stop();
                Trace.trace("Le système est bien arrêté");
            } else {
                Trace.trace("Le système est déjà arrêté");
            }
        } catch (Exception e) {
            Log.ajouterEntree("L'arrêt du serveur n'a pas fonctionné",
                    LogLevel.SYSTEM);
        }
    }
}
