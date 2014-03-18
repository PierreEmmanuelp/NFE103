/**
 * Fichier de log de l'application
 */
package http;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**.
 * Représente un fichier de log
 * @author Pierre-Emmanuel.Pourquier
 * @version 1.1
 */
public class Log {
    /**.
     * chemin du dossier contenant les fichier de log
     */
    private static String path = "/var/log/http/";

    /**.
    * initialise le buffer de sortie dans le fichier
    * @throws IOException concernant le filesystem
    */
    public Log() throws IOException {
        path = http.Http.getConfig().getPathLog();
    }


    /**.
    * renvoit le chemin du fichier de log
    * @return the path
    */
    public static String getPath() {
        return path;
    }

    /**.
    * Ajoute un ligne à la fin du fichier de log
    * @param pligne correspond à la ligne à écrire
    * @param pLvl correspond au niveau du fichier à écrire
    */
    static synchronized void ajouterEntree(final String pligne, final LogLevel pLvl) {
        FileWriter fw;
        try {
            fw = new FileWriter(path + pLvl + ".log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter fichier;
            fichier = new PrintWriter(bw);
            String format = "dd/MM/yy H:mm:ss";
            SimpleDateFormat formater;
            formater = new SimpleDateFormat(format);
            java.util.Date date;
            date = new java.util.Date();
            fichier.println(formater.format(date) + " : " + pligne);
            fichier.close();
        } catch (IOException e) {
            debug.Trace.trace(e.getMessage());
            System.out.println("erreur dans les fichiers de log");
        }
    }

    /**.
     * termine la session des log et ferme les flux
     */
    static synchronized void terminerSessionLog() {
        FileWriter fw;
        try {
            fw = new FileWriter(path, true);
            BufferedWriter bw;
            bw = new BufferedWriter(fw);
            PrintWriter fichier = new PrintWriter(bw);
            fichier.println("\n\n");
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
