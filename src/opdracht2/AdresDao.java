package opdracht2;

import java.sql.*;

public interface AdresDao {
	public Adres findAdres(String straatnaam, String postcode, int huisnummer,
			String toevoeging, String woonplaats) throws SQLException;
	public Adres findAdres(String straatnaam) throws SQLException;
	public Adres findAdres(String postcode, int huisnummer) throws SQLException;
	public Adres findAdres(Klant klant) throws SQLException;
	
	public void insert(int klant_id, Adres adres) throws SQLException;
	public void update(int klant_id, Adres adres) throws SQLException;
	public void delete(int klant_id, Adres adres) throws SQLException;
	
}
