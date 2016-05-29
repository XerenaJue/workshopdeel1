package opdracht2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdresDaoImpl implements AdresDao {

	static Logger logger = LoggerFactory.getLogger(AdresDaoImpl.class);
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	List<Klant> klanten;

	public List<Klant> findAdres(String straatnaam, String postcode, int huisnummer, String toevoeging,
			String woonplaats) throws SQLException {
		logger.info("klanten vinden via volledig adres gestart");

		klanten = new ArrayList<>();
		Klant klant;

		try (Connection connection = ConnectionFactory.getMySQLConnection()) {

			klant = new Klant();

			String query = "SELECT klant_id FROM klant_has_adres LEFT OUTER JOIN "
					+ "adres ON klant_adres.adres_id = adres.adres_id WHERE straatnaam = '" 
					+ straatnaam + "' AND postcode = '" + postcode + "' AND huisnummer = "
					+ huisnummer + " AND toevoeging = '" + toevoeging + "' AND woonplaats = '" 
					+ woonplaats + "'";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				klant.setKlantID(resultSet.getInt("klant_ID"));
				query = "SELECT * FROM klant WHERE klant_id = " + klant.getKlantID();

				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();

				klant.setVoornaam(resultSet.getString("voornaam"));
				klant.setAchternaam(resultSet.getString("achternaam"));
				klant.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
				klant.setEmail(resultSet.getString("email"));
				klanten.add(klant);
			}
			logger.info("vinden van klanten via volledig adres succesvol");
		} catch (SQLException ex) {
			logger.info("vinden van klanten via volledig adres gefaald");
		}
		return klanten;
		
	}

	@Override
	public List<Klant> findAdres(String straatnaam) throws SQLException {
		logger.info("klanten vinden via straatnaam gestart");
		Klant klant;
		klanten = new ArrayList<>();

		try (Connection connection = ConnectionFactory.getMySQLConnection()) {

			String query = "SELECT klant_id FROM klant_has_adres LEFT OUTER JOIN adres "
					+ "ON klant_adres.adres_id = adres.adres_id WHERE straatnaam = '" 
					+ straatnaam + "'";

			klant = new Klant();

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				klant.setKlantID(resultSet.getInt("klant_ID"));
				query = "SELECT * FROM klant WHERE klant_id = " + klant.getKlantID();

				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();

				klant.setVoornaam(resultSet.getString("voornaam"));
				klant.setAchternaam(resultSet.getString("achternaam"));
				klant.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
				klant.setEmail(resultSet.getString("email"));

				klanten.add(klant);
			}
			logger.info("klanten vinden via straatnaam gelukt");
		} catch (SQLException ex) {
			logger.info("vinden van klanten via straatnaam gefaald");
		}
		return klanten;
	}

	@Override
	public void update(int klant_id, Adres adres) throws SQLException {
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
	public List<Klant> findAdres(String postcode, int huisnummer) throws SQLException {
		Klant klant;
		klanten = new ArrayList<>();
		logger.info("vind klanten via postcode en huisnummer gestart");

		try (Connection connection = ConnectionFactory.getMySQLConnection()) {

			klant = new Klant();
			String query = "SELECT klant_id FROM klant_has_adres LEFT OUTER JOIN adres "
					+ "ON klant_has_adres.adres_id = adres.adres_id WHERE postcode = '" 
					+ postcode + "' AND huisnummer = " + huisnummer;

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				klant.setKlantID(resultSet.getInt("klant_ID"));
				query = "SELECT * FROM klant WHERE klant_id = " + klant.getKlantID();
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();

				klant.setVoornaam(resultSet.getString("voornaam"));
				klant.setAchternaam(resultSet.getString("achternaam"));
				klant.setTussenvoegsel("tussenvoegsel");
				klant.setEmail(resultSet.getString("email"));
				klanten.add(klant);
			}
			logger.info("klanten vinden via postcode en huisnummer succesvol");
		} catch (SQLException ex) {
			logger.info("klanten vinden via postcode en huisnummer gefaald");
		}
		return klanten;
	}

	@Override
	public List<Adres> findAdres(Klant klant) throws SQLException {
		Adres adres;
		List<Adres> adressen = new ArrayList<>();
		logger.info("adres vinden via klant gegevens gestart");

		try (Connection connection = ConnectionFactory.getMySQLConnection()) {
			String query = "SELECT adres_id FROM klant_has_adres WHERE klant_id = "
					+ klant.getKlantID();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				adres = new Adres();
				adres.setAdresID(resultSet.getInt("adres_id"));
				query = "SELECT straatnaam, postcode, huisnummer, toevoeging, woonplaats "
						+ "FROM adres WHERE adres_id = " + adres.getAdresID();
				preparedStatement = connection.prepareStatement(query);
				adres.setStraatnaam(resultSet.getString("straatnaam"));
				adres.setPostcode(resultSet.getString("postcode"));
				adres.setHuisnummer(resultSet.getInt("huisnummer"));
				adres.setToevoeging(resultSet.getString("toevoeging"));
				adres.setWoonplaats(resultSet.getString("woonplaats"));
				
				adressen.add(adres);
			}
			logger.info("adressen van klant succesvol gevonden");
		} catch (SQLException ex) {
			logger.info("vinden van adressen via de klantgegevens mislukt");
		}
		return adressen;
	}

	@Override
	public List<Adres> findAll() throws SQLException {
		logger.info("vind alle adressen gestart");
		List<Adres> adressen = new ArrayList<>();
		Adres adres;
		String query = "SELECT adres_id, straatnaam, postcode, huisnummer, "
				+ "toevoeging, woonplaats FROM adres";

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

		try (Connection connection = ConnectionFactory.getMySQLConnection();) {
			String query = "INSERT INTO adres (straatnaam, postcode, huisnummer, toevoeging, woonplaats)"
					+ " VALUES (?, ?, ?,?, ?)";

			preparedStatement.setString(1, adres.getStraatnaam());
			preparedStatement.setString(2, adres.getPostcode());
			preparedStatement.setInt(3, adres.getHuisnummer());
			preparedStatement.setString(4, adres.getToevoeging());
			preparedStatement.setString(5, adres.getWoonplaats());

			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			resultSet = preparedStatement.executeQuery();

			query = "INSERT INTO klant_has_adres (klant_id, adres_id) VALUES (?, ?)";
			preparedStatement.setInt(1, klant_id);
			preparedStatement.setInt(2, adres.getAdresID());

			logger.info("Adres succesvol aangemaakt");
		} catch (SQLException ex) {
			logger.info("Adres niet kunnen toevoegen");
		}
		return adres;

	}

	@Override
	public Adres deleteAdres(Klant klant, Adres adres) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}