package DAO;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Interface.KlantDAO;
import POJO.Adres;
import POJO.Klant;

public class KlantDaoXML implements KlantDAO {
	private final static Logger LOGGER = LoggerFactory.getLogger(KlantDaoXML.class);
	String bestand = "res/files/xmlfileKlant.xml";
	ArrayList<Klant> klanten;
	
	@Override
	public List<Klant> findAll() {
		klanten = getData();
		LOGGER.info("alle klanten");
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
	public Klant findByID(int klantId) {
		klanten = getData();
		for (Klant klant : klanten) {
			if (klant.getKlantID() == klantId) {
				LOGGER.info("klant gevonden met id");
				return klant;
			}
		}
		return null;
	}

	@Override
	public Klant findByName(String voornaam, String achternaam) {
		klanten = getData();
		for (Klant klant : klanten) {
			if (klant.getVoornaam().equalsIgnoreCase(voornaam) && klant.getAchternaam().equalsIgnoreCase(achternaam)) {
				LOGGER.info("Klant gevonden met voor- en achternaam");
				return klant;
			}
		}
		return null;
	}

	@Override
	public Klant FindByName(String voornaam) {
		klanten = getData();
		for (Klant klant : klanten) {
			if (klant.getVoornaam().equalsIgnoreCase(voornaam)) {
				LOGGER.info("Klant gevonden met voornaam");
				return klant;
			}
		}
		return null;
	}

	@Override
	public void create(Klant klant) {
		ArrayList<Klant> klanten = getData();
		if (klanten == null || klanten.isEmpty() || klanten.size() == 0) {
			klanten = new ArrayList<>();
		}
		int klantID = generateId() + 1;
		klant.setKlantID(klantID);
		klanten.add(klant);
		try (FileOutputStream file = new FileOutputStream(bestand)) {
			XMLEncoder encoder = new XMLEncoder(file);
			encoder.writeObject(klanten);
			LOGGER.info("create klant gelukt");
			encoder.close();
		} catch (FileNotFoundException e) {
			LOGGER.info("oeps gaat mis: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.info("oeps gaat mis: " + e);
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Klant klant) {
		klanten = getData();
		for (Klant deklant : klanten) {
			if (deklant.getKlantID() == klant.getKlantID()) {
				deklant.setVoornaam(klant.getVoornaam());
				deklant.setAchternaam(klant.getAchternaam());
				deklant.setTussenvoegsel(klant.getTussenvoegsel());
				deklant.setEmail(klant.getEmail());				
			}
		}
		try (FileOutputStream file = new FileOutputStream(bestand)) {
			XMLEncoder encoder = new XMLEncoder(file);
			encoder.writeObject(klanten);
			LOGGER.info("update klant gelukt");
			encoder.close();
		} catch (FileNotFoundException e) {
			LOGGER.info("oeps gaat mis: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.info("oeps gaat mis: " + e);
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Klant klant) { ///werkt nog niet
		klanten = getData();
		for (Klant deklant : klanten) {
			if (deklant.getKlantID() == klant.getKlantID()) {
				klanten.remove(deklant);
				break;
			}
		}
		try (FileOutputStream file = new FileOutputStream(bestand)) {
			XMLEncoder encoder = new XMLEncoder(file);
			encoder.writeObject(klanten);
			LOGGER.info("delete klant gelukt");
			encoder.close();
		} catch (FileNotFoundException e) {
			LOGGER.info("oeps gaat mis: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.info("oeps gaat mis: " + e);
			e.printStackTrace();
		}
	}
	
	private ArrayList<Klant> getData() {
		ArrayList<Klant> klanten = new ArrayList<>();
		
		try (FileInputStream file = new FileInputStream(bestand)) {
			XMLDecoder decoder = new XMLDecoder(file);
			klanten = (ArrayList<Klant>)decoder.readObject();			
			decoder.close();
		} catch (FileNotFoundException e) {
			LOGGER.info("oeps gaat mis: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.info("oeps gaat mis: " + e);
			e.printStackTrace();
		}
		
		return klanten;
	}
	
	private int generateId(){
		int hoogste = 0;
		klanten = getData();
		for (Klant klant : klanten) {
			if (klant.getKlantID() > hoogste) {
				hoogste = klant.getKlantID();
			}
		}
		
		return hoogste;
	}
	

	
	
	///----------------------------------- nog te implementeren---------------------------------///
	
	@Override
	public List<Klant> findKlant(Adres klantAdres) {
		// TODO Auto-generated method stub
		return null;
	}
}
