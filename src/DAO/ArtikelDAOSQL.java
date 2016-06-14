package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import POJO.ArtikelPOJO;
import opdracht2.ConnectionFactory;
import Interface.ArtikelDao;

/**
 *
 * @author jeroen
 */
public class ArtikelDAOSQL implements ArtikelDao {
    static Logger logger = LoggerFactory.getLogger(ArtikelDAOSQL.class);    
    public ArtikelDAOSQL(){}
    
    @Override
    public ArtikelPOJO createArtikel(ArtikelPOJO artikel) {
        logger.trace("creating Artikel in mysql");
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
                    logger.info("created Artikel in database");
                }
            }
        }
        catch (SQLException ex) {
            logger.error("gaat iets SQL fout in createArtikel: "  + ex);
            ex.printStackTrace();
        }
        
        return artikel;
    }
            
    public ArtikelPOJO readArtikel(ArtikelPOJO artikel ) {
        
        return readArtikel( artikel.getArtikelID());
    }
       
    @Override
    public ArtikelPOJO readArtikel(int artikelID) {
        logger.trace("reading artikel....");           
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
                logger.info("artikel is ingelezen");
            }
        }
        catch (SQLException ex) {
            logger.error("gaat iets fout in readArtikel " + ex);
            ex.printStackTrace();
        }
        return artikel;
    }
       
    @Override
    public void updateArtikel(ArtikelPOJO artikel ) {
        logger.trace("updating artikel....");               
        String query = String.format("update artikel "
                     + "set artikel_naam = ?, artikel_prijs = ?"
                     + " where artikel_id = %d", artikel.getArtikelID()); 
       
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);){
                        
            stmt.setString(1, artikel.getArtikelNaam());
            stmt.setInt(2 , artikel.getArtikelPrijs());
            stmt.executeUpdate();
            logger.info("artikel is geupdate ");
        }
        catch (SQLException ex) {
            logger.error("er gaat hier wat fout in updateArtikel" + ex);
            ex.printStackTrace();
        }
    }
      
    @Override
    public List<ArtikelPOJO> findAlleArtikelen() {
        logger.trace("entering findAlleArtikelen()....");             
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
            logger.info("alle artikelen gevonden ");
        }
        catch (SQLException ex) {
            logger.error("gaat iets fout in readAlleArtikelen " + ex );
            ex.printStackTrace();
        }
        if (artikelen.isEmpty() )artikelen.add(new ArtikelPOJO());
        return artikelen;
    }
    
    @Override
    public void deleteArtikel(ArtikelPOJO artikel){ 
        logger.trace("entering deleteArtikel ");
        String query = "DELETE FROM artikel WHERE artikel_id = " + artikel.getArtikelID();        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
                PreparedStatement stmt = connection.prepareStatement(query);){
            stmt.executeUpdate();
            
            logger.info("Artikel gegevens zijn succesvol verwijderd");
        } 
        catch (SQLException ex) {
           
            ex.printStackTrace();
            logger.error("gaat iets SQLfout in DeleteArtikel");
        }
    }
}
