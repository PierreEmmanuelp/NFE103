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
public class MimeTest {
    
    public MimeTest() {
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
     * Test of extractTypeMime method, of class Mime.
     */
    @Test
    public void testExtractTypeMime() {
        System.out.println("extractTypeMime");
        String pFichier = "C:Users/htruchard/Desktop/NFE103/essai.txt";
        
        String expResult = "text/html";
        String result = Mime.extractTypeMime(pFichier);
      
        assertEquals(expResult, result);
      
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
