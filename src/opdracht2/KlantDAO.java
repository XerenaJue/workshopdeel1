package opdracht2;

import java.sql.SQLException;
import java.util.List;

public interface KlantDAO {
    public List<Klant> findAll();// throws SQLException;
    public Klant findKlant(Klant bestaandeKlant);//throws SQLException;  
    public List<Klant> findKlant(Adres klantAdres);//throws SQLException; 
    public Klant findByID(int klantId);// throws SQLException;
    public Klant findByName(String voornaam, String achternaam);// throws SQLException;
    public Klant FindByName(String voornaam);// throws SQLException;
    public void create(Klant klant);// throws SQLException;
    public void update(Klant klant);// throws SQLException;
    public void delete(Klant klant);// throws SQLException;   
}
