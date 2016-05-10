/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

/**
 *
 * @author jeroen
 */
public class TestArtikel1 {
    public static void main(String[] args) {
        ArtikelPOJO art = new ArtikelPOJO();
        ArtikelDAO artDAO = new ArtikelDAO(art);
        // leeg artikel
        System.out.println(art);
        
        art = artDAO.readArtikel(1);
        // print zoals nu in database bij besetellingID 1
        System.out.println(art);
        
        artDAO.updateArtikel("pot", 1);    
        art = artDAO.readArtikel(1);
        // print aangepaste
        System.out.println(art);
        
        artDAO.updateArtikel("urinoir", 1);
        art = artDAO.readArtikel(1);
        // enkel de naam van artikel in bestelling 1 is aangepast
        System.out.println(art);
        
        art = new ArtikelPOJO();
        artDAO = new ArtikelDAO(art);
        
        art.setArtikelID(1);
        art.setArtikelNaam("bankstel");
        art.setArtikelPrijs(1000);
        // hieronder wordt de hele pojo in 1 keer in tabel gezet
        artDAO.updateArtikel(1); 
        
        ArtikelPOJO nogEenArt = new ArtikelPOJO();
        ArtikelDAO nogEenArtDAO = new ArtikelDAO(nogEenArt);
       
        nogEenArt = nogEenArtDAO.readArtikel(1);
        // nieuwe pojo wordt uitgeprint 
        System.out.println(nogEenArt);
        
    }
}
