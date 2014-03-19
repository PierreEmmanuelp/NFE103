package debug;
/** Permet d'écrire dans la sortie standard si la variable debug est a true.
 * @author Pourquier Pierre-Emmanuel
 * @version 1.0
 */
 public final class Trace {
    /** si le projet est en mode debug.
     */
    private static boolean debug = false;

    /** Constructeur privé pour eviter une instanciation.
     */
    private Trace() {
           throw new AssertionError("Instanciation d'une classe utilitaire");
    }

    /** Ecrit la ligne dans la sortie standard si this.debug = true.
    * @param strLigne ligne à écrire
    */
    public static void trace(final String strLigne) {
        if (Trace.debug) {
            System.out.println(strLigne);
        }
    }

    /** Si le projet est en debug.
     * @return true si le debug est actif
     */
    public static boolean isDebug() {
        return debug;
    }

    /** permet de spécifier si le debug est actif.
     * @param pdebug true si le projet est en debug
     */
    public static void setDebug(final boolean pdebug) {
        Trace.debug = pdebug;
    }
}
