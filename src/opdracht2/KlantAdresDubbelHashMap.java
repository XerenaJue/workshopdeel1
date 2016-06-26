/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jeroenO
 */
public class KlantAdresDubbelHashMap {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(KlantAdresDubbelHashMap.class);
    private HashMap<Integer, List<Integer>> KlantAlsKey;
    private HashMap<Integer, List<Integer>> AdresIDAlsKey;
    private static KlantAdresDubbelHashMap instance = null;
   
    private KlantAdresDubbelHashMap() {
        KlantAlsKey = new HashMap<>();
        AdresIDAlsKey =  new HashMap<>();       
        
    }
    
    public static synchronized KlantAdresDubbelHashMap getInstance() {
        if (instance == null) {
           return  leesTussenTabel();
        }
        else return instance;
    }
    
    public void add(Integer klantID, Integer adresID) {
        List<Integer> adressen = new ArrayList<>();
        if (KlantAlsKey.get(klantID) != null ) {
            
            adressen.addAll(KlantAlsKey.get(klantID));
        }
        adressen.add(adresID);
        KlantAlsKey.put(klantID, adressen);
        
        List<Integer> klanten = new ArrayList<>();
        if (AdresIDAlsKey.get(adresID) != null ) {
            klanten.addAll(AdresIDAlsKey.get(adresID));
        }
        klanten.add(klantID);
        AdresIDAlsKey.put(adresID, klanten);
        
    }
    
    public List<Integer> getKlantIDs(Integer adresID) {
        
        List<Integer> klantIDs = AdresIDAlsKey.get(adresID);
        
        return klantIDs;
    }
    
    public List<Integer> getAdresIDs(Integer klantID) {
        
        List<Integer> adresIDs = KlantAlsKey.get(klantID);
        
        return adresIDs;
    }
    
    public void removeKlantOpAdres(Integer klantID, Integer adresID) {
        
        List<Integer> adressen = KlantAlsKey.get(klantID);
        if (adressen != null) {
            adressen.remove(adresID);
            KlantAlsKey.put(klantID, adressen);
        }
        List<Integer> klanten = AdresIDAlsKey.get(adresID);
        if (klanten != null) {
            klanten.remove(klantID);
            AdresIDAlsKey.put(adresID, klanten);
        }
    }
    
    public HashMap<Integer, List<Integer>> getKlantAlsKey() {
        return KlantAlsKey;
    }
    public HashMap<Integer, List<Integer>> getAdresIDAlsKey() {
        return AdresIDAlsKey;
    }
    public  void setKlantAlsKey(HashMap<Integer, List<Integer>> key) {
        KlantAlsKey = key;
    }
    public void setAdresIDAlsKey(HashMap<Integer, List<Integer>> key) {
        AdresIDAlsKey = key;
    }
    
    private static KlantAdresDubbelHashMap leesTussenTabel() {
       
            Gson gson = new Gson();
            Type tussenType = new TypeToken<KlantAdresDubbelHashMap>() {}.getType();
            String fileNameTussen = "res/files/adresKlantTussenTabel.json"; 
        
            try (FileReader read = new FileReader(fileNameTussen);) {
                       
                instance = gson.fromJson(read,tussenType);
                if (instance == null) {
                    instance = new KlantAdresDubbelHashMap();
                }
                
            }
            catch (IOException ex) {
                LOGGER.error("inlezen alleAdressen " +  ex);
            }
            return instance;
        }
    
}
