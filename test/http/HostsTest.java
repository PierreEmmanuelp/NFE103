/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package http;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author morgan
 */
public class HostsTest {
    
    public HostsTest() {
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
     * Test of addHost method, of class Hosts.
     */
    @Test
    public void testAddHost() {
        System.out.println("addHost");
        Host pHost = null;
        Hosts instance = new Hosts();
        instance.addHost(pHost);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeHost method, of class Hosts.
     */
    @Test
    public void testRemoveHost() {
        System.out.println("removeHost");
        Host phost = null;
        Hosts instance = new Hosts();
        instance.removeHost(phost);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHost method, of class Hosts.
     */
    @Test
    public void testGetHost() {
        System.out.println("getHost");
        String pName = "";
        Hosts instance = new Hosts();
        Host expResult = null;
        Host result = instance.getHost(pName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPath method, of class Hosts.
     */
    @Test
    public void testGetPath() {
        System.out.println("getPath");
        String pName = "";
        Hosts instance = new Hosts();
        String expResult = "";
        String result = instance.getPath(pName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of existe method, of class Hosts.
     */
    @Test
    public void testExiste() {
        System.out.println("existe");
        Host pHost = null;
        Hosts instance = new Hosts();
        boolean expResult = false;
        boolean result = instance.existe(pHost);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHostList method, of class Hosts.
     */
    @Test
    public void testGetHostList() {
        System.out.println("getHostList");
        Hosts instance = new Hosts();
        List<Host> expResult = null;
        List<Host> result = instance.getHostList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
