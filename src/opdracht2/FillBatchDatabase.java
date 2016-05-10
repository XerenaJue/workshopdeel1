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
   
  public static void fillBatchDatabase()  {
    Connection connection = ConnectionFactory.getMySQLConnection();  
    try {  
        connection.setAutoCommit(false);
    
        String alfabet = "abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();
    
            
        int klantID;
        String voornaam;
        String achternaam;
        String tussenvoegsel;
        String email;
        String query = "Insert into klant(voornaam, achternaam, tussenvoegsel, email) values (?, ? ,?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);        
        for (int i = 0; i < 20; i++){
        
            voornaam = generateString(rand, alfabet, rand.nextInt(10));
            achternaam = generateString(rand, alfabet, rand.nextInt(10));
            tussenvoegsel = generateString(rand, alfabet, rand.nextInt(3));
            email = generateString(rand, alfabet, rand.nextInt(10));
        
            statement.setString(1, voornaam);
            statement.setString(2, achternaam);
            statement.setString(3, tussenvoegsel);
            statement.setString(4, email);
        
            statement.addBatch();
        }
    
        int[] count = statement.executeBatch();
        connection.commit();
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
    
    
    
    
    
   public static String generateString(Random rng, String characters, int length) {
    char[] text = new char[length];
    for (int i = 0; i < length; i++)  {
        text[i] = characters.charAt(rng.nextInt(characters.length()));
    }
    return new String(text);
  }
   
}
    

