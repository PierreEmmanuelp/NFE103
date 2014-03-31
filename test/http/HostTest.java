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
 * @author Morgan Pinatel
 */
public class HostTest {
    
    public HostTest() {
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
     * Test of getName method, of class Host.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Host instance = new Host("myName","myPath");
        String expResult = "myName";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPath method, of class Host.
     */
    @Test
    public void testGetPath() {
        System.out.println("getPath");
        Host instance = new Host("myName", "myPath");
        String expResult = "myPath";
        String result = instance.getPath();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class Host.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String pName = "myNewName";
        Host instance = new Host("myName","myPath");
        instance.setName(pName);
    }

    /**
     * Test of setPath method, of class Host.
     */
    @Test
    public void testSetPath() {
        System.out.println("setPath");
        String pPath = "myNewPath";
        Host instance = new Host("myName","myPath");
        instance.setPath(pPath);
    }

    /**
     * Test of toString method, of class Host.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Host instance = new Host("myName","myPath");
        String expResult = "myName";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
