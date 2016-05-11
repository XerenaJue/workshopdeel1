package opdracht2;

import opdracht2.ArtikelPOJO;
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
    
    private Connection connection;
    
    public ArtikelDAO(){
        
     }
    
    public ArtikelPOJO readArtikel(int bestellingID) {
        ArtikelPOJO artikel = new ArtikelPOJO();      
        String query = "select artikel_id, artikel_naam, artikel_prijs "
                     + "from bestelling "
                     + "where bestelling_id = " + bestellingID; 
        
        try {
            connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(); 
            
            if (resultSet.next()) {
               
                artikel.setArtikelID(resultSet.getInt(1));
                artikel.setArtikelNaam(resultSet.getString(2));
                artikel.setArtikelPrijs(resultSet.getInt(3));
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
        return artikel;
    }
    
    public ArtikelPOJO readArtikel(Bestelling bestelling) {
            
        ArtikelPOJO artikel = new ArtikelPOJO();      
        String query = "select artikel_id, artikel_naam, artikel_prijs "
                     + "from bestelling "
                     + "where bestelling_id = " + bestelling.getBestelling(); 
        
        try {
            connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(); 
            
            if (resultSet.next()) {
                
                artikel.setArtikelID(resultSet.getInt(1));
                artikel.setArtikelNaam(resultSet.getString(2));
                artikel.setArtikelPrijs(resultSet.getInt(3));
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
        return artikel;
    }
    
    
    public void updateArtikel(Integer bestellingID, ArtikelPOJO artikel) {
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
  
    
        public void updateArtikel(Bestelling bestelling, ArtikelPOJO artikel) {
        String query = "update bestelling "
                     + "set artikel_id = ?, artikel_naam = ?, artikel_prijs = ?"
                     + " where bestelling_id =" + bestelling.getBestelling(); 
       
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
