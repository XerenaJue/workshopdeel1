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
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileOutputStream;
import java.lang.reflect.Type;

/**
 *
 * @author jeroen
 */


public class BestellingDAOJson implements BestellingInterface {

        Connection connection;
        PreparedStatement statement;
        ResultSet resultSet;
        Logger logger = LoggerFactory.getLogger(BestellingDAOJson.class);
        //Type artikelType = new TypeToken<ArtikelBestelling>() {}.getType();
        //Type artikelTypeArray = new TypeToken<Bestelling>() {}.getType();
        Type artikelType = new TypeToken<HashMap<Integer, Bestelling>>() {}.getType();
            Gson gson = new Gson();
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
         HashMap<Integer, Bestelling> catalogus = new HashMap<>();
            //catalogus = bestelling;
         try (FileReader read = new FileReader("C:\\Users\\maurice\\Desktop\\Workshoptest.json");) {
                  
            catalogus = gson.fromJson(read, artikelType);
 

                  //je hoeft de arraylist Artikelbestelling niet in een hashmap te stoppen (waarvan de integer dan zou corresponderen
                  // met de bestelling ID) omdat de bestelling ID standaard al in het bestelling object opgeslagen wordt..
                  // dus je kunt met een hasNext for each loop alle elementen van catalogus doorlopen en met een if statement bekijken of
                  // het overeenkomt. Ik bedenk me net dat de key telkens uniek hoort te zijn, je zou dus niet meerdere keren dezelfde klantID
                  // als key kunnen opgeven... Je zult een unieke key moeten maken van zowel de klantID plus het bestellingID erachter geplakt.
                  // en een "_" ertussen. Zo kun je ook zeer gemakkelijk de juist bestelling vinden.
            catalogus = gson.fromJson(read, artikelType);
            bestelling = catalogus.get(bestelling.getKlant_id());
        } 
        catch (IOException ex) {
            logger.error("read input/output " +  ex);
        }
        
                        JSONParser jsonParser = new JSONParser();
                        String jsonFilePath = "C:\\Users\\maurice\\Desktop\\Workshoptest.json";
		try {
                        JsonFactory jfactory = new JsonFactory();
                        JsonParser jParser = jfactory.createJsonParser(new File("C:\\Users\\maurice\\Desktop\\Workshoptest.json"));
                        //ObjectMapper mapper = new ObjectMapper();
                        //ObjectNode node = (ObjectNode) mapper.readTree(jParser);
                        //node.get(jsonFilePath)
                }
                 catch (JsonGenerationException e) {

                    e.printStackTrace();

                }catch (IOException e) {

                e.printStackTrace();

                }
                
        return artikelBestellingen;
    }    
    
 
    
    @Override
    public Bestelling createBestelling(Klant klant)throws SQLException {
        
        Bestelling bestelling = new Bestelling();
        bestelling.setKlant_id(klant.getKlantID());
        
        String query = "insert into bestelling (klant_klant_id) values (" + klant.getKlantID() + ")";
        try (Connection connection = ConnectionFactory.getMySQLConnection(); 
                    PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); ){
            stmt.executeUpdate(); 
            try (ResultSet resultSet = stmt.getGeneratedKeys();) {
                if (resultSet.isBeforeFirst()) {
                    resultSet.next();
                    bestelling.setBestelling_id(resultSet.getInt(1));
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
        
        
        return bestellingen;
    }

    @Override
    public List<Bestelling> findAlleBestellingen(Klant klant) {
             
        List<Bestelling> bestellingen = new ArrayList<>();
        
        return bestellingen;
    }
    
       @Override
    public void createBestelling(Bestelling bestelling){
            //Bestelling catalogus;
            HashMap<String, Bestelling> catalogus = new HashMap<>();
            //catalogus = bestelling;
            try (FileReader read = new FileReader("C:\\Users\\maurice\\Desktop\\Workshoptest.json");) {
                  
            catalogus = gson.fromJson(read, artikelType);
            int nieuweID = catalogus.size() + 1;
            if (bestelling.getBestelling_id()== 0) {
                bestelling.setBestelling_id(4);                  //(nieuweID);
            }
                    }
                             catch (IOException ex) {
            logger.error("create input/output " +  ex);
        }
            try (FileWriter file = new FileWriter("C:\\Users\\maurice\\Desktop\\Workshoptest.json",true)) {
               catalogus.put(bestelling.getKlant_id() + "_" + bestelling.getBestelling_id(), bestelling);  
		file.write(gson.toJson(catalogus, artikelType));  
                logger.trace("artikel toegeveogd aan catalogus ");
            }
         catch (IOException ex) {
            logger.error("create input/output " +  ex);
        }
        
    }
    
     
        


    public void createBestelling(Bestelling bestelling, int klant_id)throws SQLException{
     try {

	JsonFactory jfactory = new JsonFactory();

	/*** write to file ***/
        
	JsonGenerator jGenerator = jfactory.createJsonGenerator(new FileOutputStream("C:\\Users\\maurice\\Desktop\\Workshoptest.json"), JsonEncoding.UTF8);    
        
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

     }  catch (IOException e) {

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

