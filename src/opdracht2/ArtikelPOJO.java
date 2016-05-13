/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;
import org.apache.commons.lang3.builder.*;

/**
 *
 * @author jeroen
 */
public class ArtikelPOJO {
    private String artikelNaam;
    private Integer artikelID = 0;
    private Integer artikelPrijs = 0;
    
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

            
    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 29).append(artikelID).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof ArtikelPOJO))
            return false;
        if (obj == this)
            return true;

        ArtikelPOJO anderArtikel = (ArtikelPOJO) obj;
        return new EqualsBuilder().
            append(artikelID, anderArtikel.getArtikelID()).
            isEquals();
    }
        
    
    
}

