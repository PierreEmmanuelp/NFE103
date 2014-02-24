package http;

/**
 * Représente une action http
 * @author htruchard, Pierre-Emmanuel Pourquier
 * @version 1.0
 */
public enum Action {
    // Les objets énumérés sont forcément au début de la déclaration
	// Ils doivent faire appel à un des constructeurs définis
	GET(1), POST(2);
	
	private int num;
	
	// Les constructeurs ne peuvent pas être protected ou public
	private Action(int val)
	{
		num = val;
	}
	
	
    
}
