/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import DAO.AdresDaoImpl;
import DAO.AdresDaoJsonNieuw;
import DAO.KlantDAOImpl;
import DAO.KlantDaoJson;
import Interface.AdresDao;
import Interface.KlantDAO;
import POJO.Adres;
import POJO.Klant;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

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
   int aantal = 20;
  
   FileTabellen.createBigJsonAdresTabel("res/files/adresTabel.json", aantal);
   FileTabellen.createBigJsonKlantTabel(aantal); 
   FileTabellen.createBigJsonKlantAdresTussenTabel("res/files/adresKlantTussenTabel.json", aantal);
   
   ConnectionFactory.useMySQL();
    FillBatchDatabase.clearDatabase();
    FillBatchDatabase.fillBatchDatabase(aantal);
   
   
      Adres adres = new Adres();
      adres.setAdresID(98);
      adres.setWoonplaats("Mokum");
      adres.setPostcode("1234AQ");
      adres.setHuisnummer(12);
      
      AdresDao adresDaoJson = new AdresDaoJsonNieuw();
      KlantDAO klantDaoJson = new KlantDaoJson();
      KlantDAO klantDaoSQL = new KlantDAOImpl();
      AdresDao adresDaoSQL = new AdresDaoImpl();
    
      Klant klant = new Klant();
      klant.setKlantID(10);
      klant.setVoornaam("Mooiman");
      klant.setAchternaam("Mooiman");
      klant.setKlantID(888);
    
      // ..................................................................
      long start = new Date().getTime();  
      System.out.println("adres: " + adresDaoJson.findAdres("1234AQ" , 12));
      long stop = new Date().getTime();
      long duur = stop - start;
      System.out.println("dit duurde bij adres zoeken op postcode Json  " + duur + " ms");
      //.....................................................................    
      
      start = new Date().getTime();
      System.out.println("klant " + klantDaoJson.findByName("wiedann", "niemand"));
      stop = new Date().getTime();
      duur = stop - start;
      System.out.println("dit duurde bij zoeken op naam json  " + duur + " ms");
      //.........................................................................
       
       klant = new Klant();
       klant.setAchternaam("Meddina");
       adres.setAdresID(0);
       // .............................................................
                   
      start = new Date().getTime();
      System.out.println("adres van klant :  " + adresDaoJson.findAdres(klant.getKlantID()));
      stop = new Date().getTime();
      duur = stop - start;
      System.out.println("dit duurde bij adres zoeken op klantID json " + duur + " ms");
      //********************************************************************
      
      start = new Date().getTime();
      System.out.println("klant " + klantDaoSQL.findByName("wiedann", "niemand"));
      stop = new Date().getTime();
      duur = stop - start;
      System.out.println("dit duurde bij KLant zoeken op naam SQL  " + duur + " ms");
      //******************************************************************************
      
      start = new Date().getTime();
      System.out.println("adres van klant :  " + adresDaoSQL.findAdres(klant.getKlantID()));
      stop = new Date().getTime();
      duur = stop - start;
      System.out.println("dit duurde bij Adres zoeken op klantID SQL " + duur + " ms");
      //********************************************************************
      
      
      
    }
    
    
    private static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)  {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}
