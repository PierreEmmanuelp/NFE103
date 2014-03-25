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
public class HttpTest {
    
    public HttpTest() {
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
     * Test of main method, of class Http.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Http.main(args);
    }

    /**
     * Test of getConfig method, of class Http.
     */
    @Test
    public void testGetConfig() {
        System.out.println("getConfig");
        Configuration expResult = null;
        Configuration result = Http.getConfig();
        assertEquals(expResult, result);
    }
    
}
