package http;

import java.util.Scanner;

/**
 * Console de gestion du server.
 * @author Marine Nogier, Pourquier Pierre-Emmanuel
 * @version 1.0
 */
public class Console implements Runnable {

    /**
     * Fil d'exécution de la console.
     */
    private final Thread thread;
    /**
     * Le serveur à gérer.
     */
    private final Dispatcher serveur;
    /**
     * Temps de sleep du thread.
     */
    private final int tempPause = 100;

    /**
     * Constructeur de la classe console.
     * @param pServeur Le serveur à gérer
     */
    public Console(final Dispatcher pServeur) {
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
            Http.syslog.error("Mise en pause impossible" + ex.getMessage());
        }
        String commande;
        System.out.println(
                "tapez /? pour une liste des commandes supportées");
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
                        System.out.println(
                                "Ce que vous venez de saisir "
                                        + "n'est pas une commande valide ! "
                                        + "Veuillez réessayer en saisissant "
                                        + "une de ces commandes !");
                        lsCommande();
                        break;
                }
            }

        } catch (Exception e) {
            Http.syslog.error("Err99 - scanner non fonctionnel "
                    + e.getMessage());
        }
    }

    /**.
     * liste les commandes disponible sur le serveur
     */
    private void lsCommande() {
        System.out.println("/? : Liste des commandes");
        System.out.println("/getHost : Liste des hosts du système");
        System.out.println("/addHost : Créer un host");
        System.out.println("/rmHost : Supprimer un host");
        System.out.println("/getPort : Affiche le port d'écoute du serveur");
        System.out.println("/setPort : Modifier le port d'écoute du serveur");
        System.out.println("/getThread : Affiche le nombre de thread");
        System.out.println("/setThread : Modifier le nombre de thread");
        System.out.println("/quit : Quitter le serveur");
        System.out.println("/author : Auteurs de l'application");
    }

    /**.
     * liste les hosts du système
     */
    private void listerHost() {
        try {
            System.out.println("Voici les hosts déjà connus :");
            if (Dispatcher.getHosts().getHostList() != null
                    && Dispatcher.getHosts().getHostList().size() > 0) {
                int i;

                System.out.println("Il y a actuellement "
                        + Dispatcher.getHosts().getHostList().size()
                        + " host(s) dans le système.\n");
                int size = Dispatcher.getHosts().getHostList().size();
                for (i = 0; i < size; i++) {
                    System.out.println(i
                        + ": Nom : "
                        + Dispatcher.getHosts().getHostList().get(i).getName()
                        + " - Chemin : "
                        + Dispatcher.getHosts().getHostList().get(i).getPath()
                        + "\n");
                }
            } else {
                System.out.println("Il n'y a aucun host sur le système");
            }
        } catch (Exception e) {
            Http.syslog.error(e.toString());
        }
    }

    /**.
     * Ajoute un host sur le système
     */
    private void ajouterHost() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Veuillez saisir le nom du host à ajouter");
            String nomHost = sc.nextLine();
            System.out.println("Le nom du host que vous aller créer est : "
                    + nomHost + "\n");
            System.out.println("Veuillez saisir le chemin du host");
            String pathHost = sc.nextLine();
            System.out.println("Le chemin du host que vous aller créer est : "
                    + pathHost
                    + "\n");

            Host host = new Host(nomHost, pathHost);
            Dispatcher.getHosts().addHost(host);

            if (Dispatcher.getHosts().getHostList().size() > 0) {
                System.out.println("Le host "
                        + nomHost
                        + " qui a comme chemin "
                        + pathHost
                        + " a bien été ajouté \n ");
            }
        } catch (Exception e) {
            Http.syslog.error("Le lecteur de la console ne fonctionne pas");
            Http.syslog.error("L'ajout du host n'a donc pas fonctionné");
        }
    }

    /**.
     * Supprime un host sur le système
     */
    private void supprimerHost() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Quel host souhaitez-vous supprimer :"
                    + " taper son nom ");
            String nomHost = sc.nextLine();

            // on recherche si le nom saisi appartient bien a un host
            Host host = Dispatcher.getHosts().getHost(nomHost);
            if (host != null) {
                System.out.println("\nEst ce que vous êtes sur "
                        + "de vouloir supprimer le host "
                        + nomHost + " ? o/n ");
                switch (sc.nextLine()) {
                    case "o":
                        // si le systeme a bien un host pour le nom saisi
                        //on supprime le host
                        Dispatcher.getHosts().removeHost(host);
                        System.out.println("Le host "
                                + nomHost
                                + " a bien été supprimé \n");
                        break;
                    case "n":
                        System.out.println("Suppression du host "
                                + nomHost
                                + " a été annulée \n");
                        break;
                    default:
                        System.out.println("Ce caractère n'est pas "
                                + "pris en compte ! "
                                + "Veuillez recommencez !  \n");
                        supprimerHost();
                        break;
                }
            } else {
                System.out.println("Le nom que vous avez saisi ne correspond "
                        + "à aucun des hosts du système \n");
                supprimerHost();
            }
        } catch (Exception e) {
            Http.syslog.error("La lecture de la console ne fonctionne pas");
        }
    }

    /**.
     * Affiche le port d'écoute du système
     */
    private void listerPort() {
        try {
            int port = serveur.getPort();
            System.out.println("Actuellement le serveur est connecté "
                    + "sur le port d'écoute "
                    + port
                    + "\n");
        } catch (Exception e) {
            Http.syslog.error("Récupération du port d'écoute impossible");
        }
    }

    /**
     * Modifie le port d'écoute du système.
     */
    private void modifierPort() {
        Scanner sc = new Scanner(System.in);
        try {
            int portActuel = Http.getConfig().getPORT();
            System.out.println("Le port d'écoute du serveur "
                    + "est actuellement le "
                    + portActuel);
            System.out.println("Sur quel port d'écoute voulez vous connecté "
                    + "le serveur pour les connexions futures ? ");
            String port = sc.nextLine();

            Http.getConfig().setPort(Integer.parseInt(port));
            System.out.println("Le nouveau port d'écoute est " + port);
        } catch (NumberFormatException e) {
            Http.syslog.error("Err261 - Port non sauvegardé "
                    + e.getMessage());
        }
    }

    /**.
     * Afficher le nombre de thread en marche sur le systeme
     */
    private void listerThread() {
        try {
            int poolThread = serveur.getPoolThread();
            System.out.println("Actuellement il y a "
                    + poolThread
                    + " thread de démarrés sur le serveur. \n");
        } catch (Exception e) {
            Http.syslog.error("Err276 - " + e.getMessage());
        }
    }

    /**.
     * Modifier les threads en marche sur le systeme
     */
    private void modifierThread() {
        Scanner sc = new Scanner(System.in);
        try {
            int nbThreadActuel = Http.getConfig().getPoolThread();
            System.out.println("Le nombre de thread qui se démarre "
                    + "sur le serveur actuellement est de "
                    + nbThreadActuel);
            System.out.println("Combien voulez vous démarré de thread "
                    + "pour les connexions futures ? ");
            String nbThread = sc.nextLine();

            Http.getConfig().setPoolThread(Integer.parseInt(nbThread));
            System.out.println("Le nombre de thread qui se démarrera "
                    + "à la connexion du serveur sera dorénavant de "
                    + nbThread
                    + " : enregistrement dans la configuration OK !");
        } catch (NumberFormatException e) {
            Http.syslog.error("Err300 - " + e.getMessage());
        }
    }

    /**.
     * Afficher les auteurs de l'application
     */
    private void lsAuthor() {
        System.out.println("Author :"
                + "\nPinatel Morgan"
                + "\nTruchard Hervé"
                + "\nPourquier Pierre-Emmanuel"
                + "\nPierrot Benjamin"
                + "\nNogier Marine");
    }

    /**
     * Démmarre le serveur.
     */
    private void start() {
        try {
            if (!serveur.isOnline()) {
                serveur.start();
                System.out.println("Le serveur vient d'être démarré");
            } else {
                System.out.println("Le serveur est déjà démarré");
            }
        } catch (Exception e) {
            Http.syslog.error("Err328 - " + e.getMessage());
        }
    }

    /**
     * Redémarre le serveur.
     */
    private void restart() {
        try {
            serveur.stop();
            serveur.start();
        } catch (Exception e) {
            Http.syslog.error("Err340 - Le redémarrage du serveur "
                    + "n'a pas fonctionné" + e.getMessage());
        }
    }

    /**.
     * Stoppé le serveur
     */
    private void stop() {
        try {
            if (serveur != null) {
                serveur.stop();
                System.out.println("Le système est bien arrêté");
            } else {
                System.out.println("Le système est déjà arrêté");
            }
        } catch (Exception e) {
            Http.syslog.error("Err 357 - Stop serveur n'a pas fonctionné "
                + e.getMessage());
        }
    }
}
