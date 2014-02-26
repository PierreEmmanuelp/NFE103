package http;

import java.io.*;

/**
 *
 * @author Pierre-Emmanuel Pourquier,Herve Truchard
 * @version 1.0
 */
public class Content {
    
    private String contenu;
    private String CheminCible;
    

   public void openFile (String CheminCible) {
        this.CheminCible = CheminCible;
        readFile();

}
   
   /*enregistrement d un fichier dans une variable de classe*/
    private String readFile (){
        
   
     
     String myLine="";
     
		try{
			//création des flux
			InputStreamReader in=new InputStreamReader(new FileInputStream(this.CheminCible));
			
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


    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getCheminCible() {
        return CheminCible;
    }

    public void setCheminCible(String CheminCible) {
        this.CheminCible = CheminCible;
    }
   
}
