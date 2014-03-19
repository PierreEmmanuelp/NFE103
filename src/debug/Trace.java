package debug;
/** Permet d'écrire dans la sortie standard si la variable debug est a true.
 * @author Pourquier Pierre-Emmanuel
 * @version 1.0
 */
public class Trace {
    /** Boolean debug : si le projet est en mode debug.
     */
    private static boolean debug = false;

    /** Ecrit la ligne dans la sortie standard si this.debug = true.
    * @param strLigne ligne à écrire
    */
    public static void trace(final String strLigne) {
        if (Trace.debug) {
            System.out.println(strLigne);
        }
    }

    /** exprime si le projet est en debug.
     * @return true si le debug est actif
     */
    public static boolean isDebug() {
        return debug;
    }

    /** permet de spécifié si le debug est actif.
     * @param pdebug true si le projet est en debug
     */
    public static void setDebug(final boolean pdebug) {
        Trace.debug = pdebug;
    }
}
