/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template encoder, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Interface.ArtikelDao;
import POJO.ArtikelPOJO;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jeroenO
 */
public class ArtikelDAOXML implements ArtikelDao{
     private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ArtikelDAOXML.class);
     private final String fileName = "artikelTabel.xml";  
    @Override
    public ArtikelPOJO createArtikel(ArtikelPOJO artikel) {
               
        HashMap<Integer, ArtikelPOJO> catalogus;
        try (XMLDecoder readFile = new XMLDecoder( new FileInputStream(fileName))) {
                  
            Object result = readFile.readObject();
            catalogus = (HashMap<Integer, ArtikelPOJO>) result;
            int nieuweID = catalogus.size() + 1;
            if (artikel.getArtikelID() == 0) {
                artikel.setArtikelID(nieuweID);
            }
            catalogus.put(artikel.getArtikelID(), artikel);
            try (XMLEncoder encoder = new XMLEncoder( new FileOutputStream(fileName))) {
                 
		encoder.writeObject(catalogus);  
                LOGGER.info("artikel toegeveogd aan catalogus ");
            }
        } 
        catch (IOException ex) {
            LOGGER.error("create input/output " +  ex);
        }
        return artikel;
            
   
    }   

    @Override
    public ArtikelPOJO readArtikel(ArtikelPOJO artikel) {
        
        return readArtikel(artikel.getArtikelID());
    }

    @Override
    public ArtikelPOJO readArtikel(int artikelID) {
        ArtikelPOJO artikel = null; 
        HashMap<Integer, ArtikelPOJO> catalogus;
        try (XMLDecoder decoder = new XMLDecoder( new FileInputStream(fileName))) {
                  
            catalogus = (HashMap<Integer, ArtikelPOJO>) decoder.readObject();
            artikel = catalogus.get(artikelID);
        } 
        catch (IOException ex) {
            LOGGER.error("read input/output " +  ex);
        }
        return artikel;
    }

    @Override
    public void updateArtikel(ArtikelPOJO artikel) {
        HashMap<Integer, ArtikelPOJO> catalogus;
        try (XMLDecoder readFile = new XMLDecoder( new FileInputStream(fileName))) {
                      
            catalogus = (HashMap<Integer, ArtikelPOJO>) readFile.readObject();
            catalogus.replace(artikel.getArtikelID() ,artikel);
            
            try (XMLEncoder encoder = new XMLEncoder( 
                                      new FileOutputStream(fileName))) {
                 
		encoder.writeObject(catalogus);  
                LOGGER.info("artikel toegeveogd aan catalogus ");
            }
        } 
        catch (IOException ex) {
            LOGGER.error("create input/output " +  ex);
        }
       
    }

    @Override
    public List<ArtikelPOJO> findAlleArtikelen() {
        HashMap<Integer, ArtikelPOJO> catalogus;
        List<ArtikelPOJO> catAlsLijst = Collections.emptyList();
        try (XMLDecoder readFile = new XMLDecoder(
                new BufferedInputStream( new FileInputStream(fileName)))) {
                  
            catalogus =  (HashMap<Integer, ArtikelPOJO>) readFile.readObject();
            catAlsLijst =  new ArrayList<>((catalogus.values()));
        }
        catch (IOException ex) {
            LOGGER.error("find alleArtikelen input/output " +  ex);
        }
        return catAlsLijst;
    }

    @Override
    public void deleteArtikel(ArtikelPOJO artikel) {
         // vervang door leeg artikel ipv leegmaken om lengte map en dus ID-values te behouden
        HashMap<Integer, ArtikelPOJO> catalogus;
        try (XMLDecoder readFile = new XMLDecoder( new FileInputStream(fileName))) {
            ArtikelPOJO leegArtikel = new ArtikelPOJO();      
            catalogus =  (HashMap<Integer, ArtikelPOJO>) readFile.readObject();
            catalogus.replace(artikel.getArtikelID(), leegArtikel);
            try (XMLEncoder encoder = new XMLEncoder( new FileOutputStream(fileName))) {
                  
		encoder.writeObject(catalogus);    
                LOGGER.info("artikel verwijderd uit catalogus ");
            }
        } 
        catch (IOException ex) {
            LOGGER.error("update input/output " +  ex);
        }
    }
    
    
    
}
