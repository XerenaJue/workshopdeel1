package DAOs;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class TestDAOCreateKlant {
	private KlantDAOImpl daoimpl;
	
	@Before
	public void setUp() throws Exception {
		daoimpl = new KlantDAOImpl();
	}

	@Test
	public void testCreate() throws SQLException {
        Klant klant = new Klant();
        klant.setAchternaam("Vos");
        klant.setEmail("lVos@gmail.com");
        klant.setTussenvoegsel("Sluwe");
        klant.setVoornaam("Lars");
		daoimpl.create(klant); 
	}
	
	@Test
	public void testFindByName1() throws SQLException {
		 /*     Klant klant = new Klant();
        klant.setAchternaam("Vos");
        klant.setEmail("lVos@gmail.com");
        klant.setTussenvoegsel("Sluwe");
        klant.setVoornaam("Lars");
        klant.setKlantID(21);
*/	
        Klant result = daoimpl.FindByName("Lars");
        assertEquals("Vos", result.getAchternaam());
	}
	
	@Test
	public void testFindByName2() throws SQLException {
        Klant result = daoimpl.findByName("Lars", "Vos");
        assertEquals("Sluwe", result.getTussenvoegsel());
	}
	
	@Test
	public void testUpdate() throws SQLException {
        Klant klant = new Klant();
        klant.setAchternaam("Krai");
        klant.setEmail("lVos@gmail.com");
        klant.setTussenvoegsel("Sluwe");
        klant.setVoornaam("Karel");
        klant.setKlantID(21);
	
		daoimpl.update(klant);		
	}

	@Test
	public void testFindByID() throws SQLException {

        Klant result = daoimpl.findByID(21);
        assertEquals("Karel", result.getVoornaam());
	}
	
	@Test
	public void testDelete() throws SQLException {
	    Klant klant = new Klant();
        klant.setAchternaam("Krai");
        klant.setEmail("lVos@gmail.com");
        klant.setTussenvoegsel("Sluwe");
        klant.setVoornaam("Karel");
        klant.setKlantID(21);
        
		daoimpl.delete(klant);
	}
}
