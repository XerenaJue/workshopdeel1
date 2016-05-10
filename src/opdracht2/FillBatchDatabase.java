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
  
    public static void fillBatchDatabase()  {
        Connection connection = ConnectionFactory.getMySQLConnection();  
        try { 
            connection.setAutoCommit(false);
    
            Random rand = new Random();
                     
            String query = "Insert into klant(voornaam, achternaam, tussenvoegsel, email, "
                    + "straatnaam, postcode, toevoeging, huisnummer, woonplaats)"
                    + " values (?, ? ,?, ?, ?, ?, ?, ?, ? )";
            PreparedStatement statement = connection.prepareStatement(query);        
            
            for (int i = 0; i < 20; i++){
        
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
        catch (SQLException ex) {
            System.out.println("er gaat hier wat fout in batchschrijven");
            ex.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } 
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    
    }   
    
    public static String generateEmail() {
        Random rand = new Random();
        String emailHuis = generateString(rand, ALFABET, 3 + rand.nextInt(8));
        String emailPlaats = generateString(rand, ALFABET, 3 + rand.nextInt(8));
        String emailLand = generateString(rand, "nlcomorg", 2 + rand.nextInt(2));
        
        return emailHuis + "@" + emailPlaats + "." + emailLand; 
    }
    
    public static String generatePostcode() {
        Random rand = new Random();
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
       Connection connection = ConnectionFactory.getMySQLConnection();   
       try { 
            
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
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } 
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
    

