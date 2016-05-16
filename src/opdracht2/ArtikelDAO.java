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
    
    
    
    public ArtikelDAO(){}
        
    
    public ArtikelPOJO readArtikel(Bestelling bestelling) {
        
        int bestellingID = bestelling.getBestelling();
        
        return readArtikel(bestellingID);
    }
    
    public ArtikelPOJO readArtikel2(Bestelling bestelling) {
        
        int bestellingID = bestelling.getBestelling();
        
        return readArtikel2(bestellingID);
    }
   
    public ArtikelPOJO readArtikel3(Bestelling bestelling) {
        
        int bestellingID = bestelling.getBestelling();
        
        return readArtikel3(bestellingID);
    }
    
    public ArtikelPOJO readArtikel(int bestellingID ) {
    
        return readArtikel(bestellingID, "1");
    }
    
    public ArtikelPOJO readArtikel2(int bestellingID ) {
    
        return readArtikel(bestellingID, "2");
    }
    
    public ArtikelPOJO readArtikel3(int bestellingID ) {
    
        return readArtikel(bestellingID, "3");
    }
     
    
    private ArtikelPOJO readArtikel(int bestellingID, String artikelNrAlsString) {
        
        if (artikelNrAlsString.equals("1")) artikelNrAlsString = "";
        
        ArtikelPOJO artikel = new ArtikelPOJO();      
        String query = String.format("select artikel%s_id, artikel%s_naam, artikel%s_prijs "
                     + "from bestelling "
                     + "where bestelling_id = %d", artikelNrAlsString, artikelNrAlsString,artikelNrAlsString, bestellingID); 
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
             
            
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
        return artikel;
                    
    }
    
    
    
    public void updateArtikel(Bestelling bestelling, ArtikelPOJO artikel) {
        
        int bestellingID = bestelling.getBestelling();
        
        updateArtikel(bestellingID, artikel);
        
    }
    
    public void updateArtikel(int bestellingID, ArtikelPOJO artikel) {
                    
        updateArtikel(bestellingID, artikel, "1");
    
    }
             
     public void updateArtikel2(Bestelling bestelling, ArtikelPOJO artikel) {
        
        int bestellingID = bestelling.getBestelling();
        
        updateArtikel(bestellingID, artikel, "2");
        
    }
     
    public void updateArtikel3(Bestelling bestelling, ArtikelPOJO artikel) {
        
        int bestellingID = bestelling.getBestelling();
        
        updateArtikel(bestellingID, artikel, "3");
        
    }
    
    
    private void updateArtikel(int bestellingID, ArtikelPOJO artikel, String artikelNrAlsString ) {
        
        if (artikelNrAlsString.equals("1")) artikelNrAlsString = "";
        
        String query = String.format("update bestelling "
                     + "set artikel%s_id = ?, artikel_naam = ?, artikel_prijs = ?"
                     + " where bestelling_id = %d", artikelNrAlsString, bestellingID); 
       
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);){
            
            stmt.setInt(1 , artikel.getArtikelID());
            stmt.setString(2, artikel.getArtikelNaam());
            stmt.setInt(3 , artikel.getArtikelPrijs());
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println("er gaat hier wat fout int updateArtikel enkel id");
            ex.printStackTrace();
        }
    }
      
    
}
