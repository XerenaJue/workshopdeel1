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
				+ "' AND huisnummer = " + huisnummer + " AND toevoeging = '" + toevoeging + "' AND woonplaats = '"
				+ woonplaats + "'";

		try {
			Connection connection = ConnectionFactory.getMySQLConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			adres = new Adres();
			klant = new Klant();
			
			if (resultSet.next()) {
			klant.setKlantID(resultSet.getInt("klant_ID"));
			klant.setVoornaam(resultSet.getString("voornaam"));
			klant.setAchternaam(resultSet.getString("achternaam"));
			klant.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
			klant.setEmail(resultSet.getString("email"));
			adres.setStraatnaam(resultSet.getString("straatnaam"));
			adres.setPostcode(resultSet.getString("postcode"));
			adres.setHuisnummer(resultSet.getInt("huisnummer"));
			adres.setToevoeging(resultSet.getString("toevoeging"));
			adres.setWoonplaats(resultSet.getString("woonplaats"));
			}
			System.out.println(klant);
			System.out.println(adres);

		}finally {
			// kijk of er verbinding is en zo ja sluit deze
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}

		return adres;
	}

	@Override
	public Adres findAdres(String straatnaam) throws SQLException {
		Klant klant;
		Adres adres;

		String query = "SELECT * FROM klant WHERE straatnaam = '" + straatnaam + "'";
		try {
			connection = ConnectionFactory.getMySQLConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			klant = new Klant();
			adres = new Adres();
			if (resultSet.next()) {
				klant.setKlantID(resultSet.getInt("klant_ID"));
				klant.setVoornaam(resultSet.getString("voornaam"));
				klant.setAchternaam(resultSet.getString("achternaam"));
				klant.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
				klant.setEmail(resultSet.getString("email"));
				adres.setStraatnaam(resultSet.getString("straatnaam"));
				adres.setPostcode(resultSet.getString("postcode"));
				adres.setHuisnummer(resultSet.getInt("huisnummer"));
				adres.setToevoeging(resultSet.getString("toevoeging"));
				adres.setWoonplaats(resultSet.getString("woonplaats"));

			}
			System.out.println(klant);
			System.out.println(adres);

		} finally {
			// kijk of er verbinding is en zo ja sluit deze
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
		return adres;
	}

	@Override
	public void update(int klant_id, Adres adres) throws SQLException {
		String query = "UPDATE klant SET straatnaam = ?, postcode = ?, "
				+ "huisnummer = ?, toevoeging = ?, woonplaats = ?";

		try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

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

		String query = "SELECT * FROM klant WHERE postcode = '" + postcode + "' AND huisnummer = '" + huisnummer + "'";

		try {
			Connection connection = ConnectionFactory.getMySQLConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			adres = new Adres();
			klant = new Klant();

			if (resultSet.next()) {
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
			System.out.println(klant);
			System.out.println(adres);

		} finally {
			// kijk of er verbinding is en zo ja sluit deze
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
		return adres;
	}

	@Override
	public Adres findAdres(Klant klant) throws SQLException {
		Adres adres;

		String query = "SELECT straatnaam, postcode, huisnummer, toevoeging, woonplaats "
				+ "FROM klant WHERE klant_id = " + klant.getKlantID();

		try {
			connection = ConnectionFactory.getMySQLConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			adres = new Adres();

			if (resultSet.next()) {
				adres.setStraatnaam(resultSet.getString("straatnaam"));
				adres.setPostcode(resultSet.getString("postcode"));
				adres.setHuisnummer(resultSet.getInt("huisnummer"));
				adres.setToevoeging(resultSet.getString("toevoeging"));
				adres.setWoonplaats(resultSet.getString("woonplaats"));
			}

			System.out.println(adres);
		} finally {
			// kijk of er verbinding is en zo ja sluit deze
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
		return adres;
	}

}