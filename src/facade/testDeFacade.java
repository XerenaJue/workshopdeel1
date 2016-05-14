/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;
import opdracht2.*;
/**
 *
 * @author jeroen
 */
public class testDeFacade {
    public static void main(String[] args)  throws Exception {
        FacadeDatabaseMenu facade = new FacadeDatabaseMenu();
        FillBatchDatabase.clearDatabase();
        FillBatchDatabase.fillBatchDatabase();
        Object[] nepAppArray = facade.getToDisplay();
        Klant klant = new Klant(); 
        nepAppArray[0] = klant;
        Adres zoekAdres = new Adres();
        nepAppArray[1] = zoekAdres;
        
        System.out.println("sherm is leeg: ");
        for (Object o : nepAppArray) {
            System.out.println(o);
        }
        
        zoekAdres.setPostcode("8890UN");
        zoekAdres.setHuisnummer(945);
        zoekAdres.setToevoeging("c");
        
        
        System.out.println("sherm bevat ingetypte waarden: ");
        for (Object o : nepAppArray) {
            System.out.println(o);
        }
                
        facade.zoek(nepAppArray);
        nepAppArray = facade.getToDisplay();
        
        System.out.println();
        System.out.println("sherm geeft nu ook de gevonden klant en rest van adres weer: ");
        for (Object o : nepAppArray) {
            System.out.println(o);
        }
        // probeer een nieuwe klant te maken:
        Klant bestaandeKlant = (Klant)nepAppArray[0];
        facade.createKlant(bestaandeKlant);
        Klant nieuweKlant = new Klant();
        nieuweKlant.setAchternaam("Vader");
        nieuweKlant.setVoornaam("Darth");
        facade.createKlant(nieuweKlant);
        
    }
}
