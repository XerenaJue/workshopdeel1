/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.*;

import POJO.ArtikelBestelling;
import POJO.ArtikelPOJO;
import POJO.Bestelling;
import POJO.Klant;
import opdracht2.ConnectionFactory;
import java.lang.reflect.Field;
import Interface.BestellingInterface;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author jeroen
 */
public class BestellingDAOJson implements BestellingInterface {

        Connection connection;
        PreparedStatement statement;
        ResultSet resultSet;
        Logger logger = LoggerFactory.getLogger(BestellingDAOJson.class);
        
        // in facade maak je een arrayList van artikelBestellingen die geupdate moeten worden. 
        //vervolgens werk je met een for each loop om voor elke aan te passen artikelBestelling een
        // updateBestelling te invoken.
    @Override
        public void updateBestelling(ArtikelBestelling artikelBestelling,int bestel_id ) throws SQLException{
            //artikelBestelling.setArtikelenAantal(4);
            String SQLStatement ="update bestelling_has_artikel set aantal_artikelen = ? where artikel_artikel_id = "+artikelBestelling.getArtikelPojo().getArtikelID()+ " AND bestelling_bestelling_id = "+ bestel_id;
        try {
            createCS(SQLStatement);
            statement.setInt(1,artikelBestelling.getArtikelenAantal());
            statement.executeUpdate();
        }
            catch(SQLException ex){}
            finally{closeConnectionStatement();}
        logger.info("de bestelling is succesvol geupdate");
                
            
        
    }
    
    @Override
    public void buildInsertStatement(Bestelling bestelling){
            Field[] declaredFields = Bestelling.class.getDeclaredFields();
            for(Field field: declaredFields){
                field.setAccessible(true);
            
            
            
            
            }
            
    }


    @Override
    public void addArtikelToBestelling(Bestelling bestelling, ArtikelBestelling artikelBestelling){
           String query ="INSERT INTO bestelling_has_artikel (bestelling_bestelling_id,"+
               "artikel_artikel_id,aantal_artikelen) VALUES ("+bestelling.getBestelling_id()+","+ artikelBestelling.getArtikelPojo().getArtikelID() + ","+artikelBestelling.getArtikelenAantal()+")"; 
           try{
           createCS(query);
           statement.executeUpdate();
           }
           catch(SQLException e){
               logger.error("helaas pindakaas");
           }
           finally{closeConnectionStatement();}
                   logger.info("er is succesvol een artikel toegevoegd");

           
    }
    
    @Override
    public List<ArtikelBestelling> readArtikelBestelling(Bestelling bestelling) throws SQLException{
        //create.put("artikellijst", list);
        //create.put("bestelling_id", bestelling.getBestelling_id());
        //create.put("klant_id", bestelling.getKlant_id()); 		
         List<ArtikelBestelling> artikelBestellingen = new ArrayList<>();      
        
                        JSONParser jsonParser = new JSONParser();
                        String jsonFilePath = "C:\\Users\\maurice\\Desktop\\Workshoptest.json";
		try {

			FileReader fileReader = new FileReader(jsonFilePath);

			JSONObject jsonObject = (JSONObject)jsonParser.parse(fileReader);

			JSONArray artikellijst = (JSONArray) jsonObject.get("artikellijst" + bestelling.getBestelling_id() + "_" + 
                                bestelling.getKlant_id());


			Iterator i = artikellijst.iterator();

			while (i.hasNext()) {
                                artikelBestellingen.add((ArtikelBestelling)i.next());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return artikelBestellingen;
    }    
    
 
    
    @Override
    public Bestelling createBestelling(Klant klant)throws SQLException {
        
        Bestelling bestelling = new Bestelling();
        bestelling.setKlant(klant.getKlantID());
        
        String query = "insert into bestelling (klant_klant_id) values (" + klant.getKlantID() + ")";
        try (Connection connection = ConnectionFactory.getMySQLConnection(); 
                    PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); ){
            stmt.executeUpdate(); 
            try (ResultSet resultSet = stmt.getGeneratedKeys();) {
                if (resultSet.isBeforeFirst()) {
                    resultSet.next();
                    bestelling.setBestellingID(resultSet.getInt(1));
                }
            }
        }
        logger.info("bestelling gecreeerd");
        return bestelling;
    }
    // idem dito als bij updateBestelling
    @Override
     public void deleteArtikelFromBestelling(ArtikelBestelling artikelBestelling, int bestel_id) throws SQLException {
     String queryBHA = "delete from bestelling_has_artikel where artikel_artikel_id = " + artikelBestelling.getArtikelPojo().getArtikelID()+" AND bestelling_bestelling_id = "+bestel_id;
         try (Connection connection = ConnectionFactory.getMySQLConnection();
                PreparedStatement statement = connection.prepareStatement(queryBHA); ){
            statement.executeUpdate();
           logger.info("artikel succesvol gedelete"); 
        }         
     }   
    
    
    @Override
    public void deleteBestelling(Bestelling bestelling) throws SQLException {
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
                Statement statement = connection.createStatement(); ){ 
            connection.setAutoCommit(false);
            String queryBHA = "delete from bestelling_has_artikel where bestelling_bestelling_id = " + bestelling.getBestelling_id();
            statement.addBatch(queryBHA);
            String queryBestelling = "delete from bestelling where bestelling_id = " + bestelling.getBestelling_id();
            statement.addBatch(queryBestelling);
            statement.executeBatch();
            connection.commit();  
            logger.info("bestelling gedelete");
    }
    }
    @Override
        public void deleteBestelling(ArrayList<Bestelling> bestelling) throws SQLException {
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
                Statement statement = connection.createStatement(); ){ 
            connection.setAutoCommit(false);
            for (Bestelling element: bestelling){
                String queryBHA = "delete from bestelling_has_artikel where bestelling_bestelling_id = " + element.getBestelling_id();
                statement.addBatch(queryBHA);
                String queryBestelling = "delete from bestelling where bestelling_id = " + element.getBestelling_id();
                statement.addBatch(queryBestelling);
            }
            statement.executeBatch();
            connection.commit();
            logger.info("bestelling gedelete");            
    }
    }
    
    @Override
    public void deleteBestellingen(Klant klant) throws SQLException{
        ArrayList<Bestelling> bestellingen = new ArrayList<>();
        bestellingen = (ArrayList<Bestelling>)findAlleBestellingen(klant);
            deleteBestelling(bestellingen);
            logger.info("alle bestellignen van klant gedelete");
    }
    
    @Override
    public List<Bestelling> findAlleBestellingen() {
             
        List<Bestelling> bestellingen = new ArrayList<>();
        
        String query = "select * from bestelling";
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                       
            while (resultSet.next()) {
               Bestelling bestelling = new Bestelling();      
               bestelling.setBestellingID(resultSet.getInt("bestelling_id"));
               bestelling.setKlant(resultSet.getInt("klant_klant_id"));  
               bestellingen.add(bestelling);
            }
        }
        catch (SQLException ex) {
            System.out.println("gaat iets fout in readAlleBestellingen" );
            ex.printStackTrace();
        }
        if (bestellingen.isEmpty() )bestellingen.add(new Bestelling());
                    logger.info("alle bestellingen weergegeven");
        return bestellingen;
    }

    @Override
    public List<Bestelling> findAlleBestellingen(Klant klant) {
             
        List<Bestelling> bestellingen = new ArrayList<>();
        
        String query = "select * from bestelling where klant_klant_id = " +klant.getKlantID();
        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();  ){
                       
            while (resultSet.next()) {
               Bestelling bestelling = new Bestelling();      
               bestelling.setBestellingID(resultSet.getInt("bestelling_id"));
               bestelling.setKlant(resultSet.getInt("klant_klant_id"));  
               bestellingen.add(bestelling);
            }
        }
        catch (SQLException ex) {
            logger.error("gaat iets fout in readAlleBestellingen" );
            ex.printStackTrace();
        }
        if (bestellingen.isEmpty() )bestellingen.add(new Bestelling());
                    logger.info("alle bestellingen van klant weergegeven");
        return bestellingen;
    }
    
       @Override
    public void createBestelling(Bestelling bestelling){} 
        


    public void createBestelling(Bestelling bestelling, int klant_id)throws SQLException{
        
        JSONObject create = new JSONObject();
        JSONArray list = new JSONArray();
        bestelling.setKlant(klant_id);
        for (ArtikelBestelling artikel: (ArrayList<ArtikelBestelling>)bestelling.getArtikelBestellingList())
            list.add(artikel);
            
        
        create.put("artikellijst" + bestelling.getBestelling_id() + "_" + bestelling.getKlant_id(), list);

	try {

		FileWriter file = new FileWriter("C:\\Users\\maurice\\Desktop\\Workshoptest.json",true);
		file.write(create.toJSONString());
                file.flush();
		file.close();

	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    @Override
        public void closeConnectionStatement(){
        {
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
            public void createCS(String SQLStatement) throws SQLException{
        connection = ConnectionFactory.getMySQLConnection();
        statement = connection.prepareStatement(SQLStatement);
    }
    @Override
            public void createCSR(String SQLStatement) throws SQLException{ // hetzelfde als createCS alleen dan met resultSet init.
        connection = ConnectionFactory.getMySQLConnection();
        statement = connection.prepareStatement(SQLStatement);
        resultSet = statement.executeQuery();
            }
}

