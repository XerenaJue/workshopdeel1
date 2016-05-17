/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;
import java.sql.*;
import java.util.*;

/**
 *
 * @author jeroen
 */
public class FillBatchDatabase {
  
    private final static String ALFABET = "abcdefghijklmnopqrstuvwxyz";
    private static Random rand = new Random(1000);
    
    public static void fillBatchDatabase()  {
         fillBatchDatabase(20);   
    } 
    public static void fillBatchDatabase(int aantal)  {
        
        try { 
            addSomeRandomKlanten(aantal);
            addSomeBestellingen((int)(aantal * 0.9));
        }
        catch (SQLException ex) {
            System.out.println("er gaat hier wat fout in batchschrijven");
            ex.printStackTrace();
        }
    } 
    
    public static void addSomeRandomKlanten(int aantal) throws SQLException{
        String query = "Insert into klant(voornaam, achternaam, tussenvoegsel, email, "
                    + "straatnaam, postcode, toevoeging, huisnummer, woonplaats)"
                    + " values (?, ? ,?, ?, ?, ?, ?, ?, ? )";
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
                PreparedStatement statement = connection.prepareStatement(query);) { 
       
            connection.setAutoCommit(false);
                               
            for (int i = 0; i < aantal; i++){
        
                String voornaam = generateString(rand, ALFABET, 2 + rand.nextInt(10));
                String achternaam = generateString(rand, ALFABET, 2 +  rand.nextInt(10));
                String tussenvoegsel = generateString(rand, ALFABET, rand.nextInt(3));
                String email = generateEmail();
                String straatnaam = generateString(rand, ALFABET, 2 + rand.nextInt(10)); 
                String postcode = generatePostcode(); 
                String toevoeging = generateString(rand, "123456789abcdefgh", rand.nextInt(2)); 
                int huisnummer = rand.nextInt(1000);
                String woonplaats = generateString(rand, ALFABET, 2 + rand.nextInt(12)); 
                       
                statement.setString(1, voornaam);
                statement.setString(2, achternaam);
                statement.setString(3, tussenvoegsel);
                statement.setString(4, email);
                statement.setString(5, straatnaam);
                statement.setString(6, postcode);
                statement.setString(7, toevoeging);
                statement.setInt(8, huisnummer);
                statement.setString(9, woonplaats);
        
                statement.addBatch();
            }
            int[] count = statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        }
    }
    
    public static void addSomeBestellingen(int aantal) throws SQLException{
        String query = "Insert into bestelling(klant_id, artikel_id, artikel_naam, artikel_aantal, artikel_prijs )"
                + " values (?, ?, ?, ?, ? )";
        
        try (Connection connection = ConnectionFactory.getMySQLConnection(); 
                PreparedStatement statement = connection.prepareStatement(query); ) {  
        
            connection.setAutoCommit(false);
                
            for (int i = 1; i < aantal; i++){
                statement.setInt(1, i);
                statement.setInt(2, 1 + rand.nextInt(8));
                statement.setString(3, generateString(rand, ALFABET, 5 + rand.nextInt(5)));
                statement.setInt(4, 1 + rand.nextInt(5));
                statement.setInt(5, 1 + rand.nextInt(100));
                statement.addBatch();
            }
            int[] count = statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        }            
    }
    
    public static String generateEmail() {
        
        String emailHuis = generateString(rand, ALFABET, 3 + rand.nextInt(8));
        String emailPlaats = generateString(rand, ALFABET, 3 + rand.nextInt(8));
        String emailLand = generateString(rand, "nlcomorg", 2 + rand.nextInt(2));
        
        return emailHuis + "@" + emailPlaats + "." + emailLand; 
    }
    
    public static String generatePostcode() {
        
        String postcode = "";
        postcode +=  1000 + rand.nextInt(9000);
        postcode += generateString(rand, ALFABET.toUpperCase(), 2);
        
        return postcode; 
    }
    
    public static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)  {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
   
    public static void clearDatabase() {
        try (Connection connection = ConnectionFactory.getMySQLConnection();){ 
            
            String query = "delete from bestelling";
            PreparedStatement statement = connection.prepareStatement(query);        
            statement.executeUpdate();
            
            query = "delete from klant";   
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            
            query = "ALTER TABLE bestelling AUTO_INCREMENT = 1";   
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            
            query = "ALTER TABLE klant AUTO_INCREMENT = 1";   
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
                   
        }
        catch (SQLException ex) {
            System.out.println("er gaat hier wat fout in batchschrijven");
            ex.printStackTrace();
        }
    }
}
    

