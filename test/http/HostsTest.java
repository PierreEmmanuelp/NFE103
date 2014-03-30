/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package http;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Morgan Pinatel
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
        Host pHost = new Host("myName", "myPath");
        Hosts instance = new Hosts();
        instance.addHost(pHost);
    }

    /**
     * Test of removeHost method, of class Hosts.
     */
    @Test
    public void testRemoveHost() {
        System.out.println("removeHost");
        Host phost = new Host("myName", "myPath");
        Hosts instance = new Hosts();
        instance.removeHost(phost);
    }

    /**
     * Test of getHost method, of class Hosts.
     */
    @Test
    public void testGetHost() {
        System.out.println("getHost");
        String pName = "myName";
        Hosts instance = new Hosts();
        Host myHost = new Host("myName", "myPath");
        instance.addHost(myHost);
        Host expResult = new Host("myName", "myPath");
        Host result = instance.getHost(pName);
        assertEquals(expResult.getName(), result.getName());
        assertEquals(expResult.getPath(), result.getPath());
    }

    /**
     * Test of getPath method, of class Hosts.
     */
    @Test
    public void testGetPath() {
        System.out.println("getPath");
        String pName = "myName";
        Hosts instance = new Hosts();
        Host myHost = new Host("myName", "myPath");
        instance.addHost(myHost);
        String expResult = "myPath";
        String result = instance.getPath(pName);
        assertEquals(expResult, result);
    }

    /**
     * Test of existe method, of class Hosts.
     */
    @Test
    public void testExiste() {
        System.out.println("existe");
        Host pHost = new Host("myName", "myPath");
        Hosts instance = new Hosts();
        Host myHost = new Host("myName", "myPath");
        instance.addHost(myHost);
        boolean expResult = true;
        boolean result = instance.existe(pHost);
        assertEquals(expResult, result);
    }

    /**
     * Test of getHostList method, of class Hosts.
     */
    @Test
    public void testGetHostList() {
        System.out.println("getHostList");
        Hosts instance = new Hosts();
        instance.addHost(new Host("myName", "myPath"));
        List<Host> expResult = new ArrayList();
        expResult.add(new Host("myName", "myPath"));
        List<Host> result = instance.getHostList();
        assertEquals(expResult.toString(), result.toString());
    }
    
}
