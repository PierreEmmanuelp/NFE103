/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package http;

/**
 *
 * @author htruchard
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
