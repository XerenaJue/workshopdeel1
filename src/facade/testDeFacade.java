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
         
        Object[] nepAppArray = facade.getToDisplay();
        Klant klant = new Klant(); 
        nepAppArray[0] = klant;
        Adres zoekAdres = new Adres();
        nepAppArray[1] = zoekAdres;
        
        System.out.println("sherm is leeg: ");
        for (Object o : nepAppArray) {
            System.out.println(o);
        }
        
        zoekAdres.setPostcode("5113GL");
        zoekAdres.setHuisnummer(267);
        zoekAdres.setToevoeging("d");
        
        
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
        
    }
}
