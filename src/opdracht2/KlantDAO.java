package opdracht2;

import java.sql.SQLException;
import java.util.List;

public interface KlantDAO {
	public List<Klant> findAll() throws SQLException;
    public Klant findByID(int klantId) throws SQLException;
    public Klant findByName(String voornaam, String achternaam) throws SQLException;
    public Klant FindByName(String voornaam) throws SQLException;
    public boolean create(Klant klant) throws SQLException;
    public boolean update(Klant klant) throws SQLException;
    public boolean delete(Klant klant) throws SQLException;   
}
