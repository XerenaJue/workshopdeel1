/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package opdracht2;
import java.sql.*;
import java.util.*;

/**
 *
 * @author maurice
 */
public class BestellingDAOtest {
        BestellingDAO dao = new BestellingDAO();
        Bestelling bestelling = new Bestelling();
        ArtikelBestelling artikelBestelling = new ArtikelBestelling();
        ArtikelPOJO artikel = new ArtikelPOJO();
        ArrayList<ArtikelBestelling> arraylist = new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
            BestellingDAOtest daotest = new BestellingDAOtest();
            daotest.findAlleBestellingenNoArg();

    }
    
    public void createBestelling() throws SQLException{
        
        //test createbestelling **** beide createBestelling overloaded methodes werken naar behoren.
        Klant klant = new Klant();
        klant.setKlantID(1);
        dao.createBestelling(klant);
        Bestelling bestelling = new Bestelling();
        bestelling.setKlant(1);
        dao.createBestelling(bestelling);
    }
    

//werkt naar behoren.  
    public void addArtikelToBestelling(){
        bestelling.setBestellingID(34);
        artikel.setArtikelID(4);
        artikelBestelling.setArtikelPojo(artikel);
        artikelBestelling.setArtikelenAantal(2);
        dao.addArtikelToBestelling(bestelling, artikelBestelling);
    }
    //werkt naar behoren.
    public void readArtikelBestelling()  throws SQLException{
        bestelling.setBestellingID(34);
        
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
        bestelling.setBestellingID(34);
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
