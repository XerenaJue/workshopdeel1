/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import java.sql.*;


/**
 *
 * @author jeroen
 */
public class DummyBestelDAO {
    
    public Bestelling readBestelling(Klant klant) {
             
        Bestelling bestelling = new Bestelling();      
        String query = "select * from bestelling where klant_id = " + klant.getKlantID();
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                       
            if (resultSet.next()) {
               
               bestelling.setBestellingID(resultSet.getInt("bestelling_id"));
               bestelling.setKlant(resultSet.getInt("klant_id"));
               bestelling.setAantalArtikel1(resultSet.getInt("artikel_aantal"));
               bestelling.setAantalArtikel2(resultSet.getInt("artikel2_aantal"));
               bestelling.setAantalArtikel3(resultSet.getInt("artikel3_aantal"));  
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in readBestelling" );
            ex.printStackTrace();
        }
        return bestelling;
                    
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
}

