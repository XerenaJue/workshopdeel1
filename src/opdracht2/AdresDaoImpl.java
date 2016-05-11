package opdracht2;

import java.sql.*;

import java.sql.SQLException;

public class AdresDaoImpl implements AdresDao {

	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;

	public Adres findAdres(String straatnaam, String postcode, int huisnummer, String toevoeging, String woonplaats)
			throws SQLException {
		Klant klant;
		Adres adres;
		String query = "SELECT * FROM klant WHERE straatnaam = " + straatnaam + " AND huisnummer = " + huisnummer
				+ " AND toevoeging = " + toevoeging + " AND woonplaats = " + woonplaats;

		try {
			connection = ConnectionFactory.getMySQLConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

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
			adres.setToevoeging("toevoeging");
			adres.setWoonplaats(resultSet.getString("woonplaats"));

		} finally {
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
	public Adres findByStraatnaam(String straatnaam) throws SQLException {
		Adres adres;
		Klant klant;
		String query = "SELECT * FROM klant WHERE straatnaam = " + straatnaam;
		try {
			connection = ConnectionFactory.getMySQLConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

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
			adres.setToevoeging("toevoeging");
			adres.setWoonplaats(resultSet.getString("woonplaats"));

		} finally {
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
	public void insert(Adres adres) throws SQLException {
		String query = "INSERT INTO klant(straatnaam, postcode, huisnummer, toevoeging, woonplaats) VALUES"
				+ "?,?,?,?,?";

		try {
			connection = ConnectionFactory.getMySQLConnection();
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, adres.getStraatnaam());
			preparedStatement.setString(2, adres.getPostcode());
			preparedStatement.setInt(3, adres.getHuisnummer());
			preparedStatement.setString(4, adres.getToevoeging());
			preparedStatement.setString(5, adres.getWoonplaats());

			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				System.out.println("Adres gegevens zijn succesvol toegevoegd");
			}

		} finally {
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

	}

	@Override
	public void update(Adres adres) throws SQLException {
		String query = "UPDATE klant SET straatnaam = ?, postcode = ?, "
				+ "huisnummer = ?, toevoeging = ?, woonplaats = ?";

		try {
			connection = ConnectionFactory.getMySQLConnection();
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, adres.getStraatnaam());
			preparedStatement.setString(2, adres.getPostcode());
			preparedStatement.setInt(3, adres.getHuisnummer());
			preparedStatement.setString(4, adres.getToevoeging());
			preparedStatement.setString(5, adres.getWoonplaats());

			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				System.out.println("Adres gegevens zijn succesvol aangepast");
			}

		} finally {
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

	}

	@Override
	public Adres findByPostcodeEnHuisnummer(String postcode, int huisnummer) throws SQLException {
		Klant klant;
		Adres adres;
		String query = "SELECT * FROM klant WHERE postcode = " + postcode + " AND huisnummer = " + huisnummer;
		try {
			connection = ConnectionFactory.getMySQLConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

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
			adres.setToevoeging("toevoeging");
			adres.setWoonplaats(resultSet.getString("woonplaats"));

		} finally {
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
