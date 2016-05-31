package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Interface.AdresDao;
import POJO.Adres;
import POJO.Klant;
import opdracht2.ConnectionFactory;

public class AdresDaoImpl implements AdresDao {

	static Logger logger = LoggerFactory.getLogger(AdresDaoImpl.class);
	
	List<Adres> adressen;
	int adres_id;

	public int findAdres(String straatnaam, String postcode, int huisnummer, String toevoeging, String woonplaats)
			throws SQLException {
		logger.info("adres_id vinden via volledig adres gestart");

		Adres adres;
		String query = "SELECT adres_id FROM adres WHERE straatnaam = '" + straatnaam + "' AND postcode = '" + postcode
				+ "' AND huisnummer = " + huisnummer + " AND toevoeging = '" + toevoeging + "' AND woonplaats = '"
				+ woonplaats + "'";

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {

			adres = new Adres();

			if (resultSet.next()) {
				adres.setAdresID(resultSet.getInt("adres_id"));
				adres_id = adres.getAdresID();
			}
			logger.info("vinden van adres_id via volledig adres succesvol");
		} catch (SQLException ex) {
			logger.info("vinden van adres_id via volledig adres gefaald");
		}
		return adres_id;

	}

	@Override
	public List<Adres> findAdres(String straatnaam) throws SQLException {
		logger.info("klanten vinden via straatnaam gestart");
		Adres adres;
		adressen = new ArrayList<>();
		String query = "SELECT * FROM adres WHERE straatnaam = '" + straatnaam + "'";

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {

			adres = new Adres();

			while (resultSet.next()) {
				adres.setAdresID(resultSet.getInt("adres_ID"));
				adres.setStraatnaam(resultSet.getString("straatnaam"));
				adres.setPostcode(resultSet.getString("postcode"));
				adres.setToevoeging(resultSet.getString("toevoeging"));
				adres.setHuisnummer(resultSet.getInt("huisnummer"));
				adres.setWoonplaats(resultSet.getString("woonplaats"));

				adressen.add(adres);
			}
			logger.info("klanten vinden via straatnaam gelukt");
		} catch (SQLException ex) {
			logger.info("vinden van klanten via straatnaam gefaald");
		}
		return adressen;
	}

	@Override
	public void update(Adres adres) throws SQLException {
		logger.info("update adres gestart");
		String query = "UPDATE adres SET straatnaam = ?, postcode = ?, huisnummer = ?, "
				+ "toevoeging = ?, woonplaats = ? WHERE adres_id =" + adres.getAdresID();

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setString(1, adres.getStraatnaam());
			preparedStatement.setString(2, adres.getPostcode());
			preparedStatement.setInt(3, adres.getHuisnummer());
			preparedStatement.setString(4, adres.getToevoeging());
			preparedStatement.setString(5, adres.getWoonplaats());

			preparedStatement.executeUpdate();

			logger.info("Adres gegevens zijn succesvol aangepast");
		} catch (SQLException ex) {
			logger.info("Update adres niet gelukt");
		}
	}

	@Override
	public List<Adres> findAdres(String postcode, int huisnummer) throws SQLException {
		logger.info("vind klanten via postcode en huisnummer gestart");
		Adres adres;
		adressen = new ArrayList<>();
		String query = "SELECT * FROM adres WHERE postcode = '" + postcode + "' AND huisnummer = " + huisnummer;

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {

			adres = new Adres();

			while (resultSet.next()) {
				adres.setAdresID(resultSet.getInt("adres_ID"));
				adres.setStraatnaam(resultSet.getString("straatnaam"));
				adres.setPostcode(resultSet.getString("postcode"));
				adres.setToevoeging(resultSet.getString("toevoeging"));
				adres.setHuisnummer(resultSet.getInt("huisnummer"));
				adres.setWoonplaats(resultSet.getString("woonplaats"));

				adressen.add(adres);
			}
			logger.info("klanten vinden via postcode en huisnummer succesvol");
		} catch (SQLException ex) {
			logger.info("klanten vinden via postcode en huisnummer gefaald");
		}
		return adressen;
	}

	@Override
	public List<Adres> findAdres(int klant_id) throws SQLException {
		Adres adres;
		List<Adres> adressen = new ArrayList<>();
		logger.info("adres vinden via klant gegevens klant_id gestart");
		String query1 = "SELECT adres_adres_id FROM klant_has_adres WHERE klant_klant_id = " + klant_id;
		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement stmt = connection.prepareStatement(query1);
				ResultSet resultSet = stmt.executeQuery();) {

			while (resultSet.next()) {

				adres = new Adres();
				adres.setAdresID(resultSet.getInt("adres_adres_id"));
				adressen.add(adres);

			}
		} catch (SQLException ex) {
			logger.info("vinden van adressen in klant_has_adres via de klantgegevens-klant_id mislukt");
		}
		for (Adres a : adressen) {

			String query2 = "SELECT straatnaam, postcode, huisnummer, toevoeging, woonplaats "
					+ "FROM adres WHERE adres_id = " + a.getAdresID();
			try (Connection connection = ConnectionFactory.getMySQLConnection();
					PreparedStatement stmt = connection.prepareStatement(query2);
					ResultSet resultSet = stmt.executeQuery();) {
				if (resultSet.next()) {

					a.setStraatnaam(resultSet.getString("straatnaam"));
					a.setPostcode(resultSet.getString("postcode"));
					a.setHuisnummer(resultSet.getInt("huisnummer"));
					a.setToevoeging(resultSet.getString("toevoeging"));
					a.setWoonplaats(resultSet.getString("woonplaats"));
				}
				logger.info("adres van klant succesvol gevonden");
			} catch (SQLException ex) {
				logger.info("vinden van adressen via de klantgegevens-klant_id mislukt");
				ex.printStackTrace();
			}
		}
		return adressen;
	}

	@Override
	public List<Adres> findAll() throws SQLException {
		logger.info("vind alle adressen gestart");
		List<Adres> adressen = new ArrayList<>();
		Adres adres;
		String query = "SELECT adres_id, straatnaam, postcode, huisnummer, " + "toevoeging, woonplaats FROM adres";

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {

			while (resultSet.next()) {
				adres = new Adres();
				adres.setAdresID(resultSet.getInt("adres_id"));
				adres.setStraatnaam(resultSet.getString("straatnaam"));
				adres.setPostcode(resultSet.getString("postcode"));
				adres.setHuisnummer(resultSet.getInt("huisnummer"));
				adres.setToevoeging(resultSet.getString("toevoeging"));
				adres.setWoonplaats(resultSet.getString("woonplaats"));
				adressen.add(adres);
			}
			logger.info("succesvol alle adressen gevonden");
		} catch (SQLException ex) {
			logger.info("kon niet alle adressen vinden");
		}
		return adressen;
	}

	@Override
	public Adres createAdres(int klant_id, Adres adres) throws SQLException {
		logger.info("Adres toevoegen gestart");
		String query = "INSERT INTO adres (straatnaam, postcode, toevoeging, huisnummer, woonplaats)"
				+ " VALUES (?, ?, ?, ?, ?)";
		ResultSet resultSet;

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);) {

			preparedStatement.setString(1, adres.getStraatnaam());
			preparedStatement.setString(2, adres.getPostcode());
			preparedStatement.setString(3, adres.getToevoeging());
			preparedStatement.setInt(4, adres.getHuisnummer());
			preparedStatement.setString(5, adres.getWoonplaats());

			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				adres.setAdresID(resultSet.getInt(1));
			}
			logger.info("Adres succesvol aangemaakt");
			try {
				query = "INSERT INTO klant_has_adres (klant_klant_id, adres_adres_id) VALUES (?, ?)";
				preparedStatement.setInt(1, klant_id);
				preparedStatement.setInt(2, adres.getAdresID());
				preparedStatement.executeUpdate();
				logger.info("klant en adres aan elkaar gekoppeld");
			} catch (SQLException ex) {
				logger.info("Adres niet kunnen toevoegen");

			}
		}
		return adres;

	}

	@Override
	public void deleteAdres(Klant klant, Adres adres) throws SQLException {
		logger.info("Deleten van Adres gestart");

		try (Connection connection = ConnectionFactory.getMySQLConnection()) {
			
			String query1 = "DELETE FROM klant_has_adres WHERE klant_klant_id = ? AND adres_adres_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query1);			
			preparedStatement.setInt(1, klant.getKlantID());
			preparedStatement.setInt(2, adres.getAdresID());
			
			preparedStatement.executeUpdate();
			
			logger.info("deleten uit tussen tabel geslaagd");
			
			//checken of er anderen gebruik maken van het adres
			String query2 = "SELECT * FROM klant_has_adres WHERE adres_adres_id = ?";
			preparedStatement = connection.prepareStatement(query2);
			preparedStatement.setInt(1, adres.getAdresID());
			
			ResultSet resultSet1 = preparedStatement.executeQuery();
			
			if (!resultSet1.next()) {
			String query3 = "DELETE FROM adres WHERE adres_id = ?";
			preparedStatement = connection.prepareStatement(query3);
			preparedStatement.setInt(1, adres.getAdresID());
			
			preparedStatement.executeUpdate();
			}
			logger.info("Deleten van adres geslaagd");
		}

	}

}