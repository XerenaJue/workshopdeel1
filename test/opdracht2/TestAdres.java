package opdracht2;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAdres {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		FillBatchDatabase.clearDatabase();
	}

	@Test
	public void testAdres() throws SQLException {
		Klant klant = new Klant();
		klant.setVoornaam("Han");
		klant.setTussenvoegsel("");
		klant.setAchternaam("Solo");
		klant.setEmail("hansolo@hotmail.com");
		klant.setKlantID(1);
		KlantDAOImpl instance = new KlantDAOImpl();
		instance.create(klant);
		Adres adres = new Adres();
		adres.setStraatnaam("Corellia");
		adres.setPostcode("2566GJ");
		adres.setHuisnummer(22);
		adres.setWoonplaats("Galaxy Far Far Away");
		AdresDaoImpl instanceAdres = new AdresDaoImpl();
		int id = klant.getKlantID();
		instanceAdres.update(id, adres);

		assertEquals("Corellia", adres.getStraatnaam());
		assertEquals("2566GJ", adres.getPostcode());
		assertEquals(22, adres.getHuisnummer());
		assertEquals("Galaxy Far Far Away", adres.getWoonplaats());

	}

	@Test
	public void testFindAdres() throws SQLException {
		Klant klant = new Klant();
		klant.setVoornaam("Han");
		klant.setTussenvoegsel("");
		klant.setAchternaam("Solo");
		klant.setEmail("hansolo@hotmail.com");
		klant.setKlantID(1);
		KlantDAOImpl instance = new KlantDAOImpl();
		instance.create(klant);
		Adres adres = new Adres();
		adres.setStraatnaam("Corellia");
		adres.setPostcode("2566GJ");
		adres.setHuisnummer(22);
		adres.setToevoeging("");
		adres.setWoonplaats("Galaxy Far Far Away");
		AdresDaoImpl instanceAdres = new AdresDaoImpl();
		int id = klant.getKlantID();
		
		instanceAdres.update(id, adres);

		instanceAdres.findAdres(adres.getStraatnaam());

		instanceAdres.findAdres(klant);
		System.out.println("Gaat tot nu toe goed");

		instanceAdres.findAdres(adres.getPostcode(), adres.getHuisnummer());
		System.out.println("So far, so good");

		instanceAdres.findAdres(adres.getStraatnaam(), adres.getPostcode(), adres.getHuisnummer(),
				adres.getToevoeging(), adres.getWoonplaats());
		System.out.println("Deze werkt ook");

	}

}
