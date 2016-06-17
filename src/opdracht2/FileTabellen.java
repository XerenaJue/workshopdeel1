/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import POJO.ArtikelPOJO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jeroenO
 */
public class FileTabellen {
    static org.slf4j.Logger logger = LoggerFactory.getLogger(FileTabellen.class);
    
    public static void createEmptyXMLArtikelTabel(String filename) {
       
        File file = new File(filename);
        
        try ( XMLEncoder encoder = new XMLEncoder( new BufferedOutputStream(
                     new FileOutputStream(file)));) {
             HashMap<Integer, ArtikelPOJO> catalogus = new HashMap<>();
             encoder.writeObject(catalogus);
        } 
        catch (FileNotFoundException ex) {
             logger.error("file om catalogus in te zetten ontbreekt " +  ex);
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
            logger.error("kan catalogus niet wegschrijven " +  ex);
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
            logger.error("kan Jsoncatalogus niet vullen " +  ex);
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
            logger.error("kan XMLcatalogus niet vullen " +  ex);
        } 
    } 
    
    
    
}
