/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.rsvier;

/**
 *
 * @author jeroen
 */
public class ArtikelPOJO {
    private String artikelNaam;
    private Integer artikelID;
    private Integer artikelPrijs;
    
    public ArtikelPOJO() {}
    
    public String getArtikelNaam() {
        return artikelNaam;
    }

   
    public void setArtikelNaam(String artikelNaam) {
        this.artikelNaam = artikelNaam;
    }

    
    public Integer getArtikelID() {
        return artikelID;
    }

    
    public void setArtikelID(Integer artikelID) {
        this.artikelID = artikelID;
    }

    
    public Integer getArtikelPrijs() {
        return artikelPrijs;
    }

    
    public void setArtikelPrijs(Integer artikelPrijs) {
        this.artikelPrijs = artikelPrijs;
    }
    
    @Override
    public String toString() {
        return "artikel_id: " + artikelID + ", artikel_naam: " + artikelNaam 
                + ", prijs: " + artikelPrijs; 
    }

}

