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
public class DummyBestelDAO {

        Connection connection;
        PreparedStatement statement;
        ResultSet resultSet;
        public void updateBestelling(Bestelling bestelling) throws SQLException{
           String SQLStatement ="update bestelling set artikel_aantal = ?, artikel2_aantal = ?, artikel3_aantal = ? where bestelling_id = ?";
        try {
            createCS(SQLStatement);
            statement.setInt(1,bestelling.getArtikel_aantal());
            statement.setInt(2,bestelling.getAantalArtikel2());
            statement.setInt(3,bestelling.getAantalArtikel3());
            statement.setInt(4,bestelling.getBestelling_id());
            statement.executeUpdate();
        }
            catch(SQLException ex){}
            finally{closeCS();}                
                
            
        
    }
    public Bestelling readBestelling(int klant_id) throws SQLException{
        Bestelling bestelling = new Bestelling();
        String SQLStatement = "SELECT * FROM bestelling WHERE bestelling_id =" + klant_id;
        try{
        createCS(SQLStatement);
        //statement.setInt(1, klant_id);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            bestelling.setBestellingID(resultSet.getInt("bestelling_id"));
            bestelling.setKlant(resultSet.getInt("klant_id"));
            bestelling.setAantalArtikel1(resultSet.getInt("artikel_aantal"));
            bestelling.setAantalArtikel2(resultSet.getInt("artikel1_aantal"));
            bestelling.setAantalArtikel3(resultSet.getInt("artikel2_aantal"));
        }
        }
        catch (SQLException e){}
        finally{closeCS();}
        
        return bestelling; 
    }
    public List<Bestelling> readBestelling(Klant klant) {
             
        List<Bestelling> bestellingen = new ArrayList<>();
        
        String query = "select * from bestelling where klant_id = " + klant.getKlantID();
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                       
            while (resultSet.next()) {
               Bestelling bestelling = new Bestelling();      
               bestelling.setBestellingID(resultSet.getInt("bestelling_id"));
               bestelling.setKlant(resultSet.getInt("klant_id"));
               bestelling.setAantalArtikel1(resultSet.getInt("artikel_aantal"));
               bestelling.setAantalArtikel2(resultSet.getInt("artikel2_aantal"));
               bestelling.setAantalArtikel3(resultSet.getInt("artikel3_aantal"));  
               bestellingen.add(bestelling);
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in readBestelling" );
            ex.printStackTrace();
        }
        if (bestellingen.isEmpty() )bestellingen.add(new Bestelling());
        return bestellingen;
    }
    
    public Bestelling createBestelling(Klant klant)throws SQLException {
        
        Bestelling bestelling = new Bestelling();
        bestelling.setKlant(klant.getKlantID());
        
        String query = "insert into bestelling (klant_id) values (" + klant.getKlantID() + ")" ;
        try (Connection connection = ConnectionFactory.getMySQLConnection(); 
                    PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); ){
            stmt.executeUpdate(); 
            try (ResultSet resultSet = stmt.getGeneratedKeys();) {
                if (resultSet.isBeforeFirst()) {
                    resultSet.next();
                    bestelling.setBestellingID(resultSet.getInt(1));
                }
            }
        }
        return bestelling;
    }
    
    public void deleteBestelling(Bestelling bestelling) throws SQLException {
    
        String query = "delete from bestelling where bestelling_id = " + bestelling.getBestelling_id(); 
        try (Connection connection = ConnectionFactory.getMySQLConnection(); 
                    PreparedStatement stmt = connection.prepareStatement(query); ){
            stmt.executeUpdate();
        }
    }
    
    public void deleteBestellingen(Klant klant) throws SQLException{
    
        String query = "delete from bestelling where klant_id = " + klant.getKlantID(); 
        try (Connection connection = ConnectionFactory.getMySQLConnection(); 
                    PreparedStatement stmt = connection.prepareStatement(query); ){
            stmt.executeUpdate();
        }
    }
    public List<Bestelling> findAlleBestellingen() {
             
        List<Bestelling> bestellingen = new ArrayList<>();
        
        String query = "select * from bestelling " ;
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                       
            while (resultSet.next()) {
               Bestelling bestelling = new Bestelling();      
               bestelling.setBestellingID(resultSet.getInt("bestelling_id"));
               bestelling.setKlant(resultSet.getInt("klant_id"));
               bestelling.setAantalArtikel1(resultSet.getInt("artikel_aantal"));
               bestelling.setAantalArtikel2(resultSet.getInt("artikel2_aantal"));
               bestelling.setAantalArtikel3(resultSet.getInt("artikel3_aantal"));  
               bestellingen.add(bestelling);
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in readAlleBestellingen" );
            ex.printStackTrace();
        }
        if (bestellingen.isEmpty() )bestellingen.add(new Bestelling());
        return bestellingen;
    }
    
    
        

    public void createBestelling(Bestelling bestelling)throws SQLException{
        try {
           String SQLStatement = "insert into bestelling (klant_id, artikel_aantal, artikel2_aantal, artikel3_aantal) VALUES (?,?,?,?)";
            connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(SQLStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, bestelling.getKlant_id());
            statement.setInt(2, bestelling.getArtikel_aantal());
            statement.setInt(3, bestelling.getAantalArtikel2());
            statement.setInt(4, bestelling.getAantalArtikel3());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                bestelling.setBestellingID(resultSet.getInt(1));
            }
 

        }
            catch(SQLException ex){}
            finally{closeCS();}                
                
                
                
    }
    
        public void closeCS(){
        {
            // kijk of er verbinding is en zo ja sluit deze
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }   
        }
    }
            public void createCS(String SQLStatement) throws SQLException{
        connection = ConnectionFactory.getMySQLConnection();
        statement = connection.prepareStatement(SQLStatement);
    }
}

