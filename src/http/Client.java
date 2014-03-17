package http;
import debug.Trace;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

/**
 * Représente un client et réagit au requêtes reçues par celui-ci
 * @author Pourquier Pierre-Emmanuel
 * @version 2.0
 */
public class Client implements Runnable{
	private final Thread thread; // contiendra le thread du client
	private Socket socket; // recevra le socket liant au client
	private DataOutputStream out = null;//écrit dans le socket
	private BufferedReader in;//lit le socket
        private Boolean free = true;//si le socket est occupé

    /**
     * créer le client dans le système pour répondre à un requete http
     */
    public Client(){
        this.thread = new Thread(this);
        this.thread.start();
    }
	
    /**
     * Si le client est libre, il peut traité un message arrivé sur le socket serveur
     * @param psocket le socket serveur
     */
    public void traiteRequete(Socket psocket){
        this.free = false;// le client va être occupé
        this.socket = psocket;
   
    }

    /**
     * démarre le thread
     */
        @Override
    public void run() {
        String ligneSocket=null; // recevra ligne à ligne les information du socket
        ArrayList<String> header=new ArrayList(); // recevra l'ensemble de la requete du socket correspondant au header
        String strContent; //recevra le content de la requete 
        Request requete = null; // requete http 

        
        while(true){     
            Boolean attendre = true;  
            while(attendre){//position d'attente. Seule l'interrupt pourra réveiller le thread
                try {
                    this.free = true;//le thread est libre, il dort
                    //Trace.trace(this.thread.getName() +"a été endormi");//TODO SUPPR LA TRACE
                    this.thread.sleep(100000);
                } catch (InterruptedException ex) {
                    this.free=false;//le client devient occupé
                    break; // Sortie de la boucle infinie attendre
                }       
            }
            
                Trace.trace(this.thread.getName() +"a été réveillé");//TODO SUPPR LA TRACE
            //le thread est réveillé
                try {//création des reader et writer
                    in = new BufferedReader( new InputStreamReader(this.socket.getInputStream()));
                    out = new DataOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    Log.ajouterEntree("Impossible de créer le reader et le writter dans client.java" +  e.getMessage(),LogLevel.SYSTEM);
                   }
                try {//lecture dans le socket
                    while((ligneSocket=in.readLine())!=null && ligneSocket.length()>0){//lecture des lignes de header
                        header.add(ligneSocket);
                    }
                requete = new Request(header);
                if(requete.besoinContent()==true){ //lecture du content (en cas de post)
                    ligneSocket=in.readLine();
                    strContent=(ligneSocket);
                    requete.setContent(new Content(/*TODO : contructeur de content avec un str strContent*/));
                }
                } catch (IOException ex) {
                    Log.ajouterEntree("impossible de lire le socket dans client.java" + ex.getMessage(),LogLevel.SYSTEM);
                }
            
                //construction de la reponse (en fonction du header de la requete)
                Response response = new Response(requete.getHeader());
                envoyer(response.genereResponse(requete.getHeader().getCible()),response.getStream());//envoi de la réponse dans le socket
            
                //il n'y a plus de traitements a exécuter => fermeture des stream ouverts
                try {
                    in.close();
                    out.close();
                } catch (IOException ex) {
                    Log.ajouterEntree("erreur de fermeture des stream in / out du socket", LogLevel.ERROR);
                }          
            }
    }


    /**
     * Envoie un message sur le socket du client
     * @param data texte a envoyer
     */
    protected void envoyer(String[] data, BufferedInputStream stream) {
        try {

            // ecriture du header    
            out.writeBytes(data[0]);

            if ("OK".equals(data[1])) { // si content

                byte buffer[] = new byte[1024];
                int cpt;// compteur

                while ((cpt = stream.read(buffer, 0, 1024)) != -1) {
                    out.write(buffer);
                }
                            stream.close();
            } else {
                if(data[1] != null){
                    Trace.trace(data[1]);
                    out.writeBytes(data[1]);
                } else {
                out.writeBytes("<h1>404 Not Found</h1>");    
                }
            }
            out.flush();
            //Trace.trace("envoyé : " + data);
        } catch (IOException ex) {
            Log.ajouterEntree("erreur envoyer socket"+ex.getMessage(),LogLevel.SYSTEM);
            //ex.printStackTrace();//TODO : suppr la trace
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
        return this.thread.isAlive();
    }

    @Override
    public String toString(){
            return this.socket.getInetAddress().toString();
    }
    
    public Thread getThread() {
        return thread;
    }

}
