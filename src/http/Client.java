package http;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;
//import java.util.Hashtable;


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
             Log.ajouterEntree("Impossible de créer le reader et le writter dans client.java",LogLevel.SYSTEM);
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
        String strContent; //recevra le content de la requete 
        Request requete = null; // requete http 
      //  Response reponse; // reponse http (qui dépend de la requete)
        try {
            while((ligneSocket=in.readLine())!=null && ligneSocket.length()>0){
                header.add(ligneSocket);
            }
            requete = new Request(header);
            if(requete.besoinContent()==true){ 
                ligneSocket=in.readLine();
                strContent=(ligneSocket);
                requete.setContent(new Content(/*TODO : contructeur de content avec un str strContent*/));
            }
            
        //TODO reponse = new Response(requete);
            
        } catch (IOException ex) {
            Log.ajouterEntree("impossible de lire le socket dans client.java",LogLevel.SYSTEM);
        }
        
        //si tout s'est bien passé header contient l'ensemble des données reçues. Vérifions puis parsons :
       
      //  Response response = new Response(requete.getHeader());
        envoyer("");//response.genereResponse(requete.getHeader().getCible()));
        
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
           
            out.writeBytes("HTTP/1.1  200\r\n");
            out.writeBytes("Content-Type:image/png\r\n");
            out.writeBytes("\r\n");
   
          File file = new File("/var/www/test.png");
          BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            byte buf[] = new byte[1024];
            
           int len;
           
          while((len = in.read(buf,0,1024)) != -1) {
            // On écrit dans notre deuxième fichier avec l'objet adéquat
            out.write(buf);         
          }  
          
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
