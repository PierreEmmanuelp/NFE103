package http.headers;

/**
*@author htruchard, Pierre-Emmanuel Pourquier
* Represente une action http
*@version 1.0
*/
public enum Action {
    /**.
     * Action GET du protocole Http
     */
    GET(1),
    /**.
     * Action POST du protocole Http
     */
    POST(2),
    /**.
     * Action HEAD du protocole Http
     */
    HEAD(3),
    /**.
     * Action GET du protocole Http
     */
    OPTIONS(4),
    /**.
     * Action GET du protocole Http
     */
    CONNECT(5),
    /**.
     * Action GET du protocole Http
     */
    TRACE(6),
    /**.
     * Action GET du protocole Http
     */
    PUT(7),
    /**.
     * Action GET du protocole Http
     */
    PATCH(8),
    /**.
     * Action GET du protocole Http
     */
    DELETE(9);

    /**.
     * numéro associé à l'action http
     */
    private final int num;

    /**.
     * @param val valeur associé à l(action
     */
    private Action(final int val) {
        this.num = val;
    }
}
