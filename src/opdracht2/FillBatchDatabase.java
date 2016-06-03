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
    private static boolean firebird = false; 
    
    public static void fillBatchDatabase(String databaseMySQLofFirebird)  {
        if (databaseMySQLofFirebird.equals("Firebird")) firebird = true;
        else firebird = false;
        fillBatchDatabase(20);   
    }
    
    public static void fillBatchDatabase()  {
         fillBatchDatabase(20);   
    } 
    public static void fillBatchDatabase(int aantal)  {
        
        try { 
            addSomeRandomKlanten(aantal);
            addSomeRandomAdresses(aantal);
            koppelKlantenAdressen(aantal);
            addSomeBestellingen(aantal);
            addSomeArtikelen(8);
            koppelBestellingArtikel((int)(aantal * 1.4));
        }
        catch (SQLException ex) {
            System.out.println("er gaat hier wat fout in batchschrijven");
            ex.printStackTrace();
        }
    } 
    
    public static void addSomeRandomKlanten(int aantal) throws SQLException{
        String query1 = "INSERT INTO klant(voornaam, achternaam, tussenvoegsel, email) VALUES (?, ? ,?, ?)";
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
                PreparedStatement statement1 = connection.prepareStatement(query1); ){ 
       
            connection.setAutoCommit(false);
                               
            for (int i = 0; i < aantal; i++){
        
                String voornaam = generateString(rand, ALFABET, 2 + rand.nextInt(10));
                String achternaam = generateString(rand, ALFABET, 2 +  rand.nextInt(10));
                String tussenvoegsel = generateString(rand, ALFABET, rand.nextInt(3));
                String email = generateEmail();
                                       
                statement1.setString(1, voornaam);
                statement1.setString(2, achternaam);
                statement1.setString(3, tussenvoegsel);
                statement1.setString(4, email);
                statement1.addBatch();
            
            }
            
            int[] count = statement1.executeBatch();
                                     
            connection.commit();
            connection.setAutoCommit(true);
        }
    }
    
    public static void addSomeRandomAdresses(int aantal) throws SQLException{
       
        String query2 = "INSERT INTO adres (straatnaam, postcode, toevoeging, huisnummer, woonplaats)"
					+ " VALUES (?, ?, ?,?, ?)";
       
        try (Connection connection = ConnectionFactory.getMySQLConnection();
                PreparedStatement statement2 = connection.prepareStatement(query2); ){ 
       
            connection.setAutoCommit(false);
                               
            for (int i = 0; i < aantal; i++){
        
                String straatnaam = generateString(rand, ALFABET, 2 + rand.nextInt(10)); 
                String postcode = generatePostcode(); 
                String toevoeging = generateString(rand, "123456789abcdefgh", rand.nextInt(2)); 
                int huisnummer = rand.nextInt(1000);
                String woonplaats = generateString(rand, ALFABET, 2 + rand.nextInt(12)); 
                                
                statement2.setString(1, straatnaam);
                statement2.setString(2, postcode);
                statement2.setString(3, toevoeging);
                statement2.setInt(4, huisnummer);
                statement2.setString(5, woonplaats);
                       
                statement2.addBatch();
                
            }
            
            statement2.executeBatch();
           
            connection.commit();
            connection.setAutoCommit(true);
        }
    }
    public static void koppelKlantenAdressen(int aantal) throws SQLException{
       
        String query = "INSERT INTO klant_has_adres (klant_klant_id, adres_adres_id) VALUES (?, ?)";
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
                PreparedStatement statement = connection.prepareStatement(query); ){ 
       
            connection.setAutoCommit(false);
                               
            for (int i = 1; i <= aantal; i++){
                       
                statement.setInt(1, i);
                statement.setInt(2, i);
                       
                statement.addBatch();
            }
            statement.executeBatch();
           
            connection.commit();
            connection.setAutoCommit(true);
        }
    }
          
    public static void addSomeBestellingen(int aantal) throws SQLException{
        String query = "Insert into bestelling(klant_klant_id ) values (?)";
        
        try (Connection connection = ConnectionFactory.getMySQLConnection(); 
                PreparedStatement statement = connection.prepareStatement(query); ) {  
        
            connection.setAutoCommit(false);
                
            for (int i = 1; i <= aantal; i++){
                statement.setInt(1, i);
                if (i%2 == 0) statement.addBatch();
                statement.addBatch();
            }
            int[] count = statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        }            
    }
    
    public static void addSomeArtikelen(int aantal) throws SQLException{
        String query = "Insert into artikel(artikel_naam, artikel_prijs ) values (?, ?)";
        
        try (Connection connection = ConnectionFactory.getMySQLConnection(); 
                PreparedStatement statement = connection.prepareStatement(query); ) {  
        
            connection.setAutoCommit(false);
                
            for (int i = 1; i <= aantal; i++){
                statement.setString(1, generateString(rand, ALFABET, 2 + rand.nextInt(10)));
                statement.setInt(2, rand.nextInt(100) + 10);
                statement.addBatch();
            }
            int[] count = statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        }            
    }
    private static void koppelBestellingArtikel(int aantalBestellingen) throws SQLException{
       
        String query = "INSERT INTO bestelling_has_artikel (bestelling_bestelling_id, artikel_artikel_id, aantal_artikelen)"
                        + " VALUES (?, ?, ?)";
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
                PreparedStatement statement = connection.prepareStatement(query); ){ 
       
            connection.setAutoCommit(false);
            int maxAantalGekocht = 20;
            int aantalInCatalogus = 5 ;
            for (int i = 1; i <= aantalBestellingen; i++){
                       
                statement.setInt(1, i);
                statement.setInt(2, rand.nextInt(aantalInCatalogus)+1);
                statement.setInt(3, rand.nextInt(maxAantalGekocht)+ 1);
                
                statement.addBatch();
            }
            statement.executeBatch();
           
            connection.commit();
            connection.setAutoCommit(true);
        }
    }
    
    private static String generateEmail() {
        
        String emailHuis = generateString(rand, ALFABET, 3 + rand.nextInt(8));
        String emailPlaats = generateString(rand, ALFABET, 3 + rand.nextInt(8));
        String emailLand = generateString(rand, "nlcomorg", 2 + rand.nextInt(2));
        
        return emailHuis + "@" + emailPlaats + "." + emailLand; 
    }
    
    private static String generatePostcode() {
        
        String postcode = "";
        postcode +=  1000 + rand.nextInt(9000);
        postcode += generateString(rand, ALFABET.toUpperCase(), 2);
        
        return postcode; 
    }
    
    private static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)  {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
    
    public static void clearDatabase(String databaseMySQLofFirebird)  {
        if (databaseMySQLofFirebird.equals("Firebird")) firebird = true;
        else firebird = false;
        clearDatabase();   
    }
   
    public static void clearDatabase() {
        try (Connection connection = ConnectionFactory.getMySQLConnection();){ 
            
            String query = "delete from klant_has_adres";   
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
                        
            query = "delete from bestelling_has_artikel";
            statement = connection.prepareStatement(query);        
            statement.executeUpdate();
            
            query = "delete from bestelling";
            statement = connection.prepareStatement(query);        
            statement.executeUpdate();
            
            query = "delete from artikel";
            statement = connection.prepareStatement(query);        
            statement.executeUpdate();
            
            query = "delete from adres";
            statement = connection.prepareStatement(query);        
            statement.executeUpdate();
            
            query = "delete from klant";   
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            
            if (firebird) resetAutoGeneratorFirebirdDatabase();
            else resetAutoIncrementMySQL();
        
        }
        catch (SQLException ex) {
            System.out.println("er gaat hier wat fout in batchschrijven");
            ex.printStackTrace();
        }
    }
    
    private static void resetAutoGeneratorFirebirdDatabase() {
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();){ 
            
           String query = "SET GENERATOR GEN_ADRES_ID TO 0";
           PreparedStatement statement = connection.prepareStatement(query);
           statement.executeUpdate();
           query = "SET GENERATOR GEN_ARTIKEL_ID TO 0";
           statement = connection.prepareStatement(query);
           statement.executeUpdate();
           query = "SET GENERATOR GEN_BESTELLING_ID TO 0";
           statement = connection.prepareStatement(query);
           statement.executeUpdate();
           query = "SET GENERATOR GEN_KLANT_ID TO 0";
           statement = connection.prepareStatement(query);
           statement.executeUpdate();
                  
        }
        
        catch (SQLException ex) {
            System.out.println("er gaat hier wat fout in batchschrijven");
            ex.printStackTrace();
        }
               
    }
    
    private static void resetAutoIncrementMySQL() {
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();){ 
            
            String query = "ALTER TABLE adres AUTO_INCREMENT = 1";   
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
            
            query = "ALTER TABLE klant AUTO_INCREMENT = 1";   
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            
            query = "ALTER TABLE bestelling AUTO_INCREMENT = 1";   
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            
            query = "ALTER TABLE artikel AUTO_INCREMENT = 1";   
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
                  
        }
        
        catch (SQLException ex) {
            System.out.println("er gaat hier wat fout in batchschrijven");
            ex.printStackTrace();
        }
    }
}
    

