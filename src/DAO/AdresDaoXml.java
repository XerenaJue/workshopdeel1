package DAO;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Interface.AdresDao;
import POJO.Adres;
import POJO.Klant;

public class AdresDaoXml implements AdresDao {
	static Logger logger = LoggerFactory.getLogger(AdresDaoXml.class);
	File fileName = new File("res/files/adres.xml");
	List<Adres> adressenLijst = new ArrayList<>();
	Adres adres;

	@SuppressWarnings("unchecked")
	@Override
	public Adres createAdres(int klant_id, Adres adres) throws SQLException {
		int id = 0;

		try {
			XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));
			adressenLijst = (ArrayList<Adres>) xmlDecoder.readObject();
			xmlDecoder.close();

			for (Adres adresTemp : adressenLijst) {
				if (adresTemp.getAdresID() > id) {
					id = adresTemp.getAdresID();
				}
			}
			adres.setAdresID(++id);
			XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(fileName));
			adressenLijst.add(adres);
			xmlEncoder.writeObject(adressenLijst);
			xmlEncoder.close();
		} catch (IOException ex) {
			logger.error("Fout in create Adres met XML");
		}
		return null;
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
		List<Adres> adresRetourLijst = new ArrayList<>();
		int id = 0;

		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));) {
			adressenLijst = (List<Adres>) xmlDecoder.readObject();

			for (Adres a : adressenLijst) {
				if (a.getStraatnaam() == adres.getStraatnaam() && a.getPostcode() == adres.getPostcode()
						&& a.getHuisnummer() == adres.getHuisnummer() && a.getToevoeging() == adres.getToevoeging()
						&& a.getWoonplaats() == adres.getWoonplaats()) {
					adresRetourLijst.add(a);
					id = a.getAdresID();
				}
			}

		} catch (IOException ex) {
			logger.error("Fout bij vinden van Adres met XML");
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Adres> findAdres(String straatnaam) throws SQLException {
		List<Adres> adresRetourLijst = new ArrayList<>();

		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));) {
			adressenLijst = (List<Adres>) xmlDecoder.readObject();

			for (Adres a : adressenLijst) {
				if (a.getStraatnaam() == adres.getStraatnaam()) {
					adresRetourLijst.add(a);
				}
			}

		} catch (IOException ex) {
			logger.error("Fout bij vinden van Adres met XML");
		}
		return adresRetourLijst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Adres> findAdres(String postcode, int huisnummer) throws SQLException {
		List<Adres> adresRetourLijst = new ArrayList<>();

		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));) {
			adressenLijst = (List<Adres>) xmlDecoder.readObject();

			for (Adres a : adressenLijst) {
				if (a.getPostcode() == adres.getPostcode() && a.getHuisnummer() == adres.getHuisnummer()) {
					adresRetourLijst.add(a);
				}
			}

		} catch (IOException ex) {
			logger.error("Fout bij vinden van Adres met XML");
		}
		return adresRetourLijst;
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
		try {
			XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));
			adressenLijst = (ArrayList<Adres>) xmlDecoder.readObject();
			xmlDecoder.close();
			for (Adres adresTemp : adressenLijst) {
				if (adresTemp.getAdresID() == adres.getAdresID()) {
					adresTemp.setStraatnaam(adres.getStraatnaam());
					adresTemp.setPostcode(adres.getPostcode());
					adresTemp.setToevoeging(adres.getToevoeging());
					adresTemp.setHuisnummer(adres.getHuisnummer());
					adresTemp.setWoonplaats(adres.getWoonplaats());
				}
			}
			XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(fileName));
			xmlEncoder.writeObject(adressenLijst);
			xmlEncoder.close();
		} catch (IOException ex) {
			logger.error("Fout in het updaten van Adres met XML");
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Adres> findAll() throws SQLException {
		List<Adres> tempAdresLijst = new ArrayList<>();

		try {
			XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));
			adressenLijst = (List<Adres>) xmlDecoder.readObject();
			xmlDecoder.close();

			for (Adres tempAdres : adressenLijst) {
				tempAdresLijst.add(tempAdres);
			}
		} catch (IOException ex) {
			logger.error("Fout bij het vinden van alle adressen met XML");
			ex.printStackTrace();
		}
		return tempAdresLijst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteAdres(Klant klant, Adres adres) throws SQLException {
		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));) {
			adressenLijst = (List<Adres>) xmlDecoder.readObject();

			for (Adres a : adressenLijst) {
				if (a.getAdresID() == adres.getAdresID()) {
					adressenLijst.remove(a);
				}
			}
			try (XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(fileName));) {
				xmlEncoder.writeObject(adressenLijst);
			}
		} catch (FileNotFoundException e) {
			logger.error("Fout bij het wissen van het adres met XML");
			e.printStackTrace();
		}

	}

}
