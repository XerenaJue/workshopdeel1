/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package opdracht2;
import java.sql.*;
import java.util.*;

//import DAO.ArtikelDAOJson1;
import POJO.ArtikelBestelling;
import POJO.ArtikelPOJO;
import POJO.Bestelling;
import POJO.Klant;

/**
 *
 * @author maurice
 */
public class BestellingDAOtest {
        bestellingDAOXML dao = new bestellingDAOXML();
        Bestelling bestelling = new Bestelling();
        ArtikelBestelling artikelBestelling = new ArtikelBestelling();
        ArtikelBestelling artikelBestelling1 = new ArtikelBestelling();
        ArtikelBestelling artikelBestelling2 = new ArtikelBestelling();
        ArtikelPOJO artikel = new ArtikelPOJO();
        List<ArtikelBestelling> arraylist = new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
            BestellingDAOtest daotest = new BestellingDAOtest();
            //daotest.readArtikelBestelling();
            daotest.createBestelling();

    }
    
    public void createBestelling() throws SQLException{
        
        //test createbestelling **** beide createBestelling overloaded methodes werken naar behoren.
        //Klant klant = new Klant();
        //klant.setKlantID(1);
        //ArtikelDAOJson1 artikeldao = new ArtikelDAOJson1();
        Bestelling bestelling = new Bestelling();
        bestelling.setKlant_id(1234);
        bestelling.setBestelling_id(5678);
        artikel.setArtikelID(999);
        artikel.setArtikelNaam("hallo");
        artikel.setArtikelPrijs(999);
        artikelBestelling.setArtikelPojo(artikel);
        artikelBestelling.setArtikelenAantal(1);
        artikelBestelling1.setArtikelPojo(artikel);
        artikelBestelling1.setArtikelenAantal(1);
        artikelBestelling2.setArtikelPojo(artikel);
        artikelBestelling2.setArtikelenAantal(1);
        arraylist.add(artikelBestelling);
        arraylist.add(artikelBestelling1);
        arraylist.add(artikelBestelling2);
        bestelling.setArtikelBestellingList(arraylist);
        //dao.createBestelling(bestelling);
        //artikeldao.createArtikel(artikel);
        dao.deleteBestelling(bestelling);
        //dao.readArtikelBestelling(bestelling);
        
    }
    

//werkt naar behoren.  
    public void addArtikelToBestelling(){
        bestelling.setBestelling_id(999);
        artikel.setArtikelID(999);
        artikelBestelling.setArtikelPojo(artikel);
        artikelBestelling.setArtikelenAantal(999);
        dao.addArtikelToBestelling(bestelling, artikelBestelling);
    }
    //werkt naar behoren.
    public void readArtikelBestelling()  throws SQLException{
        bestelling.setKlant_id(1234);
        bestelling.setBestelling_id(5678);
        
        arraylist = (ArrayList<ArtikelBestelling>)(dao.readArtikelBestelling(bestelling));
        for (ArtikelBestelling element: arraylist)
            System.out.println(element.toString());
    }

//werkt naar behoren.    
    public void updateBestelling() throws SQLException{
        artikel.setArtikelID(4);
        artikelBestelling.setArtikelPojo(artikel);
        artikelBestelling.setArtikelenAantal(5);
        dao.updateBestelling(artikelBestelling, 34);
    }
    // werkt naar behoren
     public void deleteArtikelFromBestelling() throws SQLException{
                 artikel.setArtikelID(4);
                artikelBestelling.setArtikelPojo(artikel);
                dao.deleteArtikelFromBestelling(artikelBestelling, 34);
     }
     
     //werkt naar behoren.
    public void deleteBestelling() throws SQLException{
        bestelling.setBestelling_id(34);
        dao.deleteBestelling(bestelling);
    }
    
    public void deleteBestellingen() throws SQLException{
        Klant klant = new Klant();
        klant.setKlantID(2);
        dao.deleteBestellingen(klant);
    }
    
    //werkt naar behoren.
    public void findAlleBestellingen(){
        Klant klant = new Klant();
        klant.setKlantID(4);
        ArrayList<Bestelling> lijst = (ArrayList<Bestelling>)dao.findAlleBestellingen(klant);
        for (Bestelling element: lijst)
            System.out.println(element.toString());
    }
    //werkt naar behoren.
        public void findAlleBestellingenNoArg(){

        ArrayList<Bestelling> lijst = (ArrayList<Bestelling>)dao.findAlleBestellingen();
        for (Bestelling element: lijst)
            System.out.println(element.toString());
    }

}
