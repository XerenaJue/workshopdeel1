package Interface;

import java.sql.*;
import java.util.List;

import POJO.Adres;
import POJO.Klant;

public interface AdresDao {
	
	public Adres createAdres(int klant_id, Adres adres) throws SQLException;
	public int findAdres(String straatnaam, String postcode, int huisnummer,
			String toevoeging, String woonplaats) throws SQLException;
	public List<Adres> findAdres(String straatnaam) throws SQLException;
	public List<Adres> findAdres(String postcode, int huisnummer) throws SQLException;
	public List<Adres> findAdres(int klant_id) throws SQLException;	
	public void update(Adres adres) throws SQLException;
	public List<Adres> findAll() throws SQLException;
	public void deleteAdres(Klant klant, Adres adres) throws SQLException;
	
	
}
