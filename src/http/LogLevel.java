
package http;

/**
 * Représnete la gravité d'un log
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
 */
public enum LogLevel{
    SYSTEM(0),
    ERROR(1),
    EQUEST(2);
  
    private int num;

    private LogLevel(int val)
	{
		num = val;
	}
}