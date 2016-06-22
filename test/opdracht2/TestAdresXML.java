package opdracht2;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import DAO.AdresDaoXml;
import POJO.Adres;
import POJO.Klant;

public class TestAdresXML {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCreateAdres() throws SQLException {
		Adres adres = new Adres();
		adres.setStraatnaam("Corellia");
		adres.setPostcode("2566GJ");
		adres.setHuisnummer(22);
		adres.setWoonplaats("Galaxy Far Far Away");
		AdresDaoXml instanceAdres = new AdresDaoXml();
		
		instanceAdres.createAdres(45, adres);
		System.out.println(adres);
	}

	@Test
	public void testFindAdresID() throws SQLException {
		Adres adres = new Adres();
		adres.setStraatnaam("Corellia - 2");
		adres.setPostcode("2566GJ");
		adres.setHuisnummer(22);
		adres.setWoonplaats("Galaxy Far Far Away");
	
		AdresDaoXml instanceAdres = new AdresDaoXml();
		
		instanceAdres.findAdres(adres.getStraatnaam(), adres.getPostcode(), adres.getHuisnummer(),
				adres.getToevoeging(), adres.getWoonplaats());
		System.out.println(adres);
	}

	@Test
	public void testFindAdresString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAdresStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAdresInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() throws SQLException {
		Adres adres = new Adres();
		adres.setStraatnaam("Corellia - 2");
		adres.setPostcode("2566GJ");
		adres.setHuisnummer(22);
		adres.setWoonplaats("Galaxy Far Far Away");
		AdresDaoXml instanceAdres = new AdresDaoXml();
		
		instanceAdres.update(adres);
		System.out.println(adres);
	}

	@Test
	public void testDeleteAdres() throws SQLException {
		Adres adres = new Adres();
		Klant klant = new Klant();
		adres.setStraatnaam("Corellia");
		adres.setPostcode("2566GJ");
		adres.setHuisnummer(22);
		adres.setWoonplaats("Galaxy Far Far Away");
		AdresDaoXml instanceAdres = new AdresDaoXml();
		
		instanceAdres.deleteAdres(klant, adres);
		System.out.println(instanceAdres.findAll());
	}

}
