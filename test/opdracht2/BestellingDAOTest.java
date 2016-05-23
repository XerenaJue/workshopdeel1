/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
import opdracht2.Bestelling;
import opdracht2.Bestelling;
import opdracht2.BestellingDAO;
import opdracht2.BestellingDAO;
import opdracht2.Klant;
import opdracht2.Klant;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 *
 * @author maurice
 */
public class BestellingDAOTest {
    
    public BestellingDAOTest() {
    }
    
   // @BeforeClass
   // public static void setUpClass() {
   // }
    
  //  @AfterClass
   // public static void tearDownClass() {
   // }
   // 
  // @Before
   //public void setUp() {
  //  }
    
   // @After
   // public void tearDown() {
   // }

    /**
     * Test of readBestelling method, of class BestellingDAO.
     */
    @Test
    
    public void testReadBestelling() throws Exception {
        System.out.println("readBestelling");
        int klant_id = 3;
        BestellingDAO instance = new BestellingDAO();
        Bestelling expResult = new Bestelling();
        expResult.setBestellingID(3);
        expResult.setAantalArtikel1(4);
        Bestelling result = instance.readBestelling(klant_id);
        assertEquals(expResult.getBestelling_id(), result.getBestelling_id());
        assertEquals(expResult.getArtikel_aantal(), result.getArtikel_aantal());
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createCS method, of class BestellingDAO.
     */
    @Test
        @Ignore
    public void testCreateCS() throws Exception {
        System.out.println("createCS");
        String SQLStatement = "";
        BestellingDAO instance = new BestellingDAO();
        instance.createCS(SQLStatement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeCS method, of class BestellingDAO.
     */
    @Ignore
    @Test
    public void testCloseCS() throws Exception {
        System.out.println("closeCS");
        BestellingDAO instance = new BestellingDAO();
        instance.closeCS();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createBestelling method, of class BestellingDAO.
     */
    @Test
        @Ignore
    public void testCreateBestelling() throws Exception {
        System.out.println("createBestelling");
        Klant klant = null;
        BestellingDAO instance = new BestellingDAO();
        instance.createBestelling(klant);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateBestelling method, of class BestellingDAO.
     */
    @Test
        @Ignore
    public void testUpdateBestelling() throws Exception {
        System.out.println("updateBestelling");
        Bestelling bestelling = null;
        BestellingDAO instance = new BestellingDAO();
        instance.updateBestelling(bestelling);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createBestellingRow method, of class BestellingDAO.
     */
    @Test
        @Ignore
    public void testCreateBestellingRow() throws Exception {
        System.out.println("createBestellingRow");
        int klant_id = 0;
        String[] rowInserts = null;
        BestellingDAO instance = new BestellingDAO();
        instance.createBestellingRow(klant_id, rowInserts);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteBestelling method, of class BestellingDAO.
     */
    @Test
        @Ignore
    public void testDeleteBestelling() throws Exception {
        System.out.println("deleteBestelling");
        Bestelling bestelling = null;
        BestellingDAO instance = new BestellingDAO();
        instance.deleteBestelling(bestelling);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
