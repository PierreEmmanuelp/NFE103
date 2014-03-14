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
	int PORT; //port d'ecoute du serveur
        int poolThread;
        ServerSocket servSocket;
	ArrayList<Client> clients;//tableau des clients en cours de connexion
        Hosts hosts;
	
	public Serveur(){
            this.PORT = http.Http.config.getPORT();
            this.poolThread = http.Http.config.getPoolThread();
            
            clients = new ArrayList();
            hosts = new Hosts();
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
                    e.printStackTrace();
                    Log.ajouterEntree("erreur impossible de démarrer le serveur",LogLevel.SYSTEM);
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
        
        public Hosts getHost(){
            
            return hosts;
        }
}	

	
