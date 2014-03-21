/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package http;

import java.io.BufferedInputStream;
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
public class FileContentTest {
    
    public FileContentTest() {
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
     * Test of openFile method, of class FileContent.
     */
    @Test
    public void testOpenFile() {
        System.out.println("openFile");
        String cheminCible = "/essai.txt";
        FileContent instance = new FileContent();
        instance.openFile(cheminCible);
       
      
    }

    /**
     * Test of getMime method, of class FileContent.
     */
    @Test
    public void testGetMime() {
        System.out.println("getMime");
        FileContent instance = new FileContent();
        String expResult = "";
        instance.getMime();
        
       
    }

    /**
     * Test of getStatus method, of class FileContent.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        FileContent instance = new FileContent();
        int expResult = 0;
        int result = instance.getStatus();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getFileContent method, of class FileContent.
     */
    @Test
    public void testGetFileContent() {
        System.out.println("getFileContent");
        FileContent instance = new FileContent();
        BufferedInputStream expResult = null;
        BufferedInputStream result = instance.getFileContent();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getLength method, of class FileContent.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        FileContent instance = new FileContent();
        Long expResult = null;
        Long result = instance.getLength();
        assertEquals(expResult, result);
        
    }
    
}
