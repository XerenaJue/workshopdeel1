/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jeroen
 */
public class ArtikelDAOTest {
    
    public ArtikelDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        FillBatchDatabase.fillBatchDatabase();
    }
    
    @After
    public void tearDown() {
        FillBatchDatabase.clearDatabase();
                
    }

    /**
     * Test of readArtikel method, of class ArtikelDAO.
     */
    @Test
    public void testReadArtikel_int() {
        System.out.println("readArtikel");
        int bestellingID = 2;
        ArtikelDAO instance = new ArtikelDAO();
                       
        ArtikelPOJO result = instance.readArtikel(bestellingID);
        assert  result instanceof Object;
        assert result != null;
        
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of readArtikel method, of class ArtikelDAO.
     */
    @Test
    public void testReadArtikel_Bestelling() {
        System.out.println("readArtikel");
        Bestelling bestelling = new Bestelling();
        ArtikelDAO instance = new ArtikelDAO();
        ArtikelPOJO expResult =  new ArtikelPOJO();
        ArtikelPOJO result = instance.readArtikel(bestelling);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of updateArtikel method, of class ArtikelDAO.
     */
    @Test
    public void testUpdateArtikel_Integer_ArtikelPOJO() {
        System.out.println("updateArtikel");
        Integer bestellingID = null;
        ArtikelPOJO artikel = null;
        ArtikelDAO instance = new ArtikelDAO();
        instance.updateArtikel(bestellingID, artikel);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of updateArtikel method, of class ArtikelDAO.
     */
    @Test
    public void testUpdateArtikel_Bestelling_ArtikelPOJO() {
        System.out.println("updateArtikel");
        Bestelling bestelling = null;
        ArtikelPOJO artikel = null;
        ArtikelDAO instance = new ArtikelDAO();
        instance.updateArtikel(bestelling, artikel);
        // TODO review the generated test code and remove the default call to fail.
       
    }
    
}
