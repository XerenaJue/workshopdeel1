package nl.rsvier;

import nl.rsvier.ArtikelPOJO;
import java.sql.*;
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
    private ArtikelPOJO artikel;
    private Connection connection;
    
    public ArtikelDAO(ArtikelPOJO artikel){
        this.artikel = artikel;
     }
    
    public ArtikelPOJO readArtikel(int bestellingID) {
              
        String query = "select artikel_id, artikel_naam, artikel_prijs "
                     + "from bestelling "
                     + "where bestelling_id = " + bestellingID; 
        
        try {
            connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(); 
            
            if (resultSet.next()) {
                
                artikel.setArtikelID(Integer.parseInt(resultSet.getString(1)));
                artikel.setArtikelNaam(resultSet.getString(2));
                artikel.setArtikelPrijs(Integer.parseInt(resultSet.getString(3)));
            }
            
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout" );
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
        return this.artikel;
    }
    
    public void updateArtikel(String artikelNaam, int bestellingID) {
        String query = "update bestelling "
                     + "set artikel_naam = ?"
                     + "where bestelling_id = ? "; 
        try {
            connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, artikelNaam);
            stmt.setInt(2 , bestellingID);
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println("er gaat hier wat fout int updateArtikel");
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
    
    public void updateArtikel(int bestellingID) {
        String query = "update bestelling "
                     + "set artikel_id = ?, artikel_naam = ?, artikel_prijs = ?"
                     + " where bestelling_id =" + bestellingID; 
       
        try {
            connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1 , artikel.getArtikelID());
            stmt.setString(2, artikel.getArtikelNaam());
            stmt.setInt(3 , artikel.getArtikelPrijs());
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println("er gaat hier wat fout int updateArtikel enkel id");
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
