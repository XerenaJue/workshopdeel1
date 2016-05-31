package opdracht2;

import java.sql.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KlantDAOImpl implements KlantDAO {
	static Logger logger = LoggerFactory.getLogger(KlantDAOImpl.class);
    /*Connection connection;
    ResultSet resultSet;
    PreparedStatement statement;*/
    
    @Override
    public Klant findKlant(Klant bestaandeKlant) {//throws SQLException {        
        int klantID = bestaandeKlant.getKlantID();
        String klantVoornaam = bestaandeKlant.getVoornaam();
        String klantAchternaam = bestaandeKlant.getAchternaam();
        
        if (klantID != 0) {        	
        	logger.info("klant zoeken op id -- id was niet nul");
        	return findByID(klantID);
        	}        
        
        else if (klantVoornaam.length() >= 1 && klantAchternaam.length() >= 1) {
        	logger.info("klant zoeken op voor en achternaam -- namen waren groter dan 0");
        	return findByName(klantVoornaam, klantAchternaam);
        }
        
        else if (klantVoornaam.length() >= 1) {
        	logger.info("klant zoeken op voornaam -- voornaam was groter dan 0");
        	return FindByName(klantVoornaam);
        	}
        
        else {
        	logger.info("geen bruikbaare velden gevonden om klant te zoeken probeer op andere manier");
        	return null;
        }
    } 
    
    @Override
    public List<Klant> findKlant(Adres klantAdres) {//throws SQLException {
    	List<Klant> klanten = new ArrayList<>();
        Klant klant = new Klant();
    	int adresId = klantAdres.getAdresID();
    	String query = "SELECT * FROM klant_has_adres WHERE adres_adres_id = " + adresId;
    	try (Connection connection = ConnectionFactory.getMySQLConnection();
    			PreparedStatement preparedStatement = connection.prepareStatement(query);
    			ResultSet resultSet = preparedStatement.executeQuery();) {    		
    		
    			while (resultSet.next()) {   
    				int klantID = resultSet.getInt("klant_klant_id");
    				klant = findByID(klantID);
                    klanten.add(klant);
    			}
    	
    	} catch (SQLException ex) {
    		logger.info("gaat iets mis");    		
    	}
    	return klanten;
    }
    
    /* oude findklant met adres methode
    @Override
    public Klant findKlant(Adres klantAdres) throws SQLException {
        Klant klant = new Klant();
        String postcode = klantAdres.getPostcode();
        int huisnr = klantAdres.getHuisnummer();
        String toevoeging = klantAdres.getToevoeging();
        String query = String.format("SELECT * FROM klant WHERE postcode = '%s'"
                    +  "AND huisnummer = %d AND toevoeging = '%s'", postcode, huisnr, toevoeging);
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {
            if (resultSet.next()) {
                    
                klant.setKlantID(resultSet.getInt("klant_ID"));
                klant.setVoornaam(resultSet.getString("voornaam"));
                klant.setAchternaam(resultSet.getString("achternaam"));
                klant.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
                klant.setEmail(resultSet.getString("email"));
            }
        }
        return klant;
    }*/
    
    @Override
    public List<Klant> findAll() {//throws SQLException {
        List<Klant> klanten = new ArrayList<>();
        Klant klant;
        String query = "SELECT * FROM klant";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
        		PreparedStatement stmt = connection.prepareStatement(query);
        		ResultSet resultset = stmt.executeQuery();){
        	/*connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(SQL_QUERY);
            resultSet = statement.executeQuery();*/

            while(resultset.next()) {
                //stop klant object in klanten List
                klant = new Klant();
                klant.setKlantID(resultset.getInt("klant_ID"));
                klant.setVoornaam(resultset.getString("voornaam"));
                klant.setAchternaam(resultset.getString("achternaam"));
                klant.setTussenvoegsel(resultset.getString("tussenvoegsel"));
                klant.setEmail(resultset.getString("email"));
                klanten.add(klant);
            } 
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }
        /*finally {
            // kijk of er verbinding is en zo ja sluit deze
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }   
        }*/
        logger.info("Klanten gevonden");
        return klanten;
    }

    @Override
    public Klant findByID(int klantID) {//throws SQLException {
        String query = "SELECT * FROM klant WHERE klant_id = " + klantID;
        Klant klant = new Klant();
        try (Connection connection = ConnectionFactory.getMySQLConnection();
        		PreparedStatement stmt = connection.prepareStatement(query);
        		ResultSet resultset = stmt.executeQuery();){
        	/*connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();*/
            ///crieer klant en set de gegevens er in
            //klant = new Klant();
            if (resultset.next()) {                
                klant.setKlantID(resultset.getInt("klant_ID"));
                klant.setVoornaam(resultset.getString("voornaam"));
                klant.setAchternaam(resultset.getString("achternaam"));
                klant.setTussenvoegsel(resultset.getString("tussenvoegsel"));
                klant.setEmail(resultset.getString("email"));
            }
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }
        /*finally {
            // kijk of er verbinding is en zo ja sluit deze
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }   
        } */
        logger.info("Klant gevonden");
        return klant;
    }

    @Override
    public Klant findByName(String voornaam, String achternaam) {//throws SQLException {
        String query = "SELECT * FROM klant WHERE voornaam = '" + voornaam + "' AND achternaam = '" + achternaam + "'";
        Klant klant = new Klant();
        try (Connection connection = ConnectionFactory.getMySQLConnection();
        		PreparedStatement stmt = connection.prepareStatement(query);
        		ResultSet resultset = stmt.executeQuery();){
        	/*connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();*/
            ///crieer klant en set de gegevens er in
            //klant = new Klant();
            if (resultset.next()) {
                klant.setKlantID(resultset.getInt("klant_ID"));
                klant.setVoornaam(resultset.getString("voornaam"));
                klant.setAchternaam(resultset.getString("achternaam"));
                klant.setTussenvoegsel(resultset.getString("tussenvoegsel"));
                klant.setEmail(resultset.getString("email"));
            }
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }
        /*finally {
            // kijk of er verbinding is en zo ja sluit deze
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }   
        } */
        logger.info("Klant gevonden");
        return klant;
    }

    @Override
    public Klant FindByName(String voornaam) {//throws SQLException {
        String query = "SELECT * FROM klant WHERE voornaam = '" + voornaam + "'";
        Klant klant = new Klant();
        try (Connection connection = ConnectionFactory.getMySQLConnection();
        		PreparedStatement stmt = connection.prepareStatement(query);
        		ResultSet resultset = stmt.executeQuery();){
        	/*connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(); */
            ///crieer klant en set de gegevens er in
            //klant = new Klant();
            if (resultset.next()) {
                klant.setKlantID(resultset.getInt("klant_ID"));
                klant.setVoornaam(resultset.getString("voornaam"));
                klant.setAchternaam(resultset.getString("achternaam"));
                klant.setTussenvoegsel(resultset.getString("tussenvoegsel"));
                klant.setEmail(resultset.getString("email"));
            }
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }
        /*finally {
            // kijk of er verbinding is en zo ja sluit deze
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }   
        } */
        logger.info("Klant gevonden");
        return klant;        
    } 
    
    @Override
    public void create(Klant klant) {//throws SQLException {
        String query = "INSERT INTO klant(voornaam, achternaam, tussenvoegsel, email) VALUES (?, ? ,?, ?)";
        ResultSet resultSet;
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);){
        	/*connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);*/
            stmt.setString(1, klant.getVoornaam());
            stmt.setString(2, klant.getAchternaam());
            stmt.setString(3, klant.getTussenvoegsel());
            stmt.setString(4, klant.getEmail());
            stmt.executeUpdate(); 
            //wijs door database gegenereerde id toe aan klant
            resultSet = stmt.getGeneratedKeys();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                klant.setKlantID(resultSet.getInt(1));
            }            
            logger.info("Klant is aangemaakt");
            //System.out.println("Klant is succesvol aangemaakt");
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }
        /*finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }            
        } */       
    }

    @Override
    public void update(Klant klant) {//throws SQLException {
        String query = "UPDATE klant SET voornaam = ?, achternaam = ?, tussenvoegsel = ?, email = ? WHERE klant_id = ?";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);){
        	/*connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query); */
            stmt.setString(1, klant.getVoornaam());
            stmt.setString(2, klant.getAchternaam());
            stmt.setString(3, klant.getTussenvoegsel());
            stmt.setString(4, klant.getEmail());
            stmt.setInt(5, klant.getKlantID());            
            stmt.executeUpdate();
            logger.info("Gegevens zijn succesvol gewijzigd");
            //System.out.println("Gegevens zijn succesvol gewijzigd");
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }
        /*finally {
            // kijk of er verbinding is en zo ja sluit deze
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }   
        } */       
    }

    @Override
    public void delete(Klant klant){ //throws SQLException {
        String query = "DELETE FROM klant WHERE klant_id = " + klant.getKlantID();        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
        		){
        	/*connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query);*/
            stmt.executeUpdate();
            logger.info("Klant verwijderd");
            System.out.println("Klant gegevens zijn succesvol verwijderd");
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }
        /*finally {
            // kijk of er verbinding is en zo ja sluit deze
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }   
        }  */      
    }    
}
