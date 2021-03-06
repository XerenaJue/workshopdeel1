/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import Interface.AdresDao;
import POJO.Adres;
import POJO.Klant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import opdracht2.AdresDubbelHashMap;
import opdracht2.KlantAdresDubbelHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author jeroenO
 */
public class AdresDaoJsonNieuw implements AdresDao{
    
    private final Gson gson = new Gson();
    private final Type adresType = new TypeToken<AdresDubbelHashMap>() {}.getType();
    private final Type tussenType = new TypeToken<KlantAdresDubbelHashMap>() {}.getType();
    private final String fileName = "res/files/adresTabel.json";  
    private final String fileNameTussen = "res/files/adresKlantTussenTabel.json";  
    private final static Logger LOGGER = LoggerFactory.getLogger(ArtikelDAOJson.class);
    
    KlantAdresDubbelHashMap klantenMetAdressen;
    AdresDubbelHashMap alleAdressen;
    
    public AdresDaoJsonNieuw() {
        leesAdresTabel();
     //   leesTussenTabel();
        klantenMetAdressen = KlantAdresDubbelHashMap.getInstance();
    }

    @Override
    public Adres createAdres(int klant_id, Adres adres) throws SQLException {
        
        adres = alleAdressen.add(adres);
        try (FileWriter file = new FileWriter(fileName)) {
                  
		file.write(gson.toJson(alleAdressen, adresType));  
                LOGGER.trace("adres toegeveogd aan alle adressen ");
                             
        } catch (IOException ex) {
            LOGGER.error("create input/output " +  ex);
        }
        addAdresToTussenTabel(klant_id, adres.getAdresID());
     //   leesAdresTabel();
        return adres;
        
    }
    
    private void addAdresToTussenTabel(Integer klantID, Integer adresID) {
        
        klantenMetAdressen.add(klantID, adresID);
        try (FileWriter file = new FileWriter(fileNameTussen)) {
                  
            file.write(gson.toJson(klantenMetAdressen, tussenType));  
            LOGGER.trace("adres en klant toegeveogd aan tussenTabel ");
             
        } catch (IOException ex) {
            LOGGER.error("create input/output " +  ex);
        }
     //   leesTussenTabel();
        
        
    }

    @Override
    public int findAdres(String straatnaam, String postcode, int huisnummer, String toevoeging, String woonplaats) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Adres> findAdres(String straatnaam) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Adres> findAdres(String postcode, int huisnummer) throws SQLException {
        
        List<Adres> adressen = new ArrayList<>(); // ga er vanuit dat dit uniek is
               
        Adres adres = alleAdressen.get( postcode + huisnummer ); // geen toevoeging
        adressen.add(adres);
        
        return adressen;
    }

    @Override
    public List<Adres> findAdres(int klant_id) throws SQLException {
        List<Adres> adressen = new ArrayList<>();
        List<Integer> adresIDs;
        
        adresIDs = klantenMetAdressen.getAdresIDs(klant_id); 
        adressen = alleAdressen.get(adresIDs);
              
        return adressen;
    }

    @Override
    public void update(Adres adres) throws SQLException {
        
        alleAdressen.update(adres);
        try (FileWriter file = new FileWriter(fileName)) {
                
            file.write(gson.toJson(alleAdressen, adresType));  
            LOGGER.info("adres geupdate " + adres);
           
        } catch (IOException ex) {
            LOGGER.error("create input/output " +  ex);
        }
      //  leesAdresTabel();
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        
        List<Adres> adressen =  alleAdressen.getValues();
               
        return adressen;
    }

    @Override
    public void deleteAdres(Klant klant, Adres adres) throws SQLException {
       
        removeAdresFromTussenTabel(klant.getKlantID(), adres.getAdresID());      
              
        alleAdressen.remove(adres.getAdresID());
        try (FileWriter file = new FileWriter(fileName)) {
                  
		file.write(gson.toJson(alleAdressen, adresType));  
                LOGGER.trace("adres toegeveogd aan alle adressen ");
        }
        catch (IOException ex) {
            LOGGER.error("deleteAdres input/output " +  ex);
        }
     //   leesAdresTabel();
    }
    private void removeAdresFromTussenTabel(Integer klantID, Integer adresID) {
        
            
        klantenMetAdressen.removeKlantOpAdres(klantID, adresID);
        try (FileWriter file = new FileWriter(fileNameTussen)) {
                  
		file.write(gson.toJson(klantenMetAdressen, tussenType));  
                LOGGER.info("adres en klant verwijderd uit tussenTabel ");
        } 
        catch (IOException ex) {
            LOGGER.error("deleteAdres input/output " +  ex);
        }
      //  leesTussenTabel();
    }
    private void leesAdresTabel() {
        
        try (FileReader read = new FileReader(fileName);) {
                       
            alleAdressen = gson.fromJson(read,adresType);
        }
        catch (IOException ex) {
            LOGGER.error("inlezen alleAdressen " +  ex);
        }
    }
    
    private void leesTussenTabel() {
        
        try (FileReader read = new FileReader(fileNameTussen);) {
                       
            klantenMetAdressen = gson.fromJson(read,tussenType);
        }
        catch (IOException ex) {
            LOGGER.error("inlezen alleAdressen " +  ex);
        }
    }
    
}
