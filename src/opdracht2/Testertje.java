/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import DAO.ArtikelDAOJson;
import DAO.ArtikelDAOXML;
import Interface.ArtikelDao;
import POJO.ArtikelPOJO;
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
        
      
      
    }
    
    
    private static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)  {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}
