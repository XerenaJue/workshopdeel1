package opdracht2;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAdres {

	@Before
	public void setUp() throws Exception {
		FillBatchDatabase.fillBatchDatabase();
	}

	@After
	public void tearDown() throws Exception {
		FillBatchDatabase.clearDatabase();
	}

	@Test
	public void testAdres() throws SQLException {
		
		Adres adres = new Adres();
		adres.setStraatnaam("Corellia");
		adres.setPostcode("2566GJ");
		adres.setHuisnummer(22);
		adres.setWoonplaats("Galaxy Far Far Away");
		adres.setAdresID(45);
		AdresDaoImpl instanceAdres = new AdresDaoImpl();
		
		instanceAdres.createAdres(45, adres);

		assertEquals("Corellia", adres.getStraatnaam());
		assertEquals("2566GJ", adres.getPostcode());
		assertEquals(22, adres.getHuisnummer());
		assertEquals("Galaxy Far Far Away", adres.getWoonplaats());

	}

	@Test
	public void testFindAdres() throws SQLException {
		
		Adres adres = new Adres();
		adres.setStraatnaam("Tatooine");
		adres.setPostcode("5866LS");
		adres.setHuisnummer(33);
		adres.setToevoeging("");
		adres.setWoonplaats("Galaxy Far Far Away");
		adres.setAdresID(2);
		
		AdresDaoImpl instanceAdres = new AdresDaoImpl();		
		
		instanceAdres.update(adres);
		System.out.println(adres);

		instanceAdres.findAdres(adres.getStraatnaam());
		
		Klant klant = new Klant();
		klant.setKlantID(22);
		
		instanceAdres.findAdres(klant.getKlantID());

		instanceAdres.findAdres(adres.getPostcode(), adres.getHuisnummer());

		instanceAdres.findAdres(adres.getStraatnaam(), adres.getPostcode(), adres.getHuisnummer(),
				adres.getToevoeging(), adres.getWoonplaats());
		
		instanceAdres.findAdres(adres.getStraatnaam());
		
		instanceAdres.deleteAdres(klant, adres);

	}

}
