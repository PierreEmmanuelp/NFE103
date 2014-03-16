package http;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * représente le processus serveur qui attends les connexion sur le port PORT
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
 *
 */
public class Serveur {
	int PORT = 8080; //port d'ecoute du serveur
        int poolThread = 50;
        ServerSocket servSocket;
	ArrayList<Client> clients;//tableau des clients en cours de connexion
        Hosts hosts;
	
	public Serveur(){  
            clients = new ArrayList();
            
            // récupération de la configuration depuis le fichier XML
            this.PORT = http.Http.config.getPORT();
            this.poolThread = http.Http.config.getPoolThread(); 
            hosts = http.Http.config.getHosts();
            
            try {
                 servSocket = new ServerSocket(PORT);//creation de la socket
            } catch (IOException ex) {
                Log.ajouterEntree("impossible de créer le socket"+ex.getMessage(), LogLevel.ERROR);
                System.out.println("Erreur interne : socket indisponible");
            }
            int i;
            for(i=0;i<=poolThread;i++){//creation du pool de thread
                Client client = new Client();
                clients.add(client);              
            }
            Log.ajouterEntree("pool de thread créé",LogLevel.SYSTEM);
	}
        
        
	/**
	 * démarre le mode écoute du serveur
	 */
	public void start(){
		try {
                    Log.ajouterEntree("serveur en ligne",LogLevel.SYSTEM);
                    System.out.println("http server online");
                    while (true){ // attente en boucle d'une connexion
                        Client client = this.getFreeClient(); // un client se connecte, on le renvoit sur un thread libre
                        client.traiteRequete(servSocket.accept());
                    }
			 
                } catch (IOException e) {
                    System.out.println("Serveur indisponible : socket libre? ");
                    Log.ajouterEntree("erreur impossible de démarrer le serveur : socket indisponible"+e.getMessage(),LogLevel.SYSTEM);
		}			
        }
        
        /**
         * arrête le serveur
         */
        public void stop(){
            try {
                this.servSocket.close();
                Log.ajouterEntree("extinction du serveur",LogLevel.SYSTEM);
            } catch (IOException ex) {
                Log.ajouterEntree("Impossible d'éteindre le serveur",LogLevel.SYSTEM);
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
        
        public Hosts getHost(){
            
            return hosts;
        }
        
        public int getPoolThread()
        {
            return poolThread;
        }
}	

	
