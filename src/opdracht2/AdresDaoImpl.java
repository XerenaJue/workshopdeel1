package opdracht2;

import java.sql.*;

public class AdresDaoImpl implements AdresDao {

	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;

	public Adres findAdres(String straatnaam, String postcode, int huisnummer, String toevoeging, String woonplaats)
			throws SQLException {
		Klant klant;
		Adres adres;
		String query = "SELECT * FROM klant WHERE straatnaam = '" + straatnaam + "' AND postcode = '" + postcode
				+ "' AND huisnummer = " + huisnummer + " AND toevoeging = '" + toevoeging + "' AND woonplaats = "
				+ woonplaats + "'";

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {

			adres = new Adres();
			klant = new Klant();

			klant.setKlantID(resultSet.getInt("klant_ID"));
			klant.setVoornaam(resultSet.getString("voornaam"));
			klant.setAchternaam(resultSet.getString("achternaam"));
			klant.setTussenvoegsel("tussenvoegsel");
			klant.setEmail(resultSet.getString("email"));
			adres.setStraatnaam(resultSet.getString("straatnaam"));
			adres.setPostcode(resultSet.getString("postcode"));
			adres.setHuisnummer(resultSet.getInt("huisnummer"));
			adres.setToevoeging(resultSet.getString("toevoeging"));
			adres.setWoonplaats(resultSet.getString("woonplaats"));

		}

		return adres;
	}

	@Override
	public Adres findAdres(String straatnaam) throws SQLException {
		Adres adres;
		Klant klant;
		String query = "SELECT * FROM klant WHERE straatnaam = '" + straatnaam + "'";
		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {

			adres = new Adres();
			klant = new Klant();

			klant.setKlantID(resultSet.getInt("klant_ID"));
			klant.setVoornaam(resultSet.getString("voornaam"));
			klant.setAchternaam(resultSet.getString("achternaam"));
			klant.setTussenvoegsel("tussenvoegsel");
			klant.setEmail(resultSet.getString("email"));
			adres.setStraatnaam(resultSet.getString("straatnaam"));
			adres.setPostcode(resultSet.getString("postcode"));
			adres.setHuisnummer(resultSet.getInt("huisnummer"));
			adres.setToevoeging(resultSet.getString("toevoeging"));
			adres.setWoonplaats(resultSet.getString("woonplaats"));

		}
		return adres;
	}

	@Override
	public void insert(int klant_id, Adres adres) throws SQLException {
		String query = "INSERT INTO klant(straatnaam, postcode, huisnummer, toevoeging, woonplaats) VALUES"
				+ "?,?,?,?,?";

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {

			preparedStatement.setString(1, adres.getStraatnaam());
			preparedStatement.setString(2, adres.getPostcode());
			preparedStatement.setInt(3, adres.getHuisnummer());
			preparedStatement.setString(4, adres.getToevoeging());
			preparedStatement.setString(5, adres.getWoonplaats());

			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				System.out.println("Adres gegevens zijn succesvol toegevoegd");
			}

		}

	}

	@Override
	public void update(int klant_id, Adres adres) throws SQLException {
		String query = "UPDATE klant SET straatnaam = ?, postcode = ?, "
				+ "huisnummer = ?, toevoeging = ?, woonplaats = ?";

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {

			preparedStatement.setString(1, adres.getStraatnaam());
			preparedStatement.setString(2, adres.getPostcode());
			preparedStatement.setInt(3, adres.getHuisnummer());
			preparedStatement.setString(4, adres.getToevoeging());
			preparedStatement.setString(5, adres.getWoonplaats());

			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				System.out.println("Adres gegevens zijn succesvol aangepast");
			}

		}

	}

	@Override
	public Adres findAdres(String postcode, int huisnummer) throws SQLException {
		Klant klant;
		Adres adres;
		String query = "SELECT * FROM klant WHERE postcode = '" + postcode + 
				"' AND huisnummer = '" + huisnummer + "'";

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {

			adres = new Adres();
			klant = new Klant();

			klant.setKlantID(resultSet.getInt("klant_ID"));
			klant.setVoornaam(resultSet.getString("voornaam"));
			klant.setAchternaam(resultSet.getString("achternaam"));
			klant.setTussenvoegsel("tussenvoegsel");
			klant.setEmail(resultSet.getString("email"));
			adres.setStraatnaam(resultSet.getString("straatnaam"));
			adres.setPostcode(resultSet.getString("postcode"));
			adres.setHuisnummer(resultSet.getInt("huisnummer"));
			adres.setToevoeging(resultSet.getString("toevoeging"));
			adres.setWoonplaats(resultSet.getString("woonplaats"));

		}
		return adres;
	}

	@Override
	public void delete(int klant_id, Adres adres) throws SQLException {

		String query = "DELETE FROM klant WHERE straatnaam = '" + adres.getStraatnaam() + "' AND postcode"
				+ adres.getPostcode() + "' AND huisnummer = " + adres.getHuisnummer() + " AND toevoeging = '"
				+ adres.getToevoeging() + "' AND woonplaats = '" + adres.getWoonplaats() + "'";

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {
			System.out.println("De adresgegevens zijn succesvol verwijderd");
		}
	}

	@Override
	public Adres findAdres(Klant klant) throws SQLException {
		Adres adres;
		
		String query = "SELECT straatnaam, postcode, huisnummer, toevoeging, woonplaats WHERE klant_id = "
				+ klant.getKlantID();
		
		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {
			
			adres = new Adres();
			adres.setStraatnaam(resultSet.getString("straatnaam"));
			adres.setPostcode(resultSet.getString("postcode"));
			adres.setHuisnummer(resultSet.getInt("huisnummer"));
			adres.setToevoeging(resultSet.getString("toevoeging"));
			adres.setWoonplaats(resultSet.getString("woonplaats"));
		}
		return adres;
	}

}