/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;

/**
 * Classe permettant la creation du contenu de la reponse
 *
 * @version 1.0
 * @author htruchard
 * @return contenu
 */
public class FileContent extends Content {

    private Mime mime;

    private int status;
    //private String contenu;
    private String pCheminCible;
    
    private String Contenu;
    
  

    public void openFile(String pCheminCible) {
        this.pCheminCible = pCheminCible;
       readFile();

    }

    /*enregistrement d un fichier dans une variable de classe*/
    private String readFile() {

        String myLine="";

        try {
            //création des flux
            InputStreamReader in = new InputStreamReader(new FileInputStream(this.pCheminCible));

            LineNumberReader llog = new LineNumberReader(in);
				//copie du fichier

            while ((myLine = llog.readLine()) != null) {
                // --- Ajout de la ligne au contenu
                contenu += myLine;
            }

            //forcer l'ecriture du contenu du tampon dans le fichier
            setStatus(200);
            System.out.println(getStatus() + "le contenu " + contenu);

        } catch (FileNotFoundException e) {
            setStatus(404);
            System.out.println(e);
        } catch (Exception e) {
            setStatus(500);
            System.out.println(e);
        }
        return contenu;
    }
    private  String envoyerFichier() throws Exception {
    FileInputStream fis = null;
    FileOutputStream fos = null;
    int n;

        try {
          byte buf[] = new byte[1024];
          int taille = 0;

          fis = new FileInputStream(this.pCheminCible);
          fos = new FileOutputStream(contenu);
          while ((n = fis.read(buf)) >= 0) {
            // On écrit dans notre deuxième fichier avec l'objet adéquat
            fos.write(buf);
            
          }
          return contenu;
           } finally {
            if (fis != null) {
                 try {
                fis.close();
                        } catch (Exception e) {}
                            }
                 if (fos != null) {
                    try {
                     fos.close();
                    } catch (Exception e) {
                }
            }
        }
    }
    public Mime getMime() {
        return mime;
    }

    public void setMime(Mime mime) {
        this.mime = mime;
    }

    private void setStatus(int code) {
        this.status = code;
    }

    public int getStatus() {
        return this.status;
    }

}
