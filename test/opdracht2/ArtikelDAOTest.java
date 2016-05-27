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
    public void testReadArtikel_ArtikelPOJO() {
        System.out.println("readArtikel mbv artikelpojo-object");
        int artikelID = 7;
        ArtikelPOJO expResult = new ArtikelPOJO();
        
        String query = "select artikel_id, artikel_naam, artikel_prijs "
                     + "from artikel "
                     + "where artikel_id = " + artikelID; 
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                        
            if (resultSet.next()) {
               
                expResult.setArtikelID(resultSet.getInt(1));
                expResult.setArtikelNaam(resultSet.getString(2));
                expResult.setArtikelPrijs(resultSet.getInt(3));
                System.out.println("eerste lezing, expResult: " + expResult);
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in testReadArtikel " );
            ex.printStackTrace();
        }
                
        ArtikelDAO instance = new ArtikelDAO();
       
        ArtikelPOJO nogEenArtikel = new ArtikelPOJO();
        nogEenArtikel.setArtikelID(artikelID);
              
        ArtikelPOJO result = instance.readArtikel(nogEenArtikel);
        System.out.println("tweede lezing  gedaan result: " + result);
                
        assertEquals(expResult, result);
    }
       

    /**
     * Test of updateArtikel method, of class ArtikelDAO.
     */
    @Test
    public void testUpdateArtikel_ArtikelPOJO() {
        System.out.println("updateArtikel mbv bestellingID-int");
        
        int artikelPrijs = 89;
        int artikelID = 1;
        String artikelNaam = "testNaam";
        
        ArtikelPOJO expResult = new ArtikelPOJO();
        expResult.setArtikelID(artikelID);
        expResult.setArtikelNaam(artikelNaam);
        expResult.setArtikelPrijs(artikelPrijs);
        
        ArtikelDAO instance = new ArtikelDAO();
        instance.updateArtikel(expResult);
        System.out.println("gaat nu result uitlezen mbv readArtikel artID ");
        ArtikelPOJO result = instance.readArtikel(artikelID);
        System.out.println("resultaat " + result +  " uitgelezen gaat nu vergelijken met expected" );
        
        assertEquals(expResult, result);
                       
    }

    @Test
    public void testCreateArtikel_ArtikelPOJO() {
        System.out.println("createArtikel ");
        
        int artikelPrijs = 70;
        String artikelNaam = "testCreate";
        
        ArtikelPOJO expResult = new ArtikelPOJO();
        expResult.setArtikelNaam(artikelNaam);
        expResult.setArtikelPrijs(artikelPrijs);
        
        ArtikelDAO instance = new ArtikelDAO();
        ArtikelPOJO result = instance.createArtikel(expResult);
        
        
        System.out.println("resultaat " + result +  " uitgelezen gaat nu vergelijken met expected" );
        
        assertEquals(expResult.getArtikelNaam(), result.getArtikelNaam());
        assertEquals(expResult.getArtikelPrijs(), result.getArtikelPrijs());
          
    }
    
    @Test
    public void testDeleteArtikel_ArtikelPOJO() {
        System.out.println("deleteArtikel ");
        
        int artikelID = 7;
            
        ArtikelPOJO toDelete = new ArtikelPOJO();
        toDelete.setArtikelID(artikelID);
               
        ArtikelDAO instance = new ArtikelDAO();
        instance.deleteArtikel(toDelete);
        
        ArtikelPOJO expResult = new ArtikelPOJO();
        ArtikelPOJO result = instance.readArtikel(toDelete);
             
        System.out.println("resultaat " + result +  " uitgelezen als niet meer bestaand artikel gaat nu vergelijken met expected" );
        
        assertEquals(expResult, result);
                 
    }
    
}
