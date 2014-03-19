
package log;

/** Représente la gravité d'un log.
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
 */
public enum LogLevel {
    /** log système.*/
    SYSTEM,

    /** log d'erreur.*/
    ERROR,

    /** log une requete.*/
    REQUEST;
}