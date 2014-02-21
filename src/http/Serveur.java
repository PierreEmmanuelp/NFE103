package http;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * représente le processus serveur qui attends les connexion sur le port 80
 * @author Pierre-Emmanuel Pourquier
 * @version 13.7.3
 *
 */
public class Serveur {
	int PORT = 6666; //port d'ecoute du serveur
        int poolThread = 50;
        ServerSocket servSocket;
	ArrayList<Client> clients;//tableau des clients en cours de connexion
	
	public Serveur(){
            clients = new ArrayList();
            try {
                 servSocket = new ServerSocket(PORT);//creation de la socket
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            int i;
            for(i=0;i<=poolThread;i++){//creation du pool de thread
                Client client = new Client();
                clients.add(client);              
            }
            Log.ajouterEntree("pool de thread créé");
	}
        
        
	/**
	 * démarre le mode écoute du serveur
	 */
	public void start(){
		try {
                    Log.ajouterEntree("serveur en ligne");
                    System.out.println("http server online");
                    while (true){ // attente en boucle d'une connexion
                        Client client = this.getFreeClient(); // un client se connecte, on le renvoit sur un thread libre
                        client.traiteRequete(servSocket.accept());
                    }
			 
                } catch (IOException e) {
                    System.out.println("Serveur indisponible : socket libre? ");
                    e.printStackTrace();
                    Log.ajouterEntree("erreur impossible de démarrer le serveur");
		}			
        }
        
        /**
         * arrête le serveur
         */
        public void stop(){
            try {
                this.servSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        
        /**
         *  return le premier thread disponible
         * @return 
         */
        private Client getFreeClient(){
            int i = 0;
            boolean traite = false;
            while(traite)
            for(i=0;i<=poolThread; i++){
                if(clients.get(i).getFree()==true){
                    
                    traite=true;
                }
            }
            return clients.get(i);
        }

	
	/**
	 * @return le port utilisé par le serveur
	 */
	public int getPORT() {
		return PORT;
	}
}	
	
