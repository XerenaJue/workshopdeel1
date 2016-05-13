package opdracht2;

import java.sql.*;
import java.util.*;

public class KlantDAOImpl implements KlantDAO {
    Connection connection;
    ResultSet resultSet;
    PreparedStatement statement;
    
    @Override
    public Klant findKlant(Klant bestaandeKlant) throws SQLException {
        
        int klantID = bestaandeKlant.getKlantID();
        
        return findByID(klantID);
    } 
    
    @Override
    public Klant findKlant(Adres klantAdres) throws SQLException {
        Klant klant = new Klant();
        String postcode = klantAdres.getPostcode();
        int huisnr = klantAdres.getHuisnummer();
        String toevoeging = klantAdres.getToevoeging();
        String query = String.format("SELECT * FROM klant WHERE postcode =  %s"
                    +  "AND huisnummer = %d AND toevoeging = %s", postcode, huisnr, toevoeging);
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();) {

            klant.setKlantID(resultSet.getInt("klant_ID"));
            klant.setVoornaam(resultSet.getString("voornaam"));
            klant.setAchternaam(resultSet.getString("achternaam"));
            klant.setTussenvoegsel("tussenvoegsel");
            klant.setEmail(resultSet.getString("email"));
        }
        return klant;
    }
    
    @Override
    public List<Klant> findAll() throws SQLException {
        List<Klant> klanten = new ArrayList<>();
        Klant klant;
        String SQL_QUERY = "Select * from klant";
        try {
        	connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(SQL_QUERY);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                //stop klant object in klanten List
                klant = new Klant();
                klant.setKlantID(resultSet.getInt("klant_ID"));
                klant.setVoornaam(resultSet.getString("voornaam"));
                klant.setAchternaam(resultSet.getString("achternaam"));
                klant.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
                klant.setEmail(resultSet.getString("email"));
                klanten.add(klant);
            } 
        } finally {
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
        }
        return klanten;
    }

    @Override
    public Klant findByID(int klantID) throws SQLException {
        String query = "Select * from klant where klant_id = " + klantID;
        Klant klant;
        try {
        	connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            ///crieer klant en set de gegevens er in
            klant = new Klant();
            if (resultSet.next()) {                
                klant.setKlantID(resultSet.getInt("klant_ID"));
                klant.setVoornaam(resultSet.getString("voornaam"));
                klant.setAchternaam(resultSet.getString("achternaam"));
                klant.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
                klant.setEmail(resultSet.getString("email"));
            }
        } finally {
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
        }
        return klant;
    }

    @Override
    public Klant findByName(String voornaam, String achternaam) throws SQLException {
        String query = "Select * from klant where voornaam = '" + voornaam + "' and achternaam = '" + achternaam + "'";
        Klant klant;
        try {
        	connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            ///crieer klant en set de gegevens er in
            klant = new Klant();
            if (resultSet.next()) {
                klant.setKlantID(resultSet.getInt("klant_ID"));
                klant.setVoornaam(resultSet.getString("voornaam"));
                klant.setAchternaam(resultSet.getString("achternaam"));
                klant.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
                klant.setEmail(resultSet.getString("email"));
            }
        } finally {
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
        }
        return klant;
    }

    @Override
    public Klant FindByName(String voornaam) throws SQLException {
        String query = "Select * from klant where voornaam = '" + voornaam + "'";
        Klant klant;
        try {
        	connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            ///crieer klant en set de gegevens er in
            klant = new Klant();
            if (resultSet.next()) {
                klant.setKlantID(resultSet.getInt("klant_ID"));
                klant.setVoornaam(resultSet.getString("voornaam"));
                klant.setAchternaam(resultSet.getString("achternaam"));
                klant.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
                klant.setEmail(resultSet.getString("email"));
            }
        } finally {
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
        }
        return klant;
    } 
    
    @Override
    public void create(Klant klant) throws SQLException {
        String query = "Insert into klant(voornaam, achternaam, tussenvoegsel, email) values (?, ? ,?, ?)";
        try {
        	connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, klant.getVoornaam());
            statement.setString(2, klant.getAchternaam());
            statement.setString(3, klant.getTussenvoegsel());
            statement.setString(4, klant.getEmail());
            
            //wijs door database gegenereerde id toe aan klant
            resultSet = statement.getGeneratedKeys();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                klant.setKlantID(resultSet.getInt(1));
            }            
            statement.executeUpdate(); 
            System.out.println("Klant is succesvol aangemaakt");
        } finally {
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
        }        
    }

    @Override
    public void update(Klant klant) throws SQLException {
        String query = "update klant set voornaam = ?, set achternaam = ?, set tussenvoegsel = ?, email = ? where klant_ID = ?";
        try {
        	connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, klant.getVoornaam());
            statement.setString(2, klant.getAchternaam());
            statement.setString(3, klant.getTussenvoegsel());
            statement.setString(4, klant.getEmail());
            statement.setInt(5, klant.getKlantID());            
            statement.executeUpdate();
            System.out.println("Gegevens zijn succesvol gewijzigd");
        } finally {
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
        }        
    }

    @Override
    public void delete(Klant klant) throws SQLException {
        String query = "Delete from klant where klant_id = " + klant.getKlantID();        
        try {
        	connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            System.out.println("Klant gegevens zijn succesvol verwijderd");
        } finally {
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
        }        
    }
}
