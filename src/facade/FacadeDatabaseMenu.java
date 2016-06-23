/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import opdracht2.*;
import java.util.*;
import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DAO.AdresDaoImpl;
import DAO.ArtikelDAOSQL;
import DAO.BestellingDAO;
import DAO.KlantDAOImpl;
import Interface.AdresDao;
import Interface.ArtikelDao;
import Interface.KlantDAO;
import POJO.Adres;
import POJO.ArtikelBestelling;
import POJO.ArtikelPOJO;
import POJO.Bestelling;
import POJO.Klant;
import java.util.logging.Level;

/**
 *
 * @author jeroen
 */
public class FacadeDatabaseMenu {
    static Logger logger = LoggerFactory.getLogger(FacadeDatabaseMenu.class); 
    private final KlantDAO klantDAO;
    private final AdresDao adresDAO;
    private final BestellingDAO bestellingDAO;    
    private final ArtikelDao artikelDAO;
    private List<Bestelling> bestellingen;
    private List<Bestelling> alleBestellingen;
    private List<Klant> alleKlanten;
    private List<Klant> klantenMetZelfdeAdres;
    private final Object[] toDisplay;
    private List<ArtikelBestelling> besteldeArtikelen;  
    private List<Adres> adressen;
    
    
    public FacadeDatabaseMenu() {
        klantDAO = new KlantDAOImpl();
        adresDAO = new AdresDaoImpl();
        bestellingDAO = new BestellingDAO();
        artikelDAO = new ArtikelDAOSQL();
        
        toDisplay = new Object[6];
        toDisplay[0] = new Klant();
        toDisplay[1] = new ArrayList<Adres>();
        toDisplay[2] = bestellingen;
        toDisplay[3] = besteldeArtikelen;
        toDisplay[4] = alleKlanten;
        toDisplay[5] = klantenMetZelfdeAdres;
 //       ConnectionFactory.useFirebird();  
        ConnectionFactory.useMySQL();
    }
    
    public Object[] getToDisplay() {
        
        return this.toDisplay;
    }
    
    public void zoek(Object[] watNuOpScherm) throws SQLException {  
                                                                                           
        Klant bestaandeKlant = (Klant)watNuOpScherm[0];
        List<Adres> adressenVanKlant = (List<Adres>)watNuOpScherm[1];
        
        bestellingen = (List)watNuOpScherm[2];
        if (findKlant(bestaandeKlant) != null) {       	
        	bestaandeKlant = findKlant(bestaandeKlant);
        	            
        }
        else { 
            adressenVanKlant = findAdres(adressenVanKlant.get(0));
            bestaandeKlant = klantenMetZelfdeAdres.get(0);
            
        }
        adressenVanKlant = findAdres(bestaandeKlant); 
        klantenMetZelfdeAdres = findBewoners(adressenVanKlant.get(0));
      //  bestaandeKlant = klantenMetZelfdeAdres.get(0);
        bestellingen = findBestellingen(bestaandeKlant);
        besteldeArtikelen = findArtikelen(bestellingen.get(0)); // zoekt besteldeArtikelen van eerste bestelling in lijst
        
        toDisplay[0] = bestaandeKlant;
        toDisplay[1] = adressenVanKlant;
        toDisplay[2] = bestellingen;
        toDisplay[3] = besteldeArtikelen;
        toDisplay[5] = klantenMetZelfdeAdres;
               
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
        Klant verseKlant = findKlant(inTeVoerenKlant); // zet in toe voeren klant ook op toDisplay
        zoek(toDisplay);  //update rest van toDisplay
        return verseKlant; // is kopie van die op display staat
    }
    
    public void deleteKlant() throws SQLException {
        deleteKlant((Klant)toDisplay[0]);
    
    }
    
    private void deleteKlant(Klant overbodigeKlant) throws SQLException {
        bestellingDAO.deleteBestellingen(overbodigeKlant);
        klantDAO.delete(overbodigeKlant);
        this.zoek(toDisplay); // update scherm
    }
    
    private List<Adres> findAdres(Klant bestaandeKlant) throws SQLException {
        List<Adres> postbussen = adresDAO.findAdres(bestaandeKlant.getKlantID());
        if (postbussen.isEmpty()) postbussen.add(new Adres());   // om nullpointers en outofbounds te vermijden
        Adres adres = postbussen.get(0); // pakt nu enkel 1e in lijst moet anders en crudmenu aanpassne
        toDisplay[1] = postbussen;//adres;
        
        return postbussen;//adres;
    }
    
    private List<Adres> findAdres(Adres onvolledigAdres) throws SQLException {
        List<Adres> adresMetID =  adresDAO.findAdres(onvolledigAdres.getPostcode(), onvolledigAdres.getHuisnummer());
        if (adresMetID.isEmpty()) adresMetID.add(new Adres());
        toDisplay[1] = adresMetID; //.get(0);
        
        return adresMetID; //.get(0);
    }
        
    private Klant findKlant(Klant bestaandeKlant) throws SQLException{   
        
        Klant ingelezenKlant = klantDAO.findKlant(bestaandeKlant);
        toDisplay[0] = ingelezenKlant;
        
        return ingelezenKlant;
    }
    
    public List<Klant> findKlanten() throws SQLException{
        
        alleKlanten = klantDAO.findAll();
        toDisplay[4] = alleKlanten;
        logger.info("alle klanten: " + alleKlanten);
        return alleKlanten;
    }

    public List<Adres> findAlleAdressen() throws SQLException {
    	adressen = adresDAO.findAll();
    	logger.info("alle adressen: " + adressen);
    	return adressen;
    }
    
     public List<Bestelling> findAlleBestellingen() throws SQLException {
    	alleBestellingen = bestellingDAO.findAlleBestellingen();
    	
    	return alleBestellingen;
    }
     
     public List<ArtikelPOJO> findAlleArtikelen() throws SQLException {
    	
        return artikelDAO.findAlleArtikelen();
    } 
     
    public ArtikelPOJO createArtikel(ArtikelPOJO artikel) {
        
        return artikelDAO.createArtikel(artikel);
    } 
     
    public void deleteArtikel(ArtikelPOJO artikel) {
        
       artikelDAO.deleteArtikel(artikel);
    } 
    
    private List<Klant> findBewoners(Adres klantAdres) throws SQLException{   
        List<Klant> bewoners = klantDAO.findKlant(klantAdres);
        if (bewoners.isEmpty()) bewoners.add(new Klant()); // om arrayoutofbounds en nullpinters te vermijden
     //   Klant ingelezenKlant = bewoners.get(0);
      //  toDisplay[5] = bewoners;
        
        return bewoners;//ingelezenKlant;
    }
     
    public void updateKlant() throws SQLException {
        updateKlant((Klant)toDisplay[0]);
    }
    
    public void updateKlant(Klant bestaandeKlant) throws SQLException {
        
        klantDAO.update(bestaandeKlant);
    }
    
    private List<Bestelling> findBestellingen(Klant bestaandeKlant) throws SQLException {  
        
        bestellingen = bestellingDAO.findAlleBestellingen(bestaandeKlant);          // Deze methode werk nog niet in bestellingDAO!!
    //  toDisplay[2] = bestellingen;           ff kijken of handig nu update of apart       //  dit asap fixen    nu Dummy gemaakt  
                                                                                    
        return bestellingen;
    } 
    
    public Bestelling createBestelling() throws SQLException{
        System.out.println("nog leeg? " + (Klant)toDisplay[0]);
        Bestelling netBesteld = bestellingDAO.createBestelling((Klant)toDisplay[0]);
        
        findBestellingen((Klant)toDisplay[0]);
        
        return netBesteld;
    }
    
    public void deleteBestelling(Bestelling bestelling) throws SQLException{
      
       bestellingDAO.deleteBestelling(bestelling);
    }
    
    public void verwijderAlleBestellingen(Klant klant) throws SQLException{
        bestellingDAO.deleteBestellingen(klant);
    }
    
    public void updateBestelling(ArtikelBestelling artikelBestelling, int bestel_id) throws SQLException{
        Bestelling bestelling = new Bestelling();
        bestelling.setBestelling_id(bestel_id);
        bestellingDAO.addArtikelToBestelling(bestelling, artikelBestelling);
    }
     
    public void removeFromBestelling(Bestelling bestelling, ArtikelBestelling artikelBestelling) {
        
        try {
            bestellingDAO.deleteArtikelFromBestelling(artikelBestelling, bestelling.getBestelling_id());
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(FacadeDatabaseMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public List<ArtikelBestelling> findArtikelen(Bestelling bestelling)  {
        List<ArtikelBestelling> lijstje = null;
        try {
            lijstje = bestellingDAO.readArtikelBestelling(bestelling);
        }
        catch (Exception e ) {
            e.printStackTrace();
        }
        return lijstje;
   }
   public void update(int klant_id, Adres adres) throws SQLException {
	   adresDAO.update(adres);
	   
   }
   
   public void changeConnectionPool() {
       ConnectionFactory.changeConnectionPool();
   }
   
   public void changeDatabase(String naamDatabase) {
      
       if (naamDatabase.equals("MySQL")) {
           ConnectionFactory.useMySQL();
       }
       else if (naamDatabase.equals("Firebird")) {
           ConnectionFactory.useFirebird();
       }
       
   }
   
   public Klant volgendeBewoner() {
        
       if (klantenMetZelfdeAdres.size() == 1) {
           return klantenMetZelfdeAdres.get(0);
       } 
       
       List<Klant> rouleerLijst = new ArrayList<>();
       for (int i = 1; i < klantenMetZelfdeAdres.size(); i++) {
           rouleerLijst.add(klantenMetZelfdeAdres.get(i));
       }
       rouleerLijst.add(klantenMetZelfdeAdres.get(0));
       klantenMetZelfdeAdres = rouleerLijst;
       Klant nuEerste = klantenMetZelfdeAdres.get(0);
       
       if (((Klant)toDisplay[0]).getKlantID() == (nuEerste.getKlantID()) ) {  // equals van Klant pojo niet overridden
           logger.debug("zelfde huidige bewoner als volgende, recur");
           return volgendeBewoner();
       }
       toDisplay[0] = nuEerste;
       return nuEerste;
   }

}
