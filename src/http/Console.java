package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Scanner;
//import org.apache.torque.util.Criteria;
//import org.apache.torque.TorqueException;

/**
 *
 * @author Marine Nogier
 * @version 1.0
 */
public class Console implements Runnable{

    Thread thread;
    BufferedReader consoleIn;
    Serveur serveur;
    Boolean logged = false;
    
    public Console(Serveur serveur){
            this.consoleIn = new BufferedReader(new InputStreamReader(System.in));
            thread = new Thread(this);
            thread.start();
            this.serveur = serveur;
            System.out.println("interpréteur de commande en attente");
            Log.ajouterEntree("commande ok", LogLevel.SYSTEM);
    }
        
    @Override
    public void run() {
        String commande;
        System.out.println("tapez /? pour une liste des commandes supportées \n \n");
	Scanner sconsole = new Scanner(System.in);
        try {
            while((commande=sconsole.nextLine()) !=null){
                switch (commande){
                    case "/?":lsCommande();break;
                    case "/getHost":listerHost();break;//ok
                    case "/addHost":ajouterHost();break;
                    case "/rmHost":supprimerHost();break;
                    case "/getPort" : listerPort(); break;
                    case "/setPort" : modifierPort(); break;
                    case "/getThread" : listerThread(); break;
                    case "/setThread" : modifierThread(); break;
                    case "/author":lsAuthor();break;//ok
                    case "/quit":quit();break;//ok
                    case "/start" : start();break;
                    case "/stop" : stop();break;
                    case "/restart" : restart();break;
                    default:
                        System.out.println("Ce que vous venez de saisir n'est pas une commande valide ! Veuillez réessayer en saisissant une de ces commandes ! \n");
                        lsCommande();
                    break;
                }
            }
		
        } catch (Exception e) {
            e.printStackTrace();
            Log.ajouterEntree("Le lecteur de la console ne fonctionne pas", LogLevel.SYSTEM);
        }        
    }
        
    /**
     * liste les commandes disponible sur le serveur
     */
    private void lsCommande() {
        System.out.println("/? : Liste des commandes");
        System.out.println("/getHost : Liste des hosts du système");
        System.out.println("/addHost : Créer un host");
        System.out.println("/rmHost : Supprimer un host");
        System.out.println("/getPort : Affiche le port d'écoute du serveur");
        System.out.println("/setPort : Modifier le port d'écoute du serveur");
        System.out.println("/getThread : Affiche le nombre de thread démarré");
        System.out.println("/setThread : Modifier le nombre de thread démarré");
        System.out.println("/quit : Quitter le serveur");
        System.out.println("/author : Auteurs de l'application");
    }
        
    /**
     * liste les hosts du système
     */
    private void listerHost() {
        try{
            System.out.println("Voici les hosts déjà connus :");
            if(serveur.getHost().getHostList() != null && serveur.getHost().getHostList().size()>0)
            {
                int i;
                
                System.out.println("Il y a actuellement " + serveur.getHost().getHostList().size() + " host(s) dans le système.\n");
                
                for(i=0;i< serveur.getHost().getHostList().size();i++){
                        System.out.println(i + ": Nom : " + serveur.getHost().getHostList().get(i).getName() + " - Chemin : " + serveur.getHost().getHostList().get(i).getPath() + "\n");
                }
            }
            else
            {
                System.out.println("Il n'y a aucun host sur le système");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.ajouterEntree(e.toString(), LogLevel.SYSTEM);
        }
    }
    
    /**
     * Ajoute un host sur le système
     */
    private void ajouterHost(){
        Scanner sc = new Scanner(System.in);
        try
        {
            System.out.println("Veuillez saisir le nom du host à ajouter");
            String nomHost = sc.nextLine();
            System.out.println("Le nom du host que vous aller créer est : " + nomHost + "\n");
            System.out.println("Veuillez saisir le chemin du host");
            String pathHost = sc.nextLine();
            System.out.println("Le chemin du host que vous aller créer est : " + pathHost + "\n");

            Host host = new Host(nomHost, pathHost);
            serveur.getHost().addHost(host);
            
            if(serveur.getHost().getHostList().size() >0)
            {
                System.out.println("Le host " + nomHost + " qui a comme chemin " + pathHost + " a bien été ajouté \n ");
            }
        }
        catch (Exception e ){
            e.printStackTrace();
            Log.ajouterEntree("Le lecteur de la console ne fonctionne pas", LogLevel.SYSTEM);
            Log.ajouterEntree("L'ajout du host n'a donc pas fonctionné", LogLevel.SYSTEM);
        } 
    }

    /**
     * Supprime un host sur le système
     */
    private void supprimerHost(){
	Scanner sc = new Scanner(System.in);
        try{ 
            System.out.println("Quel host souhaitez-vous supprimer : taper son nom ");
            String nomHost = sc.nextLine();
            
            // on recherche si le nom saisi appartient bien a un host
            Host host = serveur.getHost().getHost(nomHost);
            if(host != null)
            {
                System.out.println("\nEst ce que vous êtes sur de vouloir supprimer le host " + nomHost + " ? o/n ");
                switch (sc.nextLine()) {
                    case "o":
                        // si le systeme a bien un host correspondant au nom saisi
                        //on supprime le host
                        serveur.getHost().removeHost(host);
                        System.out.println("Le host " + nomHost + " a bien été supprimé \n");

                        break;
                    case "n":
                        System.out.println("Suppression du host " + nomHost + " a été annulée \n");
                        break;
                    default:
                        System.out.println("Ce caractère n'est pas pris en compte ! Veuillez recommencez !  \n");
                        supprimerHost();
                        break;
                }
            }
            else
            {
                System.out.println("Le nom que vous avez saisi ne correspond à aucun des hosts du système \n");
                supprimerHost();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.ajouterEntree("La lecture de la console ne fonctionne pas", LogLevel.SYSTEM);
        }
    }
    
    /**
     * Affiche le port d'écoute du système
     */
    private void listerPort()
    {
        try{
            int port = serveur.getPORT();
            System.out.println("Actuellement le serveur est connecté sur le port d'écoute " + port + "\n");
        }
        catch(Exception e){
            e.printStackTrace();
            Log.ajouterEntree("Récupération du port d'écoute impossible", LogLevel.ERROR);
        }
    }
    
    /**
     * Modifier le port d'écoute du système
     */
    private void modifierPort()
    {
        Scanner sc = new Scanner(System.in);
        try{
            int portActuel = Http.config.getPORT();
            System.out.println("Le port d'écoute du serveur est actuellement le " + portActuel );
            System.out.println("Sur quel port d'écoute voulez vous connecté le serveur pour les connexions futures ? ");
            String port = sc.nextLine();
            
            Http.config.setPort(Integer.parseInt(port));
            System.out.println("Le nouveau port d'écoute est " + port + " : enregistrement dans la configuration OK !");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.ajouterEntree("L'enregistrement du nouveau port d'écoute n'as pas fonctionné", LogLevel.SYSTEM);
        }
    }
    
    
    /**
     * Afficher le nombre de thread en marche sur le systeme
     */
    private void listerThread()
    {
        try{
            int poolThread = serveur.getPoolThread();
            System.out.println("Actuellement il y a " + poolThread + " thread de démarrés sur le serveur. \n");
        }
        catch(Exception e){
            e.printStackTrace();
            Log.ajouterEntree("Récupération du nombre de thread démarré sur le serveur n'a pas fonctionné", LogLevel.ERROR);
        }
    }
    
    /**
     * Modifier les threads en marche sur le systeme
     */
    private void modifierThread()
    {
        Scanner sc = new Scanner(System.in);
        try{
            int nbThreadActuel = Http.config.getPoolThread();
            System.out.println("Le nombre de thread qui se démarre sur le serveur actuellement est de " + nbThreadActuel );
            System.out.println("Combien voulez vous démarré de thread pour les connexions futures ? ");
            String nbThread = sc.nextLine();
            
            Http.config.setPoolThread(Integer.parseInt(nbThread));
            System.out.println("Le nombre de thread qui se démarrera à la connexion du serveur sera dorénavant de " + nbThread + " : enregistrement dans la configuration OK !");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.ajouterEntree("L'enregistrement du nouveau nombre de thread à démarré n'as pas fonctionné", LogLevel.SYSTEM);
        }
    }
    
    
    private void quit(){
        System.out.println("deconnexion");
        Log.ajouterEntree("serveur quit", LogLevel.SYSTEM);
        Log.terminerSessionLog();
        System.exit(0);
    }

    /**
     * Afficher les auteurs de l'application
     */
    private void lsAuthor(){
	System.out.println("Author :\nPinatel Morgan\nTruchard Hervé\nPourquier Pierre-Emmanuel\nPierrot Benjamin\nNogier Marine ");
    }
    
    /**
     * Démmaré le serveur
     */
    private void start()
    {
        try{
            if(serveur == null)
            {
               serveur.start(); 
               System.out.println("Le serveur vient d'être démarré");
            }
            else
            {
                System.out.println("Le serveur est déjà démarré");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.ajouterEntree("La connexion du serveur n'a pas fonctionné", LogLevel.SYSTEM);
        }
    }
    
    /**
     * Redémarré le serveur
     */
    private void restart()
    {
        try{
            
            serveur.stop();
            serveur.start();
        }
        catch(Exception e){
            e.printStackTrace();
            Log.ajouterEntree("Le redémarrage du serveur n'a pas fonctionné", LogLevel.SYSTEM);
        }
    }
    
    /**
     * Stoppé le serveur
     */
    private void stop()
    {
        try{
            if(serveur != null)
            {
                serveur.stop();
                System.out.println("Le système est bien arrêté");
            }
            else
            {
                System.out.println("Le système est déjà arrêté");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.ajouterEntree("L'arrêt du serveur n'a pas fonctionné", LogLevel.SYSTEM);
        }
    }
}
