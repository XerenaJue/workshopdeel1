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
    
    private final KlantDAO klantDAO;
    private final AdresDao adresDAO;
    private final DummyBestelDAO bestellingDAO;    // tijdelijke bestellingDAO
    private final ArtikelDAO artikelDAO;
    private List<Bestelling> bestellingen;
    private List<Klant> klanten;
    private Object[] toDisplay;
    private Object[] fromDisplay;
    private List<ArtikelPOJO> artikelen;  
    private List<Adres> adressen;
    
    public FacadeDatabaseMenu() {
        klantDAO = new KlantDAOImpl();
        adresDAO = new AdresDaoImpl();
        bestellingDAO = new DummyBestelDAO();
        artikelDAO = new ArtikelDAO();
        
        toDisplay = new Object[5];
        toDisplay[0] = new Klant();
        toDisplay[1] = new Adres();
        toDisplay[2] = bestellingen;
        toDisplay[3] = artikelen;
        toDisplay[4] = klanten;
           
    }
    
    public Object[] getToDisplay() {
        
        return this.toDisplay;
    }
    
    public void zoek(Object[] watNuOpScherm) throws SQLException {  
        fromDisplay = watNuOpScherm;                                                                                    
        Klant bestaandeKlant = (Klant)watNuOpScherm[0];
        Adres adres = (Adres)watNuOpScherm[1];
        bestellingen = (List)watNuOpScherm[2];
        if (bestaandeKlant.getKlantID() != 0) {
            adres = findAdres(bestaandeKlant);
            bestaandeKlant = findKlant(bestaandeKlant);
        }
        else {
            bestaandeKlant = findKlant(adres);
            adres = findAdres(bestaandeKlant);
        }
        bestellingen = findBestellingen(bestaandeKlant);
        artikelen = findArtikelen(bestellingen.get(0)); // zoekt artikelen van eerste bestelling in lijst
        
        toDisplay[0] = bestaandeKlant;
        toDisplay[1] = adres;
        toDisplay[2] = bestellingen;
        toDisplay[3] = artikelen;
               
    }
    
    public Klant createKlant(Klant klantNuOpScherm) throws SQLException {
        Klant inTeVoerenKlant = klantNuOpScherm;
        Klant eventueelBestaandeKlant = findKlant(inTeVoerenKlant); 
        if (eventueelBestaandeKlant.getKlantID() == 0 ) {
            klantDAO.create(inTeVoerenKlant);
            System.out.println("klant gemaakt met ID " + inTeVoerenKlant);
        }        
        else {
            System.out.println("Deze klant bestaat reeds: " + eventueelBestaandeKlant);
        }
        Klant verseKlant = findKlant(inTeVoerenKlant); // zet m ook op toDisplay
        zoek(toDisplay);  //update rest van toDisplay
        return verseKlant;
    }
    
    public void deleteKlant() throws SQLException {
        deleteKlant((Klant)toDisplay[0]);
    
    }
    
    private void deleteKlant(Klant overbodigeKlant) throws SQLException {
        bestellingDAO.deleteBestellingen(overbodigeKlant);
        klantDAO.delete(overbodigeKlant);
        this.zoek(toDisplay); // update scherm
    }
    
    private Adres findAdres(Klant bestaandeKlant) throws SQLException {
        
        Adres adres = adresDAO.findAdres(bestaandeKlant);
        toDisplay[1] = adres;
        
        return adres;
    }
        
    private Klant findKlant(Klant bestaandeKlant) throws SQLException{   
        
        Klant ingelezenKlant = klantDAO.findKlant(bestaandeKlant);
        toDisplay[0] = ingelezenKlant;
        
        return ingelezenKlant;
    }
    
    public List<Klant> findKlanten() throws SQLException{
        
        klanten = klantDAO.findAll();
        toDisplay[4] = klanten;
        
        return klanten;
    }
    
/*    public List<Adres> findAlleAdressen() throws SQLException {
        Adres adres1 = new Adres();
        adres1.setStraatnaam("Eresvierweg");
        Adres adres2 = new Adres();
        adres2.setStraatnaam("Boekweg");
        
        List<Adres> adresLijst =  Arrays.asList(adres1,adres2);
        return adresLijst;
    }
*/
    public List<Adres> findAlleAdressen() throws SQLException {
    	adressen = adresDAO.findAll();
    	
    	return adressen;
    }
    private Klant findKlant(Adres klantAdres) throws SQLException{   
      
        Klant ingelezenKlant = klantDAO.findKlant(klantAdres);
        toDisplay[0] = ingelezenKlant;
        
        return ingelezenKlant;
    }
     
    public void updateKlant() throws SQLException {
        updateKlant((Klant)toDisplay[0]);
    }
    
    public void updateKlant(Klant bestaandeKlant) throws SQLException {
        
        klantDAO.update(bestaandeKlant);
    }
    
    private List<Bestelling> findBestellingen(Klant bestaandeKlant) throws SQLException {  
        
        bestellingen = bestellingDAO.readBestelling(bestaandeKlant);          // Deze methode werk nog niet in bestellingDAO!!
    //    toDisplay[2] = bestellingen;                                              //  dit asap fixen    nu Dummy gemaakt  
                                                                                    
        return bestellingen;
    } 
     
    private List<ArtikelPOJO> findArtikelen(Bestelling bestelling) {
       artikelen = new ArrayList<>();
              
       ArtikelPOJO artikel = artikelDAO.readArtikel(bestelling); 
       artikelen.add(artikel);
       ArtikelPOJO artikel2 = artikelDAO.readArtikel2(bestelling); 
       artikelen.add(artikel2);
       ArtikelPOJO artikel3 = artikelDAO.readArtikel3(bestelling); 
       artikelen.add(artikel3);
             
     //  toDisplay[3] = artikelen;
     
       return artikelen;
   }
   

}
