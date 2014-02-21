package http;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Représente un client et réagit au requêtes reçues par celui-ci
 * @author Pourquier Pierre-Emmanuel
 * @version 1.0
 */
public class Client implements Runnable{
	private Thread thread; // contiendra le thread du client
	private Socket socket; // recevra le socket liant au client
	private DataOutputStream out = null;//écrit dans le socket
	private BufferedReader in;//lit le socket
        private Boolean free = true;//si le socket est occupé


    /**
     * créer le client dans le système pour répondre à un requete http
     * @param serveur serveur http
     */
        public Client(){
            thread = new Thread(this);
        }
	
        public void traiteRequete(Socket socket){
            this.socket = socket;
		thread = new Thread(this);//Création du thread dédié à ce client
		try {//création des reader et writer
			in = new BufferedReader( new InputStreamReader(this.socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.getMessage();
		}
                this.free = false;
		thread.start();//démarrage du thread
        }
        
	/**
	 * démarre le thread
	 */
	public void run() {
            String message = null;
            try {
                while((message=in.readLine())!=null && message.length() > 0){
                        System.out.println(message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            envoyer("Status: HTTP/1.1 200 OK \n\r<br> Hello World !");
            try {
                out.close();
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            thread.stop();
            this.free = true;
	}
	
	/**
	 * analyse la requête reçue sur le socket.
	 * @param requete requête reçue sur le socket
	 */
	private void analyse(String requete){
		
	}
	
	/**
	 * Envoie un message sur le socket du client
	 * @param data texte a envoyer
	 */
	protected void envoyer(String data){
            try {
                out.writeChars("\n\r " + data + " \n\r");
                out.flush();
                System.out.println("envoyé : " + data);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
		
	/**
	 * Lit les données dans la socket
	 * @return le message reçu sur la socket
	 * @throws IOException 
	 */
	protected String recevoir() throws IOException{
		String message=null;
		message = in.readLine();
		return message;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}
        
        /**
         * 
         * @return if the socket is free
         */
        public Boolean getFree(){
            return this.free;
        }

        @Override
	public String toString(){
		return this.socket.getInetAddress().toString();
	}

}
