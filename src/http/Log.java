/**
 * Fichier de log de l'application
 */
package http;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Représente un fichier de log 
 * @author Pierre-Emmanuel.Pourquier
 * @version 1.1
 */
public class Log {
        static String path="/var/log/http/";
	
	
	/**
	 * initialise le buffer de sortie dans le fichier
	 * @throws IOException 
	 */
	public Log() throws IOException {
               path = http.Http.config.getPathLog();
	}


	/**
	 * renvoit le chemin du fichier de log
	 * @return the path
	 */
	public String getPATH() {
		return path;
	}
	
	
	/**
	 * Ajoute un ligne à la fin du fichier de log
	 * @param ligneLog correspond 
	 * @throws IOException 
	 */
	synchronized static void ajouterEntree(String pligneLog,LogLevel plog){
		FileWriter fw;
		try {
			fw = new FileWriter (path+plog+".log",true);
			BufferedWriter bw = new BufferedWriter (fw);
                        PrintWriter fichier = new PrintWriter (bw);
                        String format = "dd/MM/yy H:mm:ss"; 
                        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format ); 
                        java.util.Date date = new java.util.Date(); 
                        fichier.println(formater.format(date)+" : "+pligneLog);
                        fichier.close();
                         
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("erreur log");
		}
               

	}
	
	/**
	 * 
	 */
	synchronized static void terminerSessionLog(){
		FileWriter fw;
		try {
			fw = new FileWriter (path,true);
			BufferedWriter bw = new BufferedWriter (fw);
                        PrintWriter fichier = new PrintWriter (bw);
                        fichier.println("\n\n");      
		} catch (IOException e) {
			e.getMessage();
		}

	}
}
