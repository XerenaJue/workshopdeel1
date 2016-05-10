package opdracht2;

import java.sql.*;

public interface AdresDao {
	public Adres findAdres(String straatnaam, String postcode, int huisnummer,
			String toevoeging, String woonplaats) throws SQLException;
	public Adres findByStraatnaam(String straatnaam) throws SQLException;
	public Adres findByPostcodeEnHuisnummer(String postcode, int huisnummer) throws SQLException;
	public void insert(Adres adres) throws SQLException;
	public void update(Adres adres) throws SQLException;
	
}
