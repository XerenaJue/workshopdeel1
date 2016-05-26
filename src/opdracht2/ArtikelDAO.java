package opdracht2;

import opdracht2.ArtikelPOJO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static opdracht2.KlantDAOImpl.logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jeroen
 */
public class ArtikelDAO {
        
    public ArtikelDAO(){}
    
    public ArtikelPOJO createArtikel(ArtikelPOJO artikel) {
                      
        String query = "insert into artikel (artikel_naam, artikel_prijs) values ( ?, ? )";
                           
        try (Connection connection = ConnectionFactory.getMySQLConnection(); 
                    PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); ){
            stmt.setString(1, artikel.getArtikelNaam());
            stmt.setInt(2, artikel.getArtikelPrijs());
            stmt.executeUpdate(); 
            try (ResultSet resultSet = stmt.getGeneratedKeys();) {
                if (resultSet.isBeforeFirst()) {
                    resultSet.next();
                    artikel.setArtikelID(resultSet.getInt(1));
                }
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in createArtikel" );
            ex.printStackTrace();
        }
        return artikel;
    }
            
    public ArtikelPOJO readArtikel(ArtikelPOJO artikel ) {
        
        return readArtikel( artikel.getArtikelID());
    }
       
    public ArtikelPOJO readArtikel(int artikelID) {
                   
        ArtikelPOJO artikel = new ArtikelPOJO();      
        String query = String.format("select artikel_id, artikel_naam, artikel_prijs "
                     + "from artikel "
                     + "where artikel_id = %d", artikelID); 
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                       
            if (resultSet.next()) {
               
                artikel.setArtikelID(resultSet.getInt("artikel_ID"));
                artikel.setArtikelNaam(resultSet.getString("artikel_naam"));
                artikel.setArtikelPrijs(resultSet.getInt("artikel_prijs"));
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in readArtikel" );
            ex.printStackTrace();
        }
        return artikel;
    }
       
    public void updateArtikel(ArtikelPOJO artikel ) {
                      
        String query = String.format("update artikel "
                     + "set artikel_naam = ?, artikel_prijs = ?"
                     + " where artikel_id = %d", artikel.getArtikelID()); 
       
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);){
                        
            stmt.setString(1, artikel.getArtikelNaam());
            stmt.setInt(2 , artikel.getArtikelPrijs());
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println("er gaat hier wat fout in updateArtikel");
            ex.printStackTrace();
        }
    }
      
    public List<ArtikelPOJO> findAlleArtikelen() throws SQLException {
                
        List<ArtikelPOJO> artikelen = new ArrayList<>();
        ArtikelPOJO artikel;
        String query = "SELECT * FROM artikel";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
        	PreparedStatement stmt = connection.prepareStatement(query);
        	ResultSet resultset = stmt.executeQuery();){
            while(resultset.next()) {
                artikel = new ArtikelPOJO();
                artikel.setArtikelID(resultset.getInt("artikel_ID"));
                artikel.setArtikelNaam(resultset.getString("artikel_naam"));
                artikel.setArtikelPrijs(resultset.getInt("artikel_prijs"));
                                
                artikelen.add(artikel);
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in readAlleArtikelen" );
            ex.printStackTrace();
        }
        if (artikelen.isEmpty() )artikelen.add(new ArtikelPOJO());
        return artikelen;
    }
    
    public void deleteArtikel(ArtikelPOJO artikel){ 
        String query = "DELETE FROM artikel WHERE artikel_id = " + artikel.getArtikelID();        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
                PreparedStatement stmt = connection.prepareStatement(query);){
            stmt.executeUpdate();
            System.out.println("Artikel gegevens zijn succesvol verwijderd");
            logger.info("Artikel gegevens zijn succesvol verwijderd");
        } 
        catch (SQLException ex) {
           
            System.out.println("gaat iets fout in DeleteArtikel" );
            ex.printStackTrace();
            logger.info("gaat iets SQLfout in DeleteArtikel");
        }
    }
}
