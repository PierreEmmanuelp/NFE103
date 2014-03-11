/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package http;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Classe permettant la creation du contenu de la reponse
 *@version 1.0
 * @author htruchard
 * @return contenu
 */
public class FileContent extends Content{
    private Mime mime;

   
    //private String contenu;
    private String pCheminCible;
    
    
    public void openFile (String pCheminCible) {
        this.pCheminCible = pCheminCible;
        readFile();

}
   
   /*enregistrement d un fichier dans une variable de classe*/
    private String readFile (){
        
   
     
     String myLine="";
     
		try{
			//création des flux
			InputStreamReader in=new InputStreamReader(new FileInputStream(this.pCheminCible));
			
				 LineNumberReader llog = new LineNumberReader(in);
				//copie du fichier
				
					while ((myLine = llog.readLine()) != null) 
                                        { 
                      // --- Ajout de la ligne au contenu
                                        contenu += myLine;
                                         }
                                        
					//forcer l'ecriture du contenu du tampon dans le fichier
					
                                        System.out.println("le contenu" +contenu );
                                       
                                    }catch(Exception e){
                                
                            }
          return contenu;      
    }
 public Mime getMime() {
        return mime;
    }

    public void setMime(Mime mime) {
        this.mime = mime;
    }
    
}
