package DAO;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Interface.AdresDao;
import POJO.Adres;
import POJO.Klant;
import POJO.KlantAdres;

public class AdresDaoXml implements AdresDao {
	static Logger logger = LoggerFactory.getLogger(AdresDaoXml.class);
	File fileName = new File("res/files/adres.xml");
	HashMap<Integer, Adres>adressenLijst = new HashMap<>();
	Adres adres;

	@SuppressWarnings("unchecked")
	@Override
	public Adres createAdres(int klant_id, Adres adres) throws SQLException {
		int id = 0;

		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName))){
			
			adressenLijst = (HashMap<Integer, Adres>) xmlDecoder.readObject();

			id = adressenLijst.size() + 1;
            if (adres.getAdresID() == 0) {
                adres.setAdresID(id);
            }
			adres.setAdresID(++id);
			try (XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(fileName))){
				adressenLijst.put(adres.getAdresID(), adres);
				xmlEncoder.writeObject(adressenLijst);
			}
		} catch (IOException ex) {
			logger.error("Fout in create Adres met XML");
		}
		createKlant_Has_Adres(klant_id, adres.getAdresID());
		return adres;
	}
	
	@SuppressWarnings("unchecked")
	public void createKlant_Has_Adres(int klant_id, int adres_id) {
		HashMap<Integer, KlantAdres> klantAdresLijst;
		String klantFile = "res/files/klant_has_adres.xml";
		KlantAdres klantAdres = new KlantAdres();
		klantAdres.setKlantID(klant_id);
		klantAdres.setAdresID(adres_id);
		
		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(klantFile))) {
			klantAdresLijst = (HashMap<Integer, KlantAdres>) xmlDecoder.readObject();
			int newID = klantAdresLijst.size() + 1;
			
			klantAdresLijst.put(newID, klantAdres);

			try (XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(klantFile))) {
				xmlEncoder.writeObject(klantAdresLijst);
			}
		} catch (IOException e) {
			logger.error("Fout bij het wegschrijven naar de tussentabel");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findAdres(String straatnaam, String postcode, int huisnummer, String toevoeging, String woonplaats)
			throws SQLException {

		Adres adres = new Adres();
		adres.setStraatnaam(straatnaam);
		adres.setPostcode(postcode);
		adres.setHuisnummer(huisnummer);
		adres.setToevoeging(toevoeging);
		adres.setWoonplaats(woonplaats);
		int id = 0;

		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));) {
			adressenLijst = (HashMap<Integer, Adres>) xmlDecoder.readObject();
			adressenLijst.get(adres);
			id = adres.getAdresID();

		} catch (IOException ex) {
			logger.error("Fout bij vinden van Adres met XML");
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Adres> findAdres(String straatnaam) throws SQLException {
		Adres adres = new Adres();
		adres.setStraatnaam(straatnaam);
		List<Adres> adresLijst = new ArrayList<>();
		HashMap<Integer, Adres> adressenLijst = new HashMap<>();

		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));) {
			adressenLijst = (HashMap<Integer, Adres>) xmlDecoder.readObject();
			adres = adressenLijst.get(adres.getStraatnaam());
			adresLijst.add(adres);

		} catch (IOException ex) {
			logger.error("Fout bij vinden van Adres met XML");
		}
		return adresLijst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Adres> findAdres(String postcode, int huisnummer) throws SQLException {
		Adres adres = new Adres();
		adres.setPostcode(postcode);
		adres.setHuisnummer(huisnummer);
		List<Adres> adresLijst = new ArrayList<>();
		HashMap<Integer, Adres> adressenLijst = new HashMap<>();

		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));) {
			adressenLijst = (HashMap<Integer, Adres>) xmlDecoder.readObject();

			adres = adressenLijst.get(adres.getPostcode());
			adresLijst.add(adres);

		} catch (IOException ex) {
			logger.error("Fout bij vinden van Adres met XML");
		}
		return adresLijst;
	}
	//Nog te implementeren, nog geen File voor tussentabel gemaakt
	@Override
	public List<Adres> findAdres(int klant_id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Adres adres) throws SQLException {
		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName))){
		
			adressenLijst = (HashMap<Integer, Adres>) xmlDecoder.readObject();
			adressenLijst.replace(adres.getAdresID(), adres);
			
			try (XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(fileName))) {
			xmlEncoder.writeObject(adressenLijst);
			}
		} catch (IOException ex) {
			logger.error("Fout in het updaten van Adres met XML");
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Adres> findAll() throws SQLException {
		HashMap<Integer, Adres> adresMap = new HashMap<>();
		List<Adres> adresLijst = new ArrayList<>();

		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName))){			
			adresMap = (HashMap<Integer, Adres>) xmlDecoder.readObject();
			adresLijst = new ArrayList<>(adresMap.values());
		} catch (IOException ex) {
			logger.error("Fout bij het vinden van alle adressen met XML");
			ex.printStackTrace();
		}
		return adresLijst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteAdres(Klant klant, Adres adres) throws SQLException {
		HashMap<Integer, Adres> adressenLijst;
		Adres adresReplace = new Adres();
		adresReplace = null;
		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));) {
			
			adressenLijst = (HashMap<Integer, Adres>) xmlDecoder.readObject();			
			adressenLijst.replace(adres.getAdresID(),adresReplace);
			
			try (XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(fileName));) {
				xmlEncoder.writeObject(adressenLijst);
			}
		}catch (FileNotFoundException e) {
			logger.error("Fout bij het wissen van het adres met XML");
			e.printStackTrace();
		}
	}
}
