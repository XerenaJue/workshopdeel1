package opdracht2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import DAO.AdresDaoJson;
import POJO.Adres;
import POJO.Klant;

public class TestAdresJson {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCreate() throws SQLException {
		Adres adres = new Adres();
		adres.setStraatnaam("Corellia");
		adres.setPostcode("2566GJ");
		adres.setHuisnummer(22);
		adres.setWoonplaats("Galaxy Far Far Away");
		adres.setAdresID(78);
		AdresDaoJson instanceAdres = new AdresDaoJson();
		
		instanceAdres.createAdres(45, adres);
		System.out.println(adres);
	}
	
	@Test
	public void testUpdate() throws SQLException {
		Adres adres = new Adres();
		adres.setStraatnaam("Space");
		adres.setPostcode("4567LD");
		adres.setHuisnummer(78);
		adres.setWoonplaats("Galaxy Far Far Away");
		adres.setAdresID(45);
		AdresDaoJson instanceAdres2 = new AdresDaoJson();
		instanceAdres2.update(adres);
	}
	
	@Test
	public void testFindAll() throws SQLException {
		AdresDaoJson adresDaoJson = new AdresDaoJson();
		System.out.println(adresDaoJson.findAll());
	}
	
	@Test
	public void testFindAdresID() throws SQLException {
		Adres adres = new Adres();
		adres.setStraatnaam("Space");
		adres.setPostcode("4567LD");
		adres.setHuisnummer(78);
		adres.setToevoeging("");
		adres.setWoonplaats("Galaxy Far Far Away");
		AdresDaoJson adresDaoJson = new AdresDaoJson();
		adres.setAdresID(adresDaoJson.findAdres(adres.getStraatnaam(), adres.getPostcode(), adres.getHuisnummer(), 
				adres.getToevoeging(), adres.getWoonplaats()));
		System.out.println(adres.getAdresID());
		
	}
	@Test
	public void testFindAdresByStraatnaam() throws SQLException {
		Adres adres = new Adres();
		adres.setStraatnaam("Space");
		AdresDaoJson adresDaoJson = new AdresDaoJson();
		adresDaoJson.findAdres(adres.getStraatnaam());
		System.out.println(adres);
	}
	@Test
	public void testDeleteAdres() throws SQLException {
		Adres adres = new Adres();
		adres.setStraatnaam("Space");
		adres.setPostcode("4567LD");
		adres.setHuisnummer(78);
		adres.setToevoeging("");
		adres.setWoonplaats("Galaxy Far Far Away");
		AdresDaoJson adresDaoJson = new AdresDaoJson();
		adresDaoJson.deleteAdres(new Klant(), adres);
		System.out.println(adresDaoJson.findAll());
	}
}
