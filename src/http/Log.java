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
	static String PATH="/var/log/http/";
	
	
	/**
	 * initialise le buffer de sortie dans le fichier
	 * @throws IOException 
	 */
	public Log() throws IOException {

	}


	/**
	 * renvoit le chemin du fichier de log
	 * @return the path
	 */
	public String getPATH() {
		return PATH;
	}
	
	
	/**
	 * Ajoute un ligne à la fin du fichier de log
	 * @param ligneLog correspond 
	 * @throws IOException 
	 */
	synchronized static void ajouterEntree(String pligneLog,LogLevel plog){
		FileWriter fw;
		try {
			fw = new FileWriter (PATH+plog+".log",true);
			BufferedWriter bw = new BufferedWriter (fw);
                        PrintWriter fichier = new PrintWriter (bw);
                        String format = "dd/MM/yy H:mm:ss"; 
                        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format ); 
                        java.util.Date date = new java.util.Date(); 
                        fichier.println(formater.format(date)+" : "+pligneLog);
                        fichier.close();
                         
		} catch (IOException e) {
			e.getMessage();	
			System.out.println("erreur log");
		}
               

	}
	
	/**
	 * 
	 */
	synchronized static void terminerSessionLog(){
		FileWriter fw;
		try {
			fw = new FileWriter (PATH,true);
			BufferedWriter bw = new BufferedWriter (fw);
                        PrintWriter fichier = new PrintWriter (bw);
                        fichier.println("\n\n");      
		} catch (IOException e) {
			e.getMessage();
		}

	}
}
