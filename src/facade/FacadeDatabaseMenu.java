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
    private Object[] fromDisplay;
    private List<ArtikelPOJO> artikelen;   
    
    public FacadeDatabaseMenu() {
        
        toDisplay = new Object[4];
        toDisplay[0] = new Klant();
        toDisplay[1] = new Adres();
        toDisplay[2] = new Bestelling();
        toDisplay[3] = artikelen;
           
    }
    
    public Object[] getToDisplay() {
        
        return this.toDisplay;
    }
    
    public void zoek(Object[] watNuOpScherm) throws SQLException , ClassNotFoundException{  //Cnotfoundeception is onnodig@bestelling
        fromDisplay = watNuOpScherm;                                                                                    
        Klant bestaandeKlant = (Klant)watNuOpScherm[0];
        Adres adres = (Adres)watNuOpScherm[1];
        Bestelling bestelling = (Bestelling)watNuOpScherm[2];
        if (bestaandeKlant.getKlantID() != 0) {
            adres = findAdres(bestaandeKlant);
            bestaandeKlant = findKlant(bestaandeKlant);
        }
        else {
            bestaandeKlant = findKlant(adres);
            adres = findAdres(bestaandeKlant);
        }
        bestelling = findBestelling(bestaandeKlant);
        artikelen = findArtikelen(bestelling);
        
        toDisplay[0] = bestaandeKlant;
        toDisplay[1] = adres;
        toDisplay[2] = bestelling;
        toDisplay[3] = artikelen;
               
    }
    
    public void createKlant(Klant klantNuOpScherm) throws SQLException {
        Klant inTeVoerenKlant = klantNuOpScherm;
        klantDAO = new KlantDAOImpl();
        
        Klant eventueelBestaandeKlant = findKlant(inTeVoerenKlant);
        if (eventueelBestaandeKlant.getKlantID() == 0 ) {
            klantDAO.create(inTeVoerenKlant);
            System.out.println("klant gemaakt met ID " + inTeVoerenKlant);
        }        
        else {
            System.out.println("Deze klant bestaat reeds: " + eventueelBestaandeKlant);
        }
        
        
    }
    
    
    private Adres findAdres(Klant bestaandeKlant) throws SQLException {
        
        adresDAO = new AdresDaoImpl();
        Adres adres = adresDAO.findAdres(bestaandeKlant);
        toDisplay[1] = adres;
        
        return adres;
    }
    
    
    
    private Klant findKlant(Klant bestaandeKlant) throws SQLException{   
        
        klantDAO = new KlantDAOImpl();
        Klant ingelezenKlant = klantDAO.findKlant(bestaandeKlant);
        toDisplay[0] = ingelezenKlant;
        
        return ingelezenKlant;
    }
    
     private Klant findKlant(Adres klantAdres) throws SQLException{   
      
        klantDAO = new KlantDAOImpl();
        Klant ingelezenKlant = klantDAO.findKlant(klantAdres);
        toDisplay[0] = ingelezenKlant;
        
        return ingelezenKlant;
    }
    private Bestelling findBestelling(Klant bestaandeKlant) throws SQLException, ClassNotFoundException {  
        int klantID = bestaandeKlant.getKlantID();
        bestellingDAO = new BestellingDAO();
        Bestelling bestelling = bestellingDAO.readBestelling(klantID);          // Deze methode werk nog niet in bestellingDAO!!
        toDisplay[2] = bestelling;                                              //  dit asap fixen      
                                                                                    
        return bestelling;
    } 
     
   private List<ArtikelPOJO> findArtikelen(Bestelling bestelling) {
       artikelen = new ArrayList<>();
       artikelDAO = new ArtikelDAO();
       ArtikelPOJO artiekeltje = artikelDAO.readArtikel(bestelling); 
       artikelen.add(artiekeltje);
       toDisplay[3] = artikelen;
       System.out.println("klantID van bestelling in FacadeDatabaseMenu.findArtikele... : "+ bestelling.getKlant());
       return artikelen;
   }
   

}
