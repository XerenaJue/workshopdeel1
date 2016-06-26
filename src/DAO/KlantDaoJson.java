package DAO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Interface.KlantDAO;
import POJO.Adres;
import POJO.Klant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import opdracht2.KlantAdresDubbelHashMap;

public class KlantDaoJson implements KlantDAO{
	private final static Logger LOGGER = LoggerFactory.getLogger(KlantDaoJson.class);
	JSONObject deKlant;
	JSONArray klanttabel;
	JSONParser parser;
	String bestand = "res/files/klantTabel.json";
        KlantAdresDubbelHashMap tussenTabel;
        Gson gson;
        Type tussenType;
        
        public KlantDaoJson() {
            
            klanttabel = getData();
            leesTussenTabel();
        }
        
        
	@Override
	public void create(Klant klant) {
	//	klanttabel = getData();
		int newID = generateId() + 1;
                klant.setKlantID(newID);
		if (klanttabel == null || klanttabel.isEmpty() || klanttabel.size() == 0) {
			klanttabel = new JSONArray();
		}
		deKlant = new JSONObject();		
		
		deKlant.put("Klant_id", newID);			
		deKlant.put("Voornaam", klant.getVoornaam());
		deKlant.put("Achternaam", klant.getAchternaam());
		deKlant.put("Tussenvoegsel", klant.getTussenvoegsel());
		deKlant.put("Email", klant.getEmail());
			
		
		klanttabel.add(deKlant);
		
		try (FileWriter file = new FileWriter(bestand)) {
			file.write(klanttabel.toJSONString());
			LOGGER.info("create klant gelukt");
		} catch (IOException e) {
			LOGGER.info("create klant mislukt " + e);			
		}
                klanttabel = getData();
	}
	
	@Override
	public List<Klant> findAll() {
		List<Klant> klanten = new ArrayList<>();		
		//klanttabel = getData();
		Klant klant;
		
		Iterator iterator = klanttabel.iterator();
		while (iterator.hasNext()) {
			deKlant = (JSONObject)iterator.next();
			klant = new Klant();
			//set de long van JSON om naar een int om in de klant te passen.
			Long deKlant_id = (Long)deKlant.get("Klant_id");
			klant.setKlantID(deKlant_id.intValue());
			//zet de resterende info ook in klant
			klant.setVoornaam((String)deKlant.get("Voornaam"));
			klant.setAchternaam((String)deKlant.get("Achternaam"));
			klant.setTussenvoegsel((String)deKlant.get("Tussenvoegsel"));
			klant.setEmail((String)deKlant.get("Email"));
			klanten.add(klant);
		}
		return klanten;
	}
	
	@Override
	public Klant findKlant(Klant bestaandeKlant) {
        int klantID = bestaandeKlant.getKlantID();
        String klantVoornaam = bestaandeKlant.getVoornaam();
        String klantAchternaam = bestaandeKlant.getAchternaam();
        
        if (klantID != 0) {        	
        	LOGGER.info("klant zoeken op id -- id was niet nul");
        	return findByID(klantID);
        }   
        
        else if (klantVoornaam != null && klantVoornaam.length() >= 1 && klantAchternaam.length() >= 1) {
        	LOGGER.info("klant zoeken op voor en achternaam -- namen waren groter dan 0");
        	return findByName(klantVoornaam, klantAchternaam);
        }
        
        else if (klantVoornaam != null && klantVoornaam.length() >= 1) {
        	LOGGER.info("klant zoeken op voornaam -- voornaam was groter dan 0");
        	return FindByName(klantVoornaam);
        }
        
        else {
        	LOGGER.info("geen bruikbaare velden gevonden om klant te zoeken probeer op andere manier");
        	return null;
        }
	}
	@Override
        public List<Klant> findKlant(Adres klantAdres) {
    
            List<Klant> klanten = new ArrayList<>();
            List<Integer> klantIDs = new ArrayList<>();
                  
            if (klantAdres != null)klantIDs = tussenTabel.getKlantIDs(klantAdres.getAdresID());
            if (klantIDs != null) {
                for (Integer i: klantIDs) {
                        klanten.add(this.findByID(i));
                }  
            }        
            return klanten;
        }

        
	@Override
	public Klant findByID(int klantId) {
	
		Klant klant = new Klant();
		deKlant = new JSONObject();
	//	klanttabel = getData();
		parser = new JSONParser();
		
		Iterator iterator = klanttabel.iterator();
		Boolean gevonden = false;
		while (iterator.hasNext() && !gevonden) {
			deKlant = (JSONObject)iterator.next();
			Long huidig_id = (Long) deKlant.get("Klant_id");
			if (huidig_id == Integer.toUnsignedLong(klantId)) {
				gevonden = true;
				LOGGER.info("klant gevonden op bassis van id");
			}
		}
		klant.setKlantID(klantId);
		klant.setVoornaam((String)deKlant.get("Voornaam"));
		klant.setAchternaam((String)deKlant.get("Achternaam"));
		klant.setTussenvoegsel((String)deKlant.get("Tussenvoegsel"));
		klant.setEmail((String)deKlant.get("Email"));
		
		return klant;
	}
	
	@Override
	public Klant findByName(String voornaam, String achternaam) {
		Klant klant = new Klant();
		deKlant = new JSONObject();
	//	klanttabel = getData();
		parser = new JSONParser();
		
		Iterator iterator = klanttabel.iterator();
		Boolean gevonden = false;
		while (iterator.hasNext() && !gevonden) {
			deKlant = (JSONObject)iterator.next();
			String huidigeVoornaam = (String)deKlant.get("Voornaam");
			String huidigeAchternaam = (String)deKlant.get("Achternaam");
			if ( huidigeVoornaam != null && huidigeAchternaam != null && huidigeVoornaam.equalsIgnoreCase(voornaam) && huidigeAchternaam.equalsIgnoreCase(achternaam)) {
				gevonden = true;
				LOGGER.info("klant gevonden op bassis van voornaam en achternaam");
			}
		}
                if (gevonden) {
		//set de long van JSON om naar een int om in de klant te passen.
                    Long deKlant_id = (Long)deKlant.get("Klant_id");
                    klant.setKlantID(deKlant_id.intValue());
		
                    klant.setVoornaam(voornaam);
                    klant.setAchternaam(achternaam);
                    klant.setTussenvoegsel((String)deKlant.get("Tussenvoegsel"));
                    klant.setEmail((String)deKlant.get("Email"));
                }
		return klant;
	}
	
	@Override
	public Klant FindByName(String voornaam) {
		Klant klant = new Klant();
		deKlant = new JSONObject();
	//	klanttabel = getData();
		parser = new JSONParser();
		
		Iterator iterator = klanttabel.iterator();
		Boolean gevonden = false;
		while (iterator.hasNext() && !gevonden) {
			deKlant = (JSONObject)iterator.next();
			String huidigeVoornaam = (String)deKlant.get("Voornaam");
			if (huidigeVoornaam.equalsIgnoreCase(voornaam)) {
				gevonden = true;
				LOGGER.info("klant gevonden op bassis van voornaam");
			}
		}
		//set de long van JSON om naar een int om in de klant te passen.
		Long deKlant_id = (Long)deKlant.get("Klant_id");
		klant.setKlantID(deKlant_id.intValue());
		
		klant.setVoornaam(voornaam);
		klant.setAchternaam((String)deKlant.get("Achternaam"));
		klant.setTussenvoegsel((String)deKlant.get("Tussenvoegsel"));
		klant.setEmail((String)deKlant.get("Email"));
		
		return klant;
	}
	
	@Override
	public void update(Klant klant) {
	//	klanttabel = getData();
		int aantepassen = klant.getKlantID();
		deKlant = new JSONObject();
		parser = new JSONParser();
		Iterator iterator = klanttabel.iterator();
		Boolean gevonden = false;
		while (iterator.hasNext() && !gevonden) {
			deKlant = (JSONObject)iterator.next();
			Long huidig_id = (Long) deKlant.get("Klant_id");
			if (huidig_id == Integer.toUnsignedLong(aantepassen)) {
				gevonden = true;
				LOGGER.info("klant gevonden");
			}
		}
		if (gevonden) {
			int index = klanttabel.indexOf(deKlant);
			deKlant.replace("Voornaam", klant.getVoornaam());
			deKlant.replace("Achternaam", klant.getAchternaam());
			deKlant.replace("Tussenvoegsel", klant.getTussenvoegsel());
			deKlant.replace("Email", klant.getEmail());
			klanttabel.set(index, deKlant);
			
			try (FileWriter file = new FileWriter(bestand)) {
				file.write(klanttabel.toJSONString());
				LOGGER.info("update klant gelukt");
			} catch (IOException e) {
				LOGGER.info("update klant mislukt " + e);				
			}	
		} else {
			LOGGER.info("klant niet gevonden");
			return;
		}
                klanttabel = getData();
	
	}

	@Override
	public void delete(Klant klant) {
	//	klanttabel = getData();
		int teverwijderen = klant.getKlantID();
		deKlant = new JSONObject();
		parser = new JSONParser();
		
		Iterator iterator = klanttabel.iterator();
		Boolean gevonden = false;
		while (iterator.hasNext() && !gevonden) {
			deKlant = (JSONObject)iterator.next();
			Long huidig_id = (Long) deKlant.get("Klant_id");
			if (huidig_id == Integer.toUnsignedLong(teverwijderen)) {
				gevonden = true;
				LOGGER.info("klant gevonden");
			}
		}
		if (gevonden) {
			klanttabel.remove(deKlant);
			try (FileWriter file = new FileWriter(bestand)) {
				file.write(klanttabel.toJSONString());
                                deleteFromTussenTabel(teverwijderen);
				LOGGER.info("delete klant gelukt");
			} catch (IOException e) {
				LOGGER.info("delete klant mislukt " + e);				
			}	
		} else {
			LOGGER.info("Klant niet gevonden");
			return;
		}
                klanttabel = getData();
		
	}
        private void deleteFromTussenTabel(Integer klantID) {
            
            List<Integer> AdresIDs;
            gson = new Gson();
            tussenType = new TypeToken<KlantAdresDubbelHashMap>() {}.getType();
            String fileNameTussen = "res/files/adresKlantTussenTabel.json"; 
                    
            AdresIDs = tussenTabel.getAdresIDs(klantID);
            for (Integer adresje: AdresIDs) {
                tussenTabel.removeKlantOpAdres(klantID, adresje); // wellicht niet optimaal maar klant heeft max enkele adressen
            }  
            try (FileWriter file = new FileWriter(fileNameTussen)) {
                  
                file.write(gson.toJson(tussenTabel, tussenType));  
                 LOGGER.info("klant en zijn adressen verwijderd uit tussenTabel ");
                
            } 
            catch (IOException ex) {
                LOGGER.error("find adres input/output " +  ex);
            }
            leesTussenTabel();      
        }
	
	private JSONArray getData() {
		parser = new JSONParser();
		Object obj = null;
		try (FileReader file = new FileReader(bestand)){
			obj = parser.parse(file);
		} catch (FileNotFoundException e) {
			LOGGER.info("oeps gaat mis: " + e);			
		} catch (IOException e) {
			LOGGER.info("oeps gaat mis: " + e);			
		} catch (ParseException e) {
			LOGGER.info("oeps gaat mis: " + e);			
		}		
		return (JSONArray) obj;
	}
	
	private int generateId() {
		//klanttabel = getData();
		int hoogsteId = 0;
		deKlant = new JSONObject();		
		
		Iterator iterator = klanttabel.iterator();
		while (iterator.hasNext()) {
			deKlant = (JSONObject)iterator.next();
			Long huidig_id = (Long) deKlant.get("Klant_id");
			int huidigAlsInt = huidig_id.intValue();
			if (huidigAlsInt > hoogsteId) {
				hoogsteId = huidigAlsInt;				
			}
		}
		return hoogsteId;
	}
	
        private void leesTussenTabel() {
       
            gson = new Gson();
            tussenType = new TypeToken<KlantAdresDubbelHashMap>() {}.getType();
            String fileNameTussen = "res/files/adresKlantTussenTabel.json"; 
        
            try (FileReader read = new FileReader(fileNameTussen);) {
                       
                tussenTabel = gson.fromJson(read,tussenType);
            }
            catch (IOException ex) {
                LOGGER.error("inlezen alleAdressen " +  ex);
            }
        }
}
