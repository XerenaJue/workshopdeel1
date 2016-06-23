package DAO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Interface.AdresDao;
import POJO.Adres;
import POJO.Klant;
import POJO.KlantAdres;

public class AdresDaoJson implements AdresDao {
	static Logger logger = LoggerFactory.getLogger(AdresDaoJson.class);
	Gson gson = new Gson();
	String fileName = "res/files/adres.json";
	Type adresType = new TypeToken<HashMap<Integer, Adres>>() {
	}.getType();
	Type klantAdrsType = new TypeToken<HashMap<Integer, KlantAdres>>() {
	}.getType();

	@SuppressWarnings("unchecked")
	@Override
	public Adres createAdres(int klant_id, Adres adres) throws SQLException {
		logger.info("createAdres met JSON gestart");
		HashMap<Integer, Adres> adressenLijst;
		
		try (FileReader fileRead = new FileReader(fileName);){
			adressenLijst = gson.fromJson(fileRead, adresType);
			int newID = adressenLijst.size() + 1;
			if (adres.getAdresID() == 0) {
				adres.setAdresID(newID);
			}
			adressenLijst.put(adres.getAdresID(), adres);
			
			try (FileWriter fileWrite = new FileWriter(fileName);) {
				fileWrite.write(gson.toJson(adressenLijst, adresType));
				logger.info("Adres succesvol aangemaakt via Json");
				
			} 
		} catch (IOException ex) {
			logger.error("Er is iets fout gegaan bij createAdres met Json");
			ex.printStackTrace();
		} 
		createKlant_Has_Adres(klant_id, adres.getAdresID());
		return adres;
	}
	
	public void createKlant_Has_Adres(int klant_id, int adres_id) {
		
		HashMap<Integer, KlantAdres> klantAdresLijst;
		String klantFile = "res/files/klant_has_adres.json";
		KlantAdres klantAdres = new KlantAdres();
		klantAdres.setKlantID(klant_id);
		klantAdres.setAdresID(adres_id);
		
		try (FileReader klantFileRead = new FileReader(klantFile);) {
			klantAdresLijst = gson.fromJson(klantFileRead, klantAdrsType);
			int newID = klantAdresLijst.size() + 1;
			
			klantAdresLijst.put(newID, klantAdres);

			try (FileWriter klantFileWrite = new FileWriter(klantFile);) {
				klantFileWrite.write(gson.toJson(klantAdresLijst, klantAdrsType));
			}
		} catch (IOException e) {
			logger.error("Fout bij het wegschrijven naar de tussentabel");
			e.printStackTrace();
		}
	}

	@Override
	public int findAdres(String straatnaam, String postcode, int huisnummer, String toevoeging, String woonplaats)
			throws SQLException {
		Adres adres = new Adres();
		Adres adresTemp = new Adres();
		int id = 0;
		adres.setStraatnaam(straatnaam);
		adres.setPostcode(postcode);
		adres.setHuisnummer(huisnummer);
		adres.setToevoeging(toevoeging);
		adres.setWoonplaats(woonplaats);

		HashMap<Integer, Adres> adressenLijst = new HashMap<>();

		try (FileReader fileRead = new FileReader(fileName)) {
			adressenLijst = gson.fromJson(fileRead, adresType);
			adressenLijst.get(adres);
			if (adressenLijst.containsValue(adres) == true) {
				adresTemp = adres;
				id = adresTemp.getAdresID();
			}

		} catch (IOException e) {
			logger.error("Fout bij vinden van adresID met Json");
			e.printStackTrace();
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	// Werkt nog niet naar behoren
	public List<Adres> findAdres(String straatnaam) throws SQLException {
		Adres adres = new Adres();
		List<Adres> adresLijst = new ArrayList<>();
		HashMap<Integer, Adres> adressenLijst = new HashMap<>();

		try (FileReader fileRead = new FileReader(fileName)) {
			adressenLijst = gson.fromJson(fileRead, adresType);
			
			for (int i = 0; i < adressenLijst.size(); i++) {
				adres = adressenLijst.get(i);
				
				if (adres.getStraatnaam() == straatnaam){
					adresLijst.add(adres);					
				}
			}

		} catch (IOException e) {
			logger.error("Fout bij vinden van adresID met Json");
			e.printStackTrace();
		}

		return adresLijst;
	}

	@Override
	public List<Adres> findAdres(String postcode, int huisnummer) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Adres> findAdres(int klant_id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Adres adres) throws SQLException {
		HashMap<Integer, Adres> adressenLijst = new HashMap<>();

		try (FileReader fileRead = new FileReader(fileName)) {
			adressenLijst = gson.fromJson(fileRead, adresType);
			adressenLijst.replace(adres.getAdresID(), adres);

			try (FileWriter fileWrite = new FileWriter(fileName)) {
				fileWrite.write(gson.toJson(adressenLijst, adresType));
				logger.info("Adres succesvol veranderd met Json");
			}
		} catch (IOException e) {
			logger.error("Fout bij update Adres met Json");
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Adres> findAll() throws SQLException {
		HashMap<Integer, Adres> adresMap = new HashMap<>();
		List<Adres> adresLijst = new ArrayList<>();

		try (FileReader fileRead = new FileReader(fileName)) {
			adresMap = gson.fromJson(fileRead, adresType);
			adresLijst = new ArrayList<>(adresMap.values());

		} catch (IOException e) {
			logger.error("Fout bij het vinden van alle adressen met Json");
			e.printStackTrace();
		}
		return adresLijst;
	}

	@Override
	public void deleteAdres(Klant klant, Adres adres) throws SQLException {
		HashMap<Integer, Adres> adressenLijst;
		// Ipv adres volledig te wissen, vervangen door een leeg object ivm
		// hanteren van id's
		try (FileReader fileRead = new FileReader(fileName)) {
			Adres adresNull = new Adres();
			adressenLijst = gson.fromJson(fileRead, adresType);
			adressenLijst.replace(adres.getAdresID(), adresNull);

			try (FileWriter fileWrite = new FileWriter(fileName)) {
				fileWrite.write(gson.toJson(adressenLijst, adresType));
				logger.info("Adres succesvol gewist met Json");
			}

		} catch (IOException e) {
			logger.error("Fout bij het wissen van een adres met Json");
			e.printStackTrace();
		}

	}
}
