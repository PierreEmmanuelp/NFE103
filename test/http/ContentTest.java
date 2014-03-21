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
 * @author htruchard
 */
public class ContentTest {
    
    public ContentTest() {
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
     * Test of getContenu method, of class Content.
     */
    @Test
    public void testGetContenu() {
        System.out.println("getContenu");
        Content instance = new Content();
        String expResult = "";
        String result = instance.getContenu();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setContenu method, of class Content.
     */
    @Test
    public void testSetContenu() {
        System.out.println("setContenu");
        String pcontenu = "";
        Content instance = new Content();
        instance.setContenu(pcontenu);
        
    }

    /**
     * Test of toString method, of class Content.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Content instance = new Content();
        String expResult = "Content{}";
        String result = instance.toString();
        assertEquals(expResult, result);
        
    }
    
}
