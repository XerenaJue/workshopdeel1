/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;
import java.sql.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;


/**
 *
 * @author maurice
 */
public class BestellingDAOTest {
    Connection connection;
    public BestellingDAOTest() {
    }
    
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        
    }
    
    @After
    public void tearDown() {
       // FillBatchDatabase.clearDatabase();
    }

    /**
     * Test of readBestelling method, of class BestellingDAO.
     */
    @Test
    public void testReadBestelling() throws Exception {
        System.out.println("readBestelling");
        int klant_id = 0;
        BestellingDAO instance = new BestellingDAO();
        Bestelling expResult = null;
        Bestelling result = instance.readBestelling(klant_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createCS method, of class BestellingDAO.
     */
    @Ignore
    @Test
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
    public void testCloseCS() throws SQLException, ClassNotFoundException {
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
    public void testCreateBestelling() throws Exception {
        System.out.println("createBestelling");
        Klant klant = new Klant();
        klant.setVoornaam("Peter");
        klant.setTussenvoegsel("de");
        klant.setAchternaam("achternaam");
        klant.setEmail("pweter@gmail.com");
        klant.setKlantID(777);
        BestellingDAO instance = new BestellingDAO();
        instance.createBestelling(klant);
        connection = ConnectionFactory.getMySQLConnection();
        String SQLStatement = "select * from Bestelling where klant_id =?";
        PreparedStatement statement = connection.prepareStatement(SQLStatement);     
        statement.setInt(1, 777);
        ResultSet resultSet = statement.executeQuery();   
        assertEquals(123, resultSet.getString("artikel_aantal"));
        assertEquals(456, resultSet.getString("artikel_aantal2"));
        assertEquals(789, resultSet.getString("artikel_aantal3"));
        // TODO review the generated test code and remove the default call to fail.
        fail("helaas pindakaas, de test is failed...");
    }

    /**
     * Test of updateBestelling method, of class BestellingDAO.
     */
    @Ignore
    @Test
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
    @Ignore
    @Test
    public void testCreateBestellingRow() throws SQLException, ClassNotFoundException {
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
    @Ignore
    @Test
    public void testDeleteBestelling() throws Exception {
        System.out.println("deleteBestelling");
        Bestelling bestelling = null;
        BestellingDAO instance = new BestellingDAO();
        instance.deleteBestelling(bestelling);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
