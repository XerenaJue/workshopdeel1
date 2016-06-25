/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import POJO.Adres;
import POJO.ArtikelBestelling;
import POJO.ArtikelPOJO;
import POJO.Bestelling;
import POJO.Klant;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import menu.BestellingScherm;
import menu.CrudInvoerMenu;
import menu.TabelScherm;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jeroenO
 */
public class Controller {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    Object[] nepAppArray;
    Klant klant ;
    Adres adres ;
    Bestelling bestelling;
    List<Bestelling> bestellingen; 
    List<ArtikelPOJO> artikelen;
    private int adresIndex = 0;
    
    private FacadeDatabaseMenu model;
    private CrudInvoerMenu view1;
    private BestellingScherm view2;
    private TabelScherm view3;
    
    public Controller(FacadeDatabaseMenu model, CrudInvoerMenu view, BestellingScherm view2, TabelScherm view3) {
        
        this.model = model;
        this.view1 = view;
        this.view2 = view2;
        this.view3 = view3;
        
       addListenersToView1();
       addListenersToView3();
             
        nepAppArray = model.getToDisplay();
        klant = (Klant)nepAppArray[0];
        List<Adres> adressen = (List<Adres>) nepAppArray[1];
        if (adressen.isEmpty()) adressen.add(new Adres());
        adres = adressen.get(0);
        bestellingen = (List<Bestelling>)nepAppArray[2];
        artikelen = (List<ArtikelPOJO> )nepAppArray[3];
    }
    private void addListenersToView1() {
        
        this.view1.addHandlerToButtonZoek(new ZoekKlant());
        this.view1.addHandlerToButtonMaak(new MaakKlant());
        this.view1.addHandlerToButtonUpdate(new UpdateKlant());
        this.view1.addHandlerToButtonVerwijder(new VerwijderKlant());
        this.view1.addHandlerToButtonBestellingen(new Bestellingen());
        this.view1.addHandlerToButtonVolgendAdres(new VolgendAdres());
        this.view1.addHandlerToButtonNextKlant(new VolgendeBewoner());
        
    }
    private void addListenersToView2() {
        view2.addListenerToMaak(new MaakBestelling());
        view2.addListenerToVerwijder(new VerwijderBestelling());
        view2.addListenerToTabel(new SelectTabelRow());
        view2.addListenerToVerwijderAlle(new VerwijderAlleBestellingen());
        view2.addListenerToVerwijderArtikel(new VerwijderArtikel());
        view2.addListenerToVoegToe(new VoegArtikelToe());
        
    }
    private void addListenersToView3() {
        this.view3.addListenerToCrud(new OpenCrud());
        
    }
    
    class ZoekKlant implements EventHandler {
        
        @Override
        public void handle(Event event) {
            System.out.println("events are happening");
            
            zoekKlant();
            zoekAdresVanKlant();
        }
    }
    class MaakKlant implements EventHandler {
        @Override
        public void handle(Event event) {
            System.out.println("events are happening");
            maakKlant();
            geefAdres();
            zoekKlant();
        }
    }
    class UpdateKlant implements EventHandler {
        @Override
        public void handle(Event event) {
            System.out.println("events are happening");
            updateKlant();
            updateAdres();// moet enkel van deze klant
            
        }
    }
    class VerwijderKlant implements EventHandler {
        @Override
        public void handle(Event event) {
            System.out.println("events are happening");
            deleteKlant();
            zoekKlant();
        }
    }
    class Bestellingen implements EventHandler {
        @Override
        public void handle(Event event) {
            LOGGER.debug("view2, bestellingscherm wordt aangemaakt");
            view2 = new BestellingScherm();
            view2.setLabels(Integer.toString(klant.getKlantID()), klant.getAchternaam());
            addListenersToView2();
            view2.showBestellingen((List) nepAppArray[2] );
            view2.startMenu();
        }
    }
    class MaakBestelling implements EventHandler {
        @Override
        public void handle(Event event) {
            LOGGER.debug("maakbestelling handler");
            plaatsBestelling();
        }
    }
    class VerwijderBestelling implements EventHandler {
        @Override
        public void handle(Event event) {
            LOGGER.debug("verwijderbestelling handler");
            verwijderSelectedBestelling();
            showBestellingen();
            zoekInDezeBestelling();
        }
    }
    class VerwijderAlleBestellingen implements EventHandler {
        @Override
        public void handle(Event event) {
            LOGGER.debug("verwijder alle bestellingen handler");
            verwijderAlleBestellingen();
            showBestellingen();
            zoekInDezeBestelling();
        }
    }
     
    class SelectTabelRow implements EventHandler {
        @Override
        public void handle(Event event) {
            LOGGER.debug("select tablerow bestellingscherm handler");
            zoekInDezeBestelling();
        }
    }
    class VerwijderArtikel implements EventHandler {
        @Override
        public void handle(Event event) {
            LOGGER.debug("verwijder artikel handler");
            verwijderArtikel();
            zoekInDezeBestelling();
                 
        }
    }
    class VoegArtikelToe implements EventHandler {
        @Override
        public void handle(Event event) {
           LOGGER.debug("voegartikeltoe  handler");
            voegArtikelToe();
            zoekInDezeBestelling();
                 
        }
    }
    class VolgendAdres implements EventHandler {
        @Override
        public void handle(Event event) {
            LOGGER.debug("volgend adres handler");
            volgendAdres();
            zoekAdresVanKlant();
        }
    }
    class VolgendeBewoner implements EventHandler {
        @Override
        public void handle(Event event) {
            LOGGER.debug("volgende bewoner handler");
            volgendeBewoner();
           
        }
    }
    class OpenCrud implements EventHandler {
        @Override
        public void handle(Event event) {
            LOGGER.debug("ga naar crudmenu handler");
            openDezeCrud();
        }
    }
       
    private void zoekKlant() {
        LOGGER.debug("zoekKlant()");
        try {
            nepAppArray[0] = klant;
             klant.setKlantID(view1.getKlantID());
              leesSchermKlant();
              leesAdresVanScherm();
              
            model.zoek(nepAppArray);
            nepAppArray = model.getToDisplay();
            showKlant();
            adresIndex = 0;
                
        } catch (SQLException ex) {
            LOGGER.error("zoek in controller sql", ex);
        }
        
    }
    public void maakKlant() {
        LOGGER.debug("maakkKlant()");
        EmailValidator emailVal = EmailValidator.getInstance();
        view1.clearStatusText();
	try {
            if (!emailVal.isValid(view1.getEmail())) {
                view1.falseEmail();
		return;
            }
            klant.setKlantID(view1.getKlantID());
            klant.setAchternaam(view1.getKlantAchternaam());
            klant.setEmail(view1.getEmail());
            klant.setVoornaam(view1.getKlantVoornaam());
            klant.setTussenvoegsel(view1.getTussenvoegsel());
            klant = model.createKlant(klant);
            nepAppArray = model.getToDisplay(); // nepapparray[0] is nu kopie
						// van klant niet klant
            						// zelf!
        } catch (SQLException e) {
            LOGGER.error("oplossen nog maaklant " + e);
        }
    }
    private void showKlant() {
        
        klant = (Klant) nepAppArray[0];
        LOGGER.debug("showKlant(): " + klant);   
        view1.setKlantID(klant.getKlantID());
        view1.setKlantVoornaam(klant.getVoornaam());
        view1.setKlantAchternaam(klant.getAchternaam());
        view1.setTussenvoegsel(klant.getTussenvoegsel());
        view1.setEmail(klant.getEmail());
    }
    
    private void geefAdres(){
        
        leesAdresVanScherm();
        model.geefAdres(klant, adres);
    }
    
    private void leesAdresVanScherm() {
        
        adres = new Adres();
        adres.setPostcode(view1.getPostcode());
        adres.setStraatnaam(view1.getStraatnaam());
        adres.setToevoeging(view1.getToevoeging());
        adres.setHuisnummer(view1.getHuisnummer());
        adres.setWoonplaats(view1.getPlaatsnaam());
        
        List<Adres> adressen = new ArrayList<>();
        nepAppArray[1] = adressen;
        adressen.add(adres);
        
    }
      
    private void volgendeBewoner() {
        
        model.volgendeBewoner();
        nepAppArray = model.getToDisplay();
        showKlant();
        
    }
    private void volgendAdres() {
        LOGGER.debug("volgendAdres()");
            if (adresIndex < ((List<Adres>) nepAppArray[1]).size() - 1 ) {
                adresIndex++;
            }
            else adresIndex = 0;
            
        }
    
    public void zoekAdresVanKlant() {
        LOGGER.debug("zoekAdresVanKlant()");
     //   nepAppArray = model.getToDisplay();
        adres = ((List<Adres>) nepAppArray[1]).get(adresIndex);

        showAdres(adres);
    }
    
    private void showAdres(Adres adres) {
        LOGGER.debug("showAdres(Adres adres) " + adres);    
        view1.setStraatnaam(adres.getStraatnaam());
        view1.setHuisnummer(adres.getHuisnummer());
        view1.setToevoeging(adres.getToevoeging());
        view1.setPostcode(adres.getPostcode());
        view1.setPlaatsnaam(adres.getWoonplaats());
    }
    
   
    public void setKlant(Klant bestaandeKlant) {
        LOGGER.debug("setKlant(Klant bestaandeKlant) " + bestaandeKlant);
	nepAppArray[0] = bestaandeKlant;

    }
    public void setAdres(Adres bestaandAdres) {
        LOGGER.debug("setAdres(Adres bestaandAdres) " + bestaandAdres);
	((List<Adres>)nepAppArray[1]).set(0,bestaandAdres);

    }
    
    public void updateKlant() {
         LOGGER.debug("updateKlant() ") ;
        EmailValidator emailVal = EmailValidator.getInstance();
        view1.clearStatusText();
	try {
            if (!emailVal.isValid(view1.getEmail())) {
                view1.falseEmail();
		return;
            }
           leesSchermKlant();
            model.updateKlant(klant);
            nepAppArray = model.getToDisplay();

	} catch (SQLException ex) {
		System.out.println("Nog op te lossen");
	}
    }
    
    private void leesSchermKlant() {
        
        klant.setKlantID(view1.getKlantID());
            klant.setEmail(view1.getEmail());
            klant.setAchternaam(view1.getKlantAchternaam());
            klant.setVoornaam(view1.getKlantVoornaam());
            klant.setTussenvoegsel(view1.getTussenvoegsel());
        
    }
    
    public void deleteKlant() {
        LOGGER.debug("deleteKlant() ") ;
	try {
            klant.setKlantID(view1.getKlantID());
            model.deleteKlant();
            nepAppArray = model.getToDisplay();
	} catch (SQLException ex) {
            System.out.println("Nog op te lossen");
	}
    }
    private void updateAdres() {
        LOGGER.debug("updateAdres() ") ;
	try {
            adres.setStraatnaam(view1.getStraatnaam());
            adres.setHuisnummer(view1.getHuisnummer());
            adres.setToevoeging(view1.getToevoeging());
            adres.setPostcode(view1.getPostcode());
            adres.setWoonplaats(view1.getPlaatsnaam());
            model.updateAdres(klant, adres);
            nepAppArray = model.getToDisplay();

	} catch (SQLException e) {
            System.out.println("Nog op te lossen" + e);
	}

    }
    
     private void setUpForBestellingen() {
         LOGGER.debug("setUpForBestellingen() ") ;
        List<String> pojoVelden = new ArrayList<>();
        pojoVelden.addAll(Arrays.asList("bestelling_id", "klant_id", "artikel_aantal" ) );
        view2.setUpTabel(pojoVelden);
        
    }
    
     private void plaatsBestelling() {
         LOGGER.debug("plaatsBestelling() ") ;
       try {
            model.zoek(nepAppArray);
            model.createBestelling();
            nepAppArray = model.getToDisplay();
            model.zoek(nepAppArray);
            nepAppArray = model.getToDisplay();
           
            setUpForBestellingen();
            view2.showBestellingen((List) nepAppArray[2] );
         
       }
       catch (SQLException e) {
           LOGGER.error("plaats bestelling ver" + e);
           System.out.println("wordt nu wel ver gegooid deze SQL exc in Bestellingscherm.plaatsbestelling");
       }
   
     }
     
     private void showBestellingen() {
        LOGGER.debug("showBestellingen() ") ;
        try {
            model.zoek(nepAppArray);
            nepAppArray = model.getToDisplay(); 
            view2.showBestellingen((List) nepAppArray[2] );
        } catch (SQLException ex) {
            LOGGER.error("show bestellingen ver" + ex);
        }
     }
     
    private void verwijderSelectedBestelling() {
         LOGGER.debug(" verwijderSelectedBestelling() ") ;
        Bestelling bestelling = view2.getSelectedBestelling();
        try {
            if (bestelling != null ) model.deleteBestelling(bestelling);
        } catch (SQLException ex) {
            LOGGER.error("verwijder bestelling ver" + ex);
        }
    }
    
     private void verwijderAlleBestellingen(){
         LOGGER.debug(" vverwijderAlleBestellingen(") ;
        try{
            model.verwijderAlleBestellingen(klant);
            
            model.zoek(nepAppArray);
            nepAppArray = model.getToDisplay();
            setUpForBestellingen();
          
        }
        catch(SQLException e){
        
        }
    }
    
    private void zoekInDezeBestelling() {
        LOGGER.debug(" zoekInDezeBestelling(") ;
        if (view2.getSelectedBestelling() != null ) {
            
            bestelling =  view2.getSelectedBestelling();
            List indezak = model.findArtikelen(bestelling);
            view2.verversWinkelwagen(indezak);
            setUpInhoudBestellingen();
        }
        else view2.clearArtikelLijst();
    }
     private void setUpInhoudBestellingen() {
         LOGGER.debug("setUpInhoudBestellingen()") ;
        List<String> pojoVelden = new ArrayList<>();
        pojoVelden.addAll(Arrays.asList("artikelNaam", "artikelID", "artikelPrijs", "aantal_artikelen" ) );
        
        view2.setUpBestellingTabel(pojoVelden);
    }
     
    private void voegArtikelToe(){
         LOGGER.debug("voegArtikelToe()") ;
        ArtikelBestelling artikelBestelling = new ArtikelBestelling();
        ArtikelPOJO artikel = new ArtikelPOJO();
        artikel.setArtikelID(view2.getArtikelID());
        artikelBestelling.setArtikelPojo(artikel);
        artikelBestelling.setArtikelenAantal(view2.getArtikelAantal());
        try{
            model.updateBestelling(artikelBestelling, bestelling.getBestelling_id());
            System.out.println("aanpassing wel gelukt");
        }
        catch (SQLException e) {
           e.printStackTrace();
           System.out.println("aanpassing niet gelukt");
        }
    } 
    private void verwijderArtikel() {
         LOGGER.debug("verwijderArtikel()") ;
        if ( view2.getSelectedArtikel() != null    ) {
            ArtikelBestelling artikelBestelling = view2.getSelectedArtikel();
            model.removeFromBestelling(bestelling, artikelBestelling);
        }
    }
    
    private void openDezeCrud() {
        LOGGER.debug("openDezeCrud(), van tabelscherm naar crudmenu") ;   
        view1.prepareMenu();
        if (view3.getSelectedItem() instanceof Klant ) {
            klant = (Klant)view3.getSelectedItem();
            zoekKlant();
            zoekAdresVanKlant();
        }
        else if (view3.getSelectedItem() instanceof Adres ) {
            adres = (Adres)view3.getSelectedItem();
            ArrayList adressen = new ArrayList();
            adressen.add(adres);
            klant = new Klant(); // om scherm te verversen
            nepAppArray[1] =  adressen;
            LOGGER.debug("zoek per adres " + adressen);
           System.out.println("zoek per adres " + adressen);
            zoekKlant();
            zoekAdresVanKlant();        
        }    
        else if (view3.getSelectedItem() instanceof Bestelling ) {
            bestelling = (Bestelling)view3.getSelectedItem();
            klant = new Klant();
            klant.setKlantID(bestelling.getKlant_id());
            zoekKlant();
            zoekAdresVanKlant();        
        }    
        view1.showMenu();
        
    }
    
    
}
