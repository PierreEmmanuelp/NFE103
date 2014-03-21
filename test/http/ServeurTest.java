/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package http;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marine
 */
public class ServeurTest {
    
    public ServeurTest() {
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
     * Test of start method, of class Serveur.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        Serveur instance = new Serveur();
        instance.start();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stop method, of class Serveur.
     */
    @Test
    public void testStop() {
        System.out.println("stop");
        Serveur instance = new Serveur();
        instance.stop();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPORT method, of class Serveur.
     */
    @Test
    public void testGetPORT() {
        System.out.println("getPORT");
        Serveur instance = new Serveur();
        int expResult = 0;
        int result = instance.getPORT();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHost method, of class Serveur.
     */
    @Test
    public void testGetHost() {
        System.out.println("getHost");
        Hosts expResult = null;
        Hosts result = Serveur.getHost();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPoolThread method, of class Serveur.
     */
    @Test
    public void testGetPoolThread() {
        System.out.println("getPoolThread");
        Serveur instance = new Serveur();
        int expResult = 0;
        int result = instance.getPoolThread();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tailThread method, of class Serveur.
     */
    @Test
    public void testTailThread() {
        System.out.println("tailThread");
        Client pclient = null;
        Serveur instance = new Serveur();
        instance.tailThread(pclient);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
