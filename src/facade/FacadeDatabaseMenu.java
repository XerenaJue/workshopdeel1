/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import opdracht2.*;
import java.util.*;
import java.sql.*;

/**
 *
 * @author jeroen
 */
public class FacadeDatabaseMenu {
    
    private KlantDAO klantDAO;
    private AdresDao adresDAO;
    private BestellingDAO bestellingDAO;
    private ArtikelDAO artikelDAO;
    private List<Bestelling> bestellingen;
    private List<Klant> klanten;
    private Object[] toDisplay;
    
    public FacadeDatabaseMenu() {
        
        toDisplay = new Object[2];
        toDisplay[0] = new Klant();
        toDisplay[1] = new Adres();
           
    }
    
    public Object[] getToDisplay() {
        
        return this.toDisplay;
    }
    
    public void zoek(Object[] watNuOpScherm) throws SQLException {
        
        Klant bestaandeKlant = (Klant)watNuOpScherm[0];
        Adres adres = (Adres)watNuOpScherm[1];
        
        if (bestaandeKlant.getKlantID() != 0) {
            adres = findAdres(bestaandeKlant);
        }
        else {
            bestaandeKlant = findKlant(adres);
            adres = findAdres(bestaandeKlant);
        }
           
        toDisplay[0] = bestaandeKlant;
        toDisplay[1] = adres;
    }
    
    
    public Adres findAdres(Klant bestaandeKlant) throws SQLException {
        
        adresDAO = new AdresDaoImpl();
        Adres adres = adresDAO.findAdres(bestaandeKlant);
        toDisplay[1] = adres;
                
        return adres;
    }
    
    
    
    public Klant findKlant(Klant bestaandeKlant) throws SQLException{   
        
        klantDAO = new KlantDAOImpl();
        Klant ingelezenKlant = klantDAO.findKlant(bestaandeKlant);
        toDisplay[0] = ingelezenKlant;
        
        return ingelezenKlant;
    }
    
     public Klant findKlant(Adres klantAdres) throws SQLException{   
        
        klantDAO = new KlantDAOImpl();
        Klant ingelezenKlant = klantDAO.findKlant(klantAdres);
        toDisplay[0] = ingelezenKlant;
        
        return ingelezenKlant;
    }
}
