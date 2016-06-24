/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import POJO.Adres;
import POJO.ArtikelPOJO;
import POJO.Klant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jeroenO
 */
public class FileTabellen {
    final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FileTabellen.class);
   
    
    public static void createEmptyXMLArtikelTabel(String filename) {
       
        File file = new File(filename);
        
        try ( XMLEncoder encoder = new XMLEncoder( new BufferedOutputStream(
                     new FileOutputStream(file)));) {
             HashMap<Integer, ArtikelPOJO> catalogus = new HashMap<>();
             encoder.writeObject(catalogus);
        } 
        catch (FileNotFoundException ex) {
             LOGGER.error("file om catalogus in te zetten ontbreekt " +  ex);
        }
    }
    
    public static void createEmptyJsonArtikelTabel(String filename) {
       
        Gson gson = new Gson();
        Type artikelType = new TypeToken<HashMap<Integer, ArtikelPOJO>>() {}.getType();
        
        try (FileWriter fileWriter = new FileWriter(filename)) {
             HashMap<Integer, ArtikelPOJO> catalogus = new HashMap<>();
             fileWriter.write(gson.toJson(catalogus, artikelType));
        } 
        catch (IOException ex) {
            LOGGER.error("kan catalogus niet wegschrijven " +  ex);
        }
    }
    
    public static void createBigJsonArtikelTabel(String filename, int entries) {
            
        try (FileWriter file = new FileWriter(filename); ) {
            Gson gson = new Gson();
            Type artikelType = new TypeToken<HashMap<Integer, ArtikelPOJO>>() {}.getType();
            
            HashMap<Integer, ArtikelPOJO> catalogus = new HashMap<>();
            ArtikelPOJO artikel;
            for (int i = 0; i < entries ;i++) {
                artikel = new ArtikelPOJO();
                Random rng = new Random();
                artikel.setArtikelID(i);
                artikel.setArtikelNaam(FillBatchDatabase.generateString(rng , "MooieNamenString", 10));
                artikel.setArtikelPrijs(rng.nextInt(100));
                catalogus.put(i, artikel);
            }   
            file.write(gson.toJson(catalogus, artikelType));
        } catch (IOException ex) {
            LOGGER.error("kan Jsoncatalogus niet vullen " +  ex);
        } 
    } 
    public static void createBigXMLArtikelTabel(String filename, int entries) {
            
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(filename))) {
                                 
            HashMap<Integer, ArtikelPOJO> catalogus = new HashMap<>();
            ArtikelPOJO artikel;
            for (int i = 0; i < entries ;i++) {
                artikel = new ArtikelPOJO();
                Random rng = new Random();
                artikel.setArtikelID(i);
                artikel.setArtikelNaam(FillBatchDatabase.generateString(rng , "MooieNamenString", 10));
                artikel.setArtikelPrijs(rng.nextInt(100));
                catalogus.put(i, artikel);
            }   
            encoder.writeObject(catalogus);
        } 
        catch (IOException ex) {
            LOGGER.error("kan XMLcatalogus niet vullen " +  ex);
        } 
    } 
    
    public static void createEmptyJsonAdresTabel(String filename) {
       
        Gson gson = new Gson();
        Type artikelType = new TypeToken<AdresDubbelHashMap>() {}.getType();
        
        try (FileWriter fileWriter = new FileWriter(filename)) {
             AdresDubbelHashMap alleAdressen = new AdresDubbelHashMap();
             fileWriter.write(gson.toJson(alleAdressen, artikelType));
        } 
        catch (IOException ex) {
            LOGGER.error("kan alleAdressen niet wegschrijven " +  ex);
        }
    }
    
    public static void createEmptyJsonKlantAdresTussenTabel(String filename) {
       
        Gson gson = new Gson();
        Type artikelType = new TypeToken<KlantAdresDubbelHashMap>() {}.getType();
        
        try (FileWriter fileWriter = new FileWriter(filename)) {
             KlantAdresDubbelHashMap tussen = new KlantAdresDubbelHashMap();
             fileWriter.write(gson.toJson(tussen, artikelType));
        } 
        catch (IOException ex) {
            LOGGER.error("kan tussenTabel niet wegschrijven " +  ex);
        }
    }
    
    public static void createBigJsonKlantAdresTussenTabel(String filename, int entries) {
       
        Gson gson = new Gson();
        Type artikelType = new TypeToken<KlantAdresDubbelHashMap>() {}.getType();
        
        try (FileWriter fileWriter = new FileWriter(filename)) {
             KlantAdresDubbelHashMap tussen = new KlantAdresDubbelHashMap();
             for (int i = 1 ; i <= entries; i++  ) {
                 tussen.add(i, i);
                 
             }
             
             fileWriter.write(gson.toJson(tussen, artikelType));
        } 
        catch (IOException ex) {
            LOGGER.error("kan tussenTabel niet wegschrijven " +  ex);
        }
    }
    
    
    public static void createBigJsonAdresTabel(String filename, int entries) {
            
        try (FileWriter fileWriter = new FileWriter(filename); ) {
            Gson gson = new Gson();
        Type artikelType = new TypeToken<AdresDubbelHashMap>() {}.getType();
            
            AdresDubbelHashMap alleAdressen = new AdresDubbelHashMap();
            Adres adres ;
            for (int i = 0; i < entries ;i++) {
                adres = new Adres();
                Random rng = new Random();
                adres.setAdresID(i);
                adres.setStraatnaam(FillBatchDatabase.generateString(rng , "MooieNamenStrinG", 10));
                adres.setPostcode(FillBatchDatabase.generateString(rng , "ABCD123456789", 6));
                alleAdressen.add(adres);
            }   
            fileWriter.write(gson.toJson(alleAdressen, artikelType));
        } catch (IOException ex) {
            LOGGER.error("kan JsonAdresTabel niet vullen " +  ex);
        } 
    } 
    
    public static void createBigJsonKlantTabel(int entries) {
        
         
	
        JSONArray klanttabel;
        //JSONParser parser = new JSONParser(); 
       String bestand = "res/files/klantTabel.json";
	
		klanttabel = new JSONArray();
		
		JSONObject deKlant = new JSONObject();		
		for (int i = 0 ; i < entries; i++ ) {
                    deKlant = new JSONObject();	
                    Random rng = new Random();   
                    deKlant.put("Klant_id", i+1);			
                    deKlant.put("Voornaam", FillBatchDatabase.generateString(rng , "MooieNamenStrinG", 10));
                    deKlant.put("Achternaam", FillBatchDatabase.generateString(rng , "MooieNamenStrinG", 7));
                    deKlant.put("Tussenvoegsel", FillBatchDatabase.generateString(rng , "MooieNamenStrinG", 2));
                    deKlant.put("Email", FillBatchDatabase.generateString(rng , "MooieNamenStrinG",5));
			
                    klanttabel.add(deKlant);
                }
		try (FileWriter file = new FileWriter(bestand)) {
			file.write(klanttabel.toJSONString());
			LOGGER.info("create big klanttabel gelukt");
		} catch (IOException e) {
			LOGGER.info("create big klant mislukt " + e);			
		}	
	}
    public static void createEmptyJsonKlantTabel() {
        
         
	
        JSONArray klanttabel = new JSONArray();
        
        String bestand = "res/files/klantTabel.json";
		
	
		try (FileWriter file = new FileWriter(bestand)) {
			file.write(klanttabel.toJSONString());
			LOGGER.info("create empty klanttabel gelukt");
		} catch (IOException e) {
			LOGGER.info("create klanttabel mislukt " + e);			
		}	
	}
    
    
}
