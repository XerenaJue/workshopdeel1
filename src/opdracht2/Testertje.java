/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import DAO.AdresDaoJsonNieuw;
import DAO.KlantDaoJson;
import POJO.Adres;
import POJO.Klant;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;
import static opdracht2.FileTabellen.createBigJsonKlantAdresTussenTabel;
import static opdracht2.FileTabellen.createEmptyJsonKlantAdresTussenTabel;

/**
 *
 * @author jeroen
 */
public class Testertje {
    public static void main(String[] args) throws SQLException, IOException{
        
      //  ConnectionFactory.useFirebird();
      //  FillBatchDatabase.clearDatabase("Firebird");
      //  FillBatchDatabase.fillBatchDatabase("Firebird");
        // ConnectionFactory.useMySQL();
       //  FillBatchDatabase.clearDatabase("MySQL");
       // FillBatchDatabase.fillBatchDatabase();
        //ConnectionFactory.closeConnectionPool();
   /*
       ArtikelPOJO artikel = new ArtikelPOJO();
   
        
        ArtikelDao xmlDao = new ArtikelDAOXML();
        FileTabellen.createBigXMLArtikelTabel("artikelTabel.xml", 1000);
         FileTabellen.createBigJsonArtikelTabel("artikelTabel.json", 1000);
        
        for (int i = 0 ; i < 1 ; i++) {
            artikel.setArtikelID(i);
            artikel.setArtikelNaam(generateString(new Random(), "Jeroen Ottervanger", 10));
            xmlDao.createArtikel(artikel);
        }
         
        System.out.println("niets " + xmlDao.readArtikel(14));
        artikel.setArtikelNaam(" pindakaas");
        artikel.setArtikelID(5001);
        
       long start = new Date().getTime();
        xmlDao.updateArtikel(artikel);
       long stop = new Date().getTime();
       xmlDao.deleteArtikel(artikel);
        
        System.out.println("catalogus: " + xmlDao.findAlleArtikelen());
        long duur = stop - start;
        System.out.println( "dit duurde bij XML " + duur + " ms" );
        
        
        
         ArtikelDao jsonDao = new ArtikelDAOJson();
       // FileTabellen.createEmptyJsonArtikelTabel("artikelTabel.json");
        
        for (int i = 0 ; i < 1 ; i++) {
            artikel.setArtikelID(i);
            artikel.setArtikelNaam(generateString(new Random(), "Jeroen Ottervanger", 10));
            jsonDao.createArtikel(artikel);
        }
         
        System.out.println("niets " + jsonDao.readArtikel(14));
        artikel.setArtikelNaam(" pindakaas");
        artikel.setArtikelID(5001);
        
       start = new Date().getTime();
        jsonDao.updateArtikel(artikel);
       stop = new Date().getTime();
       jsonDao.deleteArtikel(artikel);
        
        System.out.println("catalogus: " + xmlDao.findAlleArtikelen());
        duur = stop - start;
        System.out.println( "dit duurde bij Json " + duur + " ms" );
        */
   int aantal = 1000;
  //  FileTabellen.createEmptyJsonAdresTabel("res/files/adresTabel.json");
   FileTabellen.createBigJsonAdresTabel("res/files/adresTabel.json", aantal);
      Adres adres = new Adres();
         AdresDaoJsonNieuw adresDao = new AdresDaoJsonNieuw();
    
          Klant klant = new Klant();
          klant.setKlantID(10);
    
          
         
        adres.setAdresID(98);
          adres.setWoonplaats("Mokum");
          adres.setPostcode("1234AQ");
          adres.setHuisnummer(12);
          
         long start = new Date().getTime();  
         System.out.println("adres: " + adresDao.findAdres("1234AQ" , 12));
       long stop = new Date().getTime();
       
        
        
        long duur = stop - start;
         System.out.println("dit duurde bij adres zoeken op postcode   " + duur + " ms");
          
         klant.setVoornaam("Mooiman");
        klant.setAchternaam("Mooiman");
         klant.setKlantID(888);
         KlantDaoJson klantDao = new KlantDaoJson();
        // klantDao.findByID(1);
        
      //  FileTabellen.createEmptyJsonKlantTabel();
         FileTabellen.createBigJsonKlantTabel(aantal); 
       //  createEmptyJsonKlantAdresTussenTabel("res/files/adresKlantTussenTabel.json");
         createBigJsonKlantAdresTussenTabel("res/files/adresKlantTussenTabel.json", aantal);
         
         start = new Date().getTime();
        System.out.println("klant " + klantDao.findByName("wiedann", "niemand"));
        // System.out.println("klantje  "+  klantDao.findByID(1));
         stop = new Date().getTime();
          duur = stop - start;
         System.out.println("dit duurde bij zoeken op naam  " + duur + " ms");
       //  System.out.println("klantje  "+  klantDao.update(klant));
       
       klant = new Klant();
       klant.setAchternaam("Meddina");
       adres.setAdresID(0);
  //     klantDao.create(klant);
//       adresDao.createAdres(klant.getKlantID(), adres);
       System.out.println("klantje  "+  klantDao.findByID(klant.getKlantID()));
               
      start = new Date().getTime();
      System.out.println("adres van klant :  " + adresDao.findAdres(klant.getKlantID()));
      stop = new Date().getTime();
      duur = stop - start;
      System.out.println("dit duurde bij adres zoeken op klantID  " + duur + " ms");
    }
    
    
    private static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)  {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}
