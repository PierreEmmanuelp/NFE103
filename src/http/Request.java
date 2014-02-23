package http;

import java.util.ArrayList;

/**
 * Correspond à une requete HTPP
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
 */
public class Request {
    private Header header; //correspond au header de la requete http
    private Content content; //correspond au contenu de la requete http
    private ArrayList<String> request; // correspond à la chaine brute à analyser
    
    /**
     * initialise l'objet
     * @param pRequete la chaine de caractères à analyser qui correspond à une requete http
     */
    public Request(ArrayList<String> pRequete){
        this.request = pRequete;
        analyseRequest();
    }
    
    /**
     * parse request pour créer un header et un content
     */
    private void analyseRequest(){
        this.header = new Header();
        this.content = new Content();
        for(int i = 0; i< request.size();i++){
            //if(request.get(i).contains("GET")){
                System.out.println(request.get(i));
               
            //}
             //System.out.println("il faudrait créer un header avec la fonction addHeader(String pHeader) qui aurait ici la forme de addHeader(" + request.get(i) +")" );
             if(request.get(i).contains("Content:")){
                 //this.content.addContent(request.get(i);
             }else{
                //this.header.addHeader(request.get(i); 
             }
            
             
        }
    }

    /**
     * vérifie si la requete nécessite du contenu
     * @return true si besoin d'un content 
     */
    public boolean besoinContent() {
      //if(header.getContentLength > 0){
      //    return true;
      //}else{
        return false;//si il y a du contenu. Je l'ai mis a des fins de tests
      //}
    }
    
    /**
     * analyse le contenu
     * @param pContenu le contenu au format string de la requete http initiale
     */
    public void analyseContent(String pContenu){
        
    }
    
    
    /**
     * @return le header de la requete
     */
    public Header getHeader() {
        return header;
    }

    /**
     * @return le contenu de la requete
     */
    public Content getContent() {
        return content;
    }
}
