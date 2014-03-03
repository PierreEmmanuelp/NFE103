package http.headers;

/**
*@author htruchard, Pierre-Emmanuel Pourquier
* Represente une action http
*@version 1.0
*/
public enum Action {
    // Les objets énumérés sont forcément au début de la déclaration
	// Ils doivent faire appel à un des constructeurs définis
	GET(1), 
        POST(2),
        HEAD(3),
        OPTIONS(4),
        CONNECT(5),
        TRACE(6),
        PUT(7),
        PATCH(8),
        DELETE(9),
	;
	private int num;
	
	// Les constructeurs ne peuvent pas être protected ou public
	private Action(int val)
	{
		num = val;
	}
	
	
    
}
