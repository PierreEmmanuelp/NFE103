package http;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;


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
     */
    public Client(){
        thread = new Thread(this);
    }
	
    /**
     * Si le client est libre, il peut traité un message arrivé sur le socket serveur
     * @param psocket le socket serveur
     */
    public void traiteRequete(Socket psocket){
        this.free = false;// le client va être occupé
        this.socket = psocket;
        thread = new Thread(this);//Création du thread dédié à ce client
        try {//création des reader et writer
            in = new BufferedReader( new InputStreamReader(this.socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
             e.getMessage();
             Log.ajouterEntree("Impossible de créer le reader et le writter dans client.java");
        }   
        thread.start();//démarrage du thread
    }

    /**
     * démarre le thread
     */
        @Override
    public void run() {
        String ligneSocket=null; // recevra ligne à ligne les information du socket
        ArrayList<String> header=new ArrayList(); // recevra l'ensemble de la requete du socket correspondant au header
        String content; //recevra le content de la requete 
        Request requete; // requete http 
        Response reponse; // reponse http (qui dépend de la requete)
        try {
            while((ligneSocket=in.readLine())!=null && ligneSocket.length()>0){
                header.add(ligneSocket);
            }
            System.out.println("fin header");
            requete = new Request(header);
            if(requete.besoinContent()==true){ 
                ligneSocket=in.readLine();
                content=(ligneSocket);
            }
        } catch (IOException ex) {
            Log.ajouterEntree("impossible de lire le socket dans client.java");
        }
        
        //si tout s'est bien passé header contient l'ensemble des données reçues. Vérifions puis parsons :
     
            
    
        envoyer("Status: HTTP/1.1 200 OK \n\r<br> Hello World !<br> votre request était : "+header);
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
     * Envoie un message sur le socket du client
     * @param data texte a envoyer
     */
    protected void envoyer(String data){
        try {
            out.writeChars(data);
            out.flush();
            //System.out.println("envoyé : " + data);TODO delete
        } catch (IOException ex) {
            ex.printStackTrace();
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
            return this.socket;
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
