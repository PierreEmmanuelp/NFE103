

package debug;

/**
 * Permet d'écrire dans la sortie standard si la variable debug est a true;
 * @author Pourquier Pierre-Emmanuel
 * @version 1.0
 */
public class Trace {
    /**
     * true : sortie console activée | false : sortie désactivée
     */
    static public boolean debug = false;
    
    static public void trace(String strLigne){
        if(Trace.debug==true){
            System.out.println(strLigne);
        }
    }

}
