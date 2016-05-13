/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        System.out.println("readArtikel mbv bestellingid");
                   
        int bestellingID = 1;
        ArtikelPOJO expResult = new ArtikelPOJO();
        
        String query = "select artikel_id, artikel_naam, artikel_prijs "
                     + "from bestelling "
                     + "where bestelling_id = " + bestellingID; 
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                        
            if (resultSet.next()) {
               
                expResult.setArtikelID(resultSet.getInt(1));
                expResult.setArtikelNaam(resultSet.getString(2));
                expResult.setArtikelPrijs(resultSet.getInt(3));
                System.out.println("eerste lezing: " + expResult);
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in testReadArtikel" );
            ex.printStackTrace();
        }
        
        ArtikelDAO instance = new ArtikelDAO();
        ArtikelPOJO result = instance.readArtikel(bestellingID);
        System.out.println("tweede lezing: " + result);
                
        assertEquals(expResult, result);
    
    }
    
    /**
     * Test of readArtikel method, of class ArtikelDAO.
     */
    @Test
    public void testReadArtikel_Bestelling() {
        System.out.println("readArtikel mbv bestelling-object");
        int bestellingID = 7;
        ArtikelPOJO expResult = new ArtikelPOJO();
        
        String query = "select artikel_id, artikel_naam, artikel_prijs "
                     + "from bestelling "
                     + "where bestelling_id = " + bestellingID; 
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                        
            if (resultSet.next()) {
               
                expResult.setArtikelID(resultSet.getInt(1));
                expResult.setArtikelNaam(resultSet.getString(2));
                expResult.setArtikelPrijs(resultSet.getInt(3));
                System.out.println("eerste lezing: " + expResult);
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in testReadArtikel 2" );
            ex.printStackTrace();
        }
                
        ArtikelDAO instance = new ArtikelDAO();
        System.out.println("gaat nu Bestelling-object aanmaken " );
        Bestelling bestelling = new Bestelling();
        bestelling.setBestellingID(bestellingID);
              
        ArtikelPOJO result = instance.readArtikel(bestelling);
        System.out.println("tweede lezing  mbv bestelling: " + result);
                
        assertEquals(expResult, result);
    }
    
        /**
     * Test of readArtikel2 method, of class ArtikelDAO.
     */
    @Test
    public void testReadArtikel2_int() {
        System.out.println("readArtikel mbv bestellingid");
                   
        int bestellingID = 1;
        ArtikelPOJO expResult = new ArtikelPOJO();
        
        String query = "select artikel2_id, artikel2_naam, artikel2_prijs "
                     + "from bestelling "
                     + "where bestelling_id = " + bestellingID; 
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                        
            if (resultSet.next()) {
               
                expResult.setArtikelID(resultSet.getInt(1));
                expResult.setArtikelNaam(resultSet.getString(2));
                expResult.setArtikelPrijs(resultSet.getInt(3));
                System.out.println("eerste lezing: " + expResult);
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in testReadArtikel2" );
            ex.printStackTrace();
        }
        
        ArtikelDAO instance = new ArtikelDAO();
        ArtikelPOJO result = instance.readArtikel2(bestellingID);
        System.out.println("tweede lezing: " + result);
                
        assertEquals(expResult, result);
    
    }
    
    
    /**
     * Test of readArtikel2 method, of class ArtikelDAO.
     */
    @Test
    public void testReadArtikel2_Bestelling() {
        System.out.println("readArtikel2 mbv bestelling-object");
        int bestellingID = 7;
        ArtikelPOJO expResult = new ArtikelPOJO();
        
        String query = "select artikel2_id, artikel2_naam, artikel2_prijs "
                     + "from bestelling "
                     + "where bestelling_id = " + bestellingID; 
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                        
            if (resultSet.next()) {
               
                expResult.setArtikelID(resultSet.getInt(1));
                expResult.setArtikelNaam(resultSet.getString(2));
                expResult.setArtikelPrijs(resultSet.getInt(3));
                System.out.println("eerste lezing: " + expResult);
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in testReadArtikel 2" );
            ex.printStackTrace();
        }
                
        ArtikelDAO instance = new ArtikelDAO();
        System.out.println("gaat nu Bestelling-object aanmaken " );
        Bestelling bestelling = new Bestelling();
        bestelling.setBestellingID(bestellingID);
              
        ArtikelPOJO result = instance.readArtikel2(bestelling);
        System.out.println("tweede lezing  mbv bestelling: " + result);
                
        assertEquals(expResult, result);
    }
        /**
     * Test of readArtikel3 method, of class ArtikelDAO.
     */
    @Test
    public void testReadArtikel3_int() {
        System.out.println("readArtikel3 mbv bestellingid");
                   
        int bestellingID = 1;
        ArtikelPOJO expResult = new ArtikelPOJO();
        
        String query = "select artikel3_id, artikel3_naam, artikel3_prijs "
                     + "from bestelling "
                     + "where bestelling_id = " + bestellingID; 
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                        
            if (resultSet.next()) {
               
                expResult.setArtikelID(resultSet.getInt(1));
                expResult.setArtikelNaam(resultSet.getString(2));
                expResult.setArtikelPrijs(resultSet.getInt(3));
                System.out.println("eerste lezing: " + expResult);
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in testReadArtikel3" );
            ex.printStackTrace();
        }
        
        ArtikelDAO instance = new ArtikelDAO();
        ArtikelPOJO result = instance.readArtikel3(bestellingID);
        System.out.println("tweede lezing: " + result);
                
        assertEquals(expResult, result);
    
    }
        /**
     * Test of readArtikel3 method, of class ArtikelDAO.
     */
    @Test
    public void testReadArtikel3_Bestelling() {
        System.out.println("readArtikel3 mbv bestelling-object");
        int bestellingID = 7;
        ArtikelPOJO expResult = new ArtikelPOJO();
        
        String query = "select artikel3_id, artikel3_naam, artikel3_prijs "
                     + "from bestelling "
                     + "where bestelling_id = " + bestellingID; 
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                        
            if (resultSet.next()) {
               
                expResult.setArtikelID(resultSet.getInt(1));
                expResult.setArtikelNaam(resultSet.getString(2));
                expResult.setArtikelPrijs(resultSet.getInt(3));
                System.out.println("eerste lezing: " + expResult);
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in testReadArtikel 3" );
            ex.printStackTrace();
        }
                
        ArtikelDAO instance = new ArtikelDAO();
        System.out.println("gaat nu Bestelling-object aanmaken " );
        Bestelling bestelling = new Bestelling();
        bestelling.setBestellingID(bestellingID);
              
        ArtikelPOJO result = instance.readArtikel3(bestelling);
        System.out.println("tweede lezing  mbv bestelling: " + result);
                
        assertEquals(expResult, result);
    }
    
    
    

    /**
     * Test of updateArtikel method, of class ArtikelDAO.
     */
    @Test
    public void testUpdateArtikel_Integer_ArtikelPOJO() {
        System.out.println("updateArtikel mbv bestellingID-int");
        Integer bestellingID = 1;
        int artikelID = 9;
        
        ArtikelPOJO expResult = new ArtikelPOJO();
        expResult.setArtikelID(artikelID);
        expResult.setArtikelNaam("naampje");
        expResult.setArtikelPrijs(77);
        
        ArtikelDAO instance = new ArtikelDAO();
        instance.updateArtikel(bestellingID, expResult);
        System.out.println("gaat nu result uitlezen mbv readArtikel(integer bestellingID) ");
        ArtikelPOJO result = instance.readArtikel(bestellingID);
        System.out.println("resultaat " + result +  " uitgelezen gaat nu vergelijken met expected" );
        
        assertEquals(expResult, result);
        assertEquals(artikelID, (long)result.getArtikelID());
                
    }

    /**
     * Test of updateArtikel method, of class ArtikelDAO.
     */
    @Test
    public void testUpdateArtikel_Bestelling_ArtikelPOJO() {
        System.out.println("updateArtikel mbv bestelling-object");
        Integer bestellingID = 2;
        int artikelID = 9;
        
        Bestelling bestelling = new Bestelling();
        bestelling.setBestellingID(bestellingID);
        
        ArtikelPOJO expResult = new ArtikelPOJO();
        expResult.setArtikelID(artikelID);
        expResult.setArtikelNaam("NAAM");
        expResult.setArtikelPrijs(88);
        
        ArtikelDAO instance = new ArtikelDAO();
        instance.updateArtikel(bestelling, expResult);
        System.out.println(" geupdate en gaat nu result uitlezen mbv readArtikel(integer bestellingID) ");
        ArtikelPOJO result = instance.readArtikel(bestellingID);
        System.out.println("resultaat " + result +  " uitgelezen gaat nu vergelijken met expected" );
        
        assertEquals(expResult, result);
        assertEquals(artikelID, (long)result.getArtikelID());
                
    }
     /**
     * Test of updateArtikel2 method, of class ArtikelDAO.
     */
    @Test
    public void testUpdateArtikel2_Bestelling_ArtikelPOJO() {
        System.out.println("updateArtikel2 mbv bestelling-object");
        Integer bestellingID = 2;
        int artikelID = 9;
        
        Bestelling bestelling = new Bestelling();
        bestelling.setBestellingID(bestellingID);
        
        ArtikelPOJO expResult = new ArtikelPOJO();
        expResult.setArtikelID(artikelID);
        expResult.setArtikelNaam("NAAM");
        expResult.setArtikelPrijs(88);
        
        ArtikelDAO instance = new ArtikelDAO();
        instance.updateArtikel2(bestelling, expResult);
        System.out.println(" geupdate en gaat nu result uitlezen mbv readArtikel(integer bestellingID) ");
        ArtikelPOJO result = instance.readArtikel2(bestellingID);
        System.out.println("resultaat " + result +  " uitgelezen gaat nu vergelijken met expected" );
        
        assertEquals(expResult, result);
        assertEquals(artikelID, (long)result.getArtikelID());
                
    }   
    /**
     * Test of updateArtikel3 method, of class ArtikelDAO.
     */
    @Test
    public void testUpdateArtikel3_Bestelling_ArtikelPOJO() {
        System.out.println("updateArtikel3 mbv bestelling-object");
        Integer bestellingID = 2;
        int artikelID = 9;
        
        Bestelling bestelling = new Bestelling();
        bestelling.setBestellingID(bestellingID);
        
        ArtikelPOJO expResult = new ArtikelPOJO();
        expResult.setArtikelID(artikelID);
        expResult.setArtikelNaam("NAAM");
        expResult.setArtikelPrijs(88);
        
        ArtikelDAO instance = new ArtikelDAO();
        instance.updateArtikel3(bestelling, expResult);
        System.out.println(" geupdate en gaat nu result uitlezen mbv readArtikel(integer bestellingID) ");
        ArtikelPOJO result = instance.readArtikel3(bestellingID);
        System.out.println("resultaat " + result +  " uitgelezen gaat nu vergelijken met expected" );
        
        assertEquals(expResult, result);
        assertEquals(artikelID, (long)result.getArtikelID());
                
    }      
}
