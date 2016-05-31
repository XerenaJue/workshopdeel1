/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;
import java.sql.*;
import java.util.*;

import POJO.Adres;
import POJO.ArtikelPOJO;
import POJO.Bestelling;
import POJO.Klant;
import opdracht2.*;
/**
 *
 * @author jeroen
 */
public class Inputter {
    Object[] nepAppArray ;
    FacadeDatabaseMenu facade = new FacadeDatabaseMenu();
    Klant klant;
    Adres adres;
    Bestelling bestelling;
    List<Bestelling> bestellingen;
    List<ArtikelPOJO> artikelen;
    Scanner scanner = new Scanner(System.in);
    
    void inputter() throws SQLException{
        
        FillBatchDatabase.clearDatabase();
        FillBatchDatabase.fillBatchDatabase();
        nepAppArray = facade.getToDisplay();
                
        System.out.print("-----------------------------------------------------\n"
                        +"-----------------------------------------------------\n"
                        + "Ik ben uw database wie zoekt u? ");
        int input = -1;
        while (input != 0 ) {
            refreshScherm();
            System.out.print("\n1 = klantmenu\n"
                    + "2 = show alle klanten \n"
                    + "0 = exit \n"
                    + "maak uw keuze:  ");
            input = scanner.nextInt();
            if (input == 1) openKlantMenu();
            else if (input == 2) {
                nepAppArray[4] = facade.findKlanten();
                facade.zoek(nepAppArray);
                nepAppArray = facade.getToDisplay();
            }
            System.out.println();
            printScherm();
        }
    }
    
    void openKlantMenu() throws SQLException{
        
        int input = -1;
        while (input != 0) {
            System.out.print("\nWelkom in het klantmenu\n"
                             +"om te zoeken toets 1 \n"
                             + "om iemand toe te voegen toets 2 \n"
                             + "to exit type 0 \n"
                             + "maak uw keuze:  ");
            input = scanner.nextInt();
            if (input == 1) zoekKlant();  
            else if (input == 2) voegKlantToe();
            refreshScherm();
            System.out.println();
            printScherm();
        }
    }
    
    void zoekKlant() throws SQLException {
        System.out.print("\ngeef klantID:  ");
        int input = scanner.nextInt();
        klant.setKlantID(input);
        facade.zoek(nepAppArray);
        nepAppArray = facade.getToDisplay();
    }
    
    void voegKlantToe() throws SQLException {
        
        System.out.println("hier kunt u een klant toevoegen \n");
        klant = new Klant();
        System.out.print("geef voornaam: ");
        String voornaam = scanner.next();
        System.out.print("geef achternaam: ");
        String achternaam = scanner.next();
        klant.setAchternaam(achternaam);
        klant.setVoornaam(voornaam);
        facade.createKlant(klant);
        nepAppArray = facade.getToDisplay();
        
    }
    
    
    void refreshScherm(){
        this.klant = (Klant)nepAppArray[0];
        this.adres = (Adres)nepAppArray[1];
        this.bestellingen = (List<Bestelling>)nepAppArray[2];
        this.artikelen = (List<ArtikelPOJO>)nepAppArray[3];
    }
    
    public void printScherm() {
        
        for (Object o : nepAppArray) {
            System.out.println(o);
        }
    }
}
