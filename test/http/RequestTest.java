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
 * @author pierre-emmanuel
 */
public class RequestTest {
    
    public RequestTest() {
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
     * Test of besoinContent method, of class Request.
     */
    @Test
    public void testBesoinContent() {
        System.out.println("besoinContent");
        Request instance = null;
        boolean expResult = false;
        boolean result = instance.besoinContent();
        assertEquals(expResult, result);
       
    }

   
   

    /**
     * Test of getHeader method, of class Request.
     */
    @Test
    public void testGetHeader() {
        System.out.println("getHeader");
        Request instance = null;
        Header expResult = null;
        Header result = instance.getHeader();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getContent method, of class Request.
     */
    @Test
    public void testGetContent() {
        System.out.println("getContent");
        Request instance = null;
        Content expResult = null;
        Content result = instance.getContent();
        assertEquals(expResult, result);
       
    }
    
}
