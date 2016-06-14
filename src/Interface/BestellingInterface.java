/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import POJO.ArtikelBestelling;
import POJO.Bestelling;
import POJO.Klant;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maurice
 */
public interface BestellingInterface {

    void addArtikelToBestelling(Bestelling bestelling, ArtikelBestelling artikelBestelling);

    void buildInsertStatement(Bestelling bestelling);

    void closeConnectionStatement();

    Bestelling createBestelling(Klant klant) throws SQLException;

    void createBestelling(Bestelling bestelling) throws SQLException;

    void createCS(String SQLStatement) throws SQLException;

    void createCSR(String SQLStatement) throws SQLException;

    // idem dito als bij updateBestelling
    void deleteArtikelFromBestelling(ArtikelBestelling artikelBestelling, int bestel_id) throws SQLException;

    void deleteBestelling(Bestelling bestelling) throws SQLException;

    void deleteBestelling(ArrayList<Bestelling> bestelling) throws SQLException;

    void deleteBestellingen(Klant klant) throws SQLException;

    List<Bestelling> findAlleBestellingen();

    List<Bestelling> findAlleBestellingen(Klant klant);

    List<ArtikelBestelling> readArtikelBestelling(Bestelling bestelling) throws SQLException;

    // in facade maak je een arrayList van artikelBestellingen die geupdate moeten worden.
    //vervolgens werk je met een for each loop om voor elke aan te passen artikelBestelling een
    // updateBestelling te invoken.
    void updateBestelling(ArtikelBestelling artikelBestelling, int bestel_id) throws SQLException;
    
}
