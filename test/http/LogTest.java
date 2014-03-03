package http;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * teste la classe Log
 * @version 1.0
 * @author pierre-emmanuel Pourquier
 */
public class LogTest {
    
    public LogTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {

    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPATH method, of class Log.
     */
    @Test
    public void testGetPATH() {
        try {
            Log instance = new Log();
            String expResult = "/home/pierre-emmanuel/";
            String result = instance.getPATH();
            assertEquals(expResult, result);
        } catch (IOException ex) {
            Logger.getLogger(LogTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of ajouterEntree method, of class Log.
     */
    @Test
    public void testAjouterEntree() {
        System.out.println("ajouterEntree");
        String pligneLog = "";
        Log.ajouterEntree(pligneLog,LogLevel.ERROR);
    }

    /**
     * Test of terminerSessionLog method, of class Log.
     */
    @Test
    public void testTerminerSessionLog() {
        System.out.println("terminerSessionLog");
        Log.terminerSessionLog();
    }
    
}
