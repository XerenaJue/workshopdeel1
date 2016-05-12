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
        int bestellingID = 1;
        ArtikelDAO instance = new ArtikelDAO();
        ArtikelPOJO expResult = instance.readArtikel(bestellingID);
        System.out.println("eerste lezing: " + expResult);
                               
        ArtikelPOJO result = instance.readArtikel(bestellingID);
        System.out.println("tweede lezing: " + result);
                
        assertEquals(expResult, result);
              
    }

    /**
     * Test of readArtikel method, of class ArtikelDAO.
     */
    @Test
    public void testReadArtikel_Bestelling() {
        System.out.println("readArtikel");
        
        ArtikelDAO instance = new ArtikelDAO();
        Bestelling bestelling1 = new Bestelling();
        Bestelling bestelling2 = new Bestelling();
        
        ArtikelPOJO expResult = instance.readArtikel(bestelling1);
        System.out.println("eerste lezing: " + expResult);
                               
        ArtikelPOJO result = instance.readArtikel(bestelling2);
        System.out.println("tweede lezing: " + result);
                
        assertEquals(expResult, result);
    }

    /**
     * Test of updateArtikel method, of class ArtikelDAO.
     */
    @Test
    public void testUpdateArtikel_Integer_ArtikelPOJO() {
        System.out.println("updateArtikel");
        Integer bestellingID = 1;
        ArtikelPOJO expResult = new ArtikelPOJO();
        expResult.setArtikelID(1);
        expResult.setArtikelNaam("naampje");
        expResult.setArtikelPrijs(77);
        
        ArtikelDAO instance = new ArtikelDAO();
        instance.updateArtikel(bestellingID, expResult);
        
        ArtikelPOJO result = instance.readArtikel(bestellingID);
        
        assertEquals(expResult, result);
        assertEquals(1, (long)result.getArtikelID());
                
    }

    /**
     * Test of updateArtikel method, of class ArtikelDAO.
     */
    @Test
    public void testUpdateArtikel_Bestelling_ArtikelPOJO() {
        System.out.println("updateArtikel");
        Bestelling bestelling = new Bestelling();
        bestelling.setBestellingID(2);
        ArtikelPOJO expResult = new ArtikelPOJO();
        expResult.setArtikelID(1);
        expResult.setArtikelNaam("NAAM");
        expResult.setArtikelPrijs(88);
        
        ArtikelDAO instance = new ArtikelDAO();
        instance.updateArtikel(2, expResult);
        
        ArtikelPOJO result = instance.readArtikel(2);
        
        assertEquals(expResult, result);
        assertEquals(1, (long)result.getArtikelID());
                
    }
       
       
}
