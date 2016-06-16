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

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;

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
    
    
    //http://www.mkyong.com/java/how-to-convert-java-object-to-from-json-jackson/ <<< voor het beter inlezen..
    @Override
    public List<ArtikelBestelling> readArtikelBestelling(Bestelling bestelling) throws SQLException{
        //create.put("artikellijst", list);
        //create.put("bestelling_id", bestelling.getBestelling_id());
        //create.put("klant_id", bestelling.getKlant_id()); 		
         List<ArtikelBestelling> artikelBestellingen = new ArrayList<>();
         ArtikelBestelling artikelBestelling;
         ArtikelPOJO artikelpojo;
        
                        JSONParser jsonParser = new JSONParser();
                        String jsonFilePath = "C:\\Users\\maurice\\Desktop\\Workshoptest.json";
		try {
                        JsonFactory jfactory = new JsonFactory();
                        JsonParser jParser = jfactory.createJsonParser(new File("C:\\Users\\maurice\\Desktop\\Workshoptest.json"));
                        while (jParser.nextToken() != JsonToken.END_OBJECT) {
                            String fieldname = jParser.getCurrentName();
                            
                            if ("klantID".equals(fieldname)) {
                                    jParser.nextToken();
                                    if (jParser.getIntValue() == bestelling.getKlant_id()){
                                        while (jParser.nextToken() != JsonToken.END_OBJECT){
                                            fieldname = jParser.getCurrentName();
                                            if ("bestelID".equals(fieldname)){
                                                jParser.nextToken();
                                                if (jParser.getIntValue() == bestelling.getBestelling_id()){
                                                    jParser.nextToken();
                                                     while (jParser.nextToken() != JsonToken.END_ARRAY) {
                                                            while (jParser.nextToken() != JsonToken.END_OBJECT){
                                                                fieldname = jParser.getCurrentName();
                                                                if ("artikelID".equals(fieldname)){
                                                                    artikelBestelling = new ArtikelBestelling();
                                                                    jParser.nextToken();
                                                                    artikelpojo = new ArtikelPOJO();
                                                                    artikelpojo.setArtikelID(jParser.getIntValue());
                                                                    artikelBestelling.setArtikelPojo(artikelpojo);
                                                                    jParser.nextValue();
                                                                    artikelBestelling.setArtikelenAantal(jParser.getIntValue());
                                                                    artikelBestellingen.add(artikelBestelling);
                                                                    
                                                                    
                                                                }
                                                            }
                                                     }
                                                }
                                            }
                                        }
                                    }
                            }            
                            

                        }
                            jParser.close();                        
                }
                 catch (JsonGenerationException e) {

                    e.printStackTrace();

                } catch (JsonMappingException e) {

                     e.printStackTrace();

                 } catch (IOException e) {

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
     try {

	JsonFactory jfactory = new JsonFactory();

	/*** write to file ***/
        
	JsonGenerator jGenerator = jfactory.createJsonGenerator(new File("C:\\Users\\maurice\\Desktop\\Workshoptest.json"), JsonEncoding.UTF8);    
        
        jGenerator.writeStartObject();
        jGenerator.writeNumberField("klantID", bestelling.getKlant_id());        
        jGenerator.writeNumberField("bestelID", bestelling.getBestelling_id());
        jGenerator.writeFieldName("ArtikelBestellingArray");        
        jGenerator.writeStartArray();
             for (ArtikelBestelling artikel: (ArrayList<ArtikelBestelling>)bestelling.getArtikelBestellingList()){
                    
                 jGenerator.writeStartObject();
                    jGenerator.writeNumberField("artikelID",artikel.getArtikelPojo().getArtikelID());
                    jGenerator.writeNumberField("artikelAantal", artikel.getAantal_artikelen());
                    jGenerator.writeEndObject();                    
            }
        jGenerator.writeEndArray();
        jGenerator.writeEndObject();
        jGenerator.close();
     }
     catch (JsonGenerationException e) {

	e.printStackTrace();

     } catch (JsonMappingException e) {

	e.printStackTrace();

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

