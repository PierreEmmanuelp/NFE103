package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Scanner;
//import org.apache.torque.util.Criteria;
//import org.apache.torque.TorqueException;

/**
 *
 * @author Pierre-Emmanuel Pourquier
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
        System.out.println("tapez /? pour une liste des commandes supportées");
	Scanner sconsole = new Scanner(System.in);
	while(logged==false){
            connexion();
        try {
			while((commande=sconsole.nextLine()) !=null && logged==true){
/*				switch (commande){
					case "/quit":quit();break;//ok
					case "/ls":listerHost();break;//ok
					case "/mk":ajouterHost();break;
					case "/del":supprimerHost();break;
					case "/?":lsCommande();break;
					case "/author":lsAuthor();break;//ok
					case "/logout":deconnexion();break;// ok
					default:lsCommande();break;
				}*/
			}
		
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
        
    }
        
    private void connexion(){
            System.out.println("rentrez votre login");
            Scanner sc = new Scanner(System.in);
            String login = sc.nextLine();
            System.out.println("rentrez votre password");
            String password = sc.nextLine();
            //Criteria cr = new Criteria();
//            cr.add("utilisateur","login",login);
//            cr.add("utilisateur","password",password);
//            cr.add("utilisateur","admin",true);
            //try {
//                    if(UtilisateurPeer.doSelect(cr).isEmpty()==false){
//                            logged = true;
//                            System.out.println("Bienvenue, "+ UtilisateurPeer.doSelect(cr).get(0).getPrenom());
//                            Log.ajouterEntree("login admin de "+login);
//                    }
            //} 
            //catch (TorqueException e1) {
//                    Log.ajouterEntree("Problème avec Torque");
            }
   // }
    
    /**
     * liste les commandes disponible sur le serveur
     */
    private void lsCommande() {
            System.out.println("/quit : quitte le serveur");
            System.out.println("/ls : liste les host");
            System.out.println("/mk : créer un host");
            System.out.println("/del : supprime un host");
            System.out.println("/? liste les commandes");
            System.out.println("/author crédits de l'application");
            System.out.println("/logout : déconnecte l'amdin de la console");
    }
        
    /**
     * Ajoute un host
     */
    private void ajouterHost(){
            Scanner sc = new Scanner(System.in);

//            try{
//		Vol vol = new Vol();
//		System.out.println("code aita de départ?");
//		String aitadpt = sc.nextLine();
//		System.out.println("code aita d'arrivée?");
//		String aitaarr = sc.nextLine();
//		Date dateD = null;
//		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//		System.out.println("Date de départ : jj-MM-aaaa hh:mm (ex : 29-07-2013 08:45)");
//		String dateDpt = sc.nextLine();
//		dateD = formater.parse(dateDpt);	
//		vol.setDateDepart(dateD);
//		vol.setFkAeroportArrive(aitaarr);
//		vol.setFkAeroportArrive(aitadpt);
//		VolPeer.doInsert(vol);
//		
//		System.out.println("vol créé. paramétrage de l'enchère");
//		System.out.println("prix de réserve :");
//		String prixReserve = sc.nextLine();//TODO try catch peut-etre interressant pour tester la conversion !
//		float prixRes = Float.parseFloat(prixReserve);//conversion en double si possible
//		System.out.println("Date de début de l'enchere : jj-MM-aaaa hh:mm (ex : 29-07-2013 08:45)");
//		String dateDebut = sc.nextLine();
//		Date dateDeb=null;
//		dateDeb = formater.parse(dateDpt);//conversion de string vers date
//		
//		Enchere enchere = new Enchere();
//		enchere.setFkVol(vol.getIdVol());
//		enchere.setDateDebutEnchere(dateDeb);
//		enchere.setPrixReserve(prixRes);
//		enchere.setPrixAtteint(1);
           // }
//            catch (TorqueException e) {
                    // TODO Auto-generated catch block
              //      e.printStackTrace();
            //}

    }

    private void deconnexion(){
            this.logged=false;
    }
       
    private void quit(){
		System.out.println("deconnexion");
		Log.ajouterEntree("serveur quit", LogLevel.SYSTEM);
		Log.terminerSessionLog();
		System.exit(0);
	}

    private void listerHost() {
        System.out.println("Voici les hosts déjà connus :");
		int i = 0;
		System.out.println("Il y a actuellement " //+serveur.getHost().size()+
                        +" encheres dans le système.");
//		for(i=0;i<serveur.getHost().size();i++){
//			System.out.println(serveur.getHost()..get(i));
//		}
    }
    
    private void supprimerHost(){
	System.out.println("Quel host souhaitez-vous supprimer : \n");
    }
    
    private void lsAuthor(){
	System.out.println("Author :\nPinatel Morgan\nTruchard Hervé\nPourquier Pierre-Emmanuel\nPierrot Benjamin\nNogier Marine ");
    }
}
