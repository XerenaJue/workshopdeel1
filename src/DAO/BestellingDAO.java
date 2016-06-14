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


/**
 *
 * @author jeroen
 */
public class BestellingDAO implements BestellingInterface {

        Connection connection;
        PreparedStatement statement;
        ResultSet resultSet;
        Logger logger = LoggerFactory.getLogger(BestellingDAO.class);
        
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
        List<ArtikelBestelling> artikelBestellingen = new ArrayList<>(); // lijst met artikelBestellingPOJO's
        int i = 0;
        int z = 0;
        //haal het aantal artikel rijen op zodat BHA_artikelIdOpslag array gedeclared kan worden.
        String queryCount = "select COUNT(*) AS count from bestelling_has_artikel where bestelling_bestelling_id = " + bestelling.getBestelling_id();        
        createCSR(queryCount);//er wordt telkens gebruik gemaakt van dezelfde openstaande connectie.
        resultSet.next(); 
        int[][] BHA_artikelIdOpslag = new int[resultSet.getInt("count")][2];//array slaat in eerste dimensie artikel_id op en in tweede dimensie aantal_artikelen
                 
        //String queryBHA = "select * from bestelling_has_artikel where bestelling_bestelling_id = " + bestelling.getBestelling_id();
        String queryBHA = "select * from bestelling RIGHT OUTER JOIN bestelling_has_artikel ON bestelling_id = bestelling_bestelling_id where bestelling_bestelling_id = " + bestelling.getBestelling_id();
        statement = connection.prepareStatement(queryBHA);
        resultSet = statement.executeQuery();
       // ArtikelPOJO artikel = new ArtikelPOJO();
       // ArtikelBestelling artikelBestelling = new ArtikelBestelling();
        
        
             //haal artikel_id en aantal artikelen uit resultset          
            while (resultSet.next()) {
               BHA_artikelIdOpslag[i][0] = resultSet.getInt("artikel_artikel_id");
               BHA_artikelIdOpslag[i][1] = resultSet.getInt("aantal_artikelen");
               //System.out.println(i);
               
               i++;
              // if (i==4) break;
            }
        
        
        
        String queryJOIN;
        //gebruik opgeslagen artikel id's om artikel eigenschappen op te halen en breng ze onder in artikelPOJO en daarna in 
        // artikelBestelling Pojo samen met artikel_aantal.
        for (z = 0; z < i; z++){
            queryJOIN = "select * from artikel WHERE artikel_id = " + BHA_artikelIdOpslag[z][0];
        
        ArtikelPOJO artikel = new ArtikelPOJO();
        ArtikelBestelling artikelBestelling = new ArtikelBestelling();
        statement = connection.prepareStatement(queryJOIN);
        resultSet = statement.executeQuery();
                       
            while (resultSet.next()) {
               artikel.setArtikelID(resultSet.getInt("artikel_id"));
               artikel.setArtikelNaam(resultSet.getString("artikel_naam"));
               artikel.setArtikelPrijs(resultSet.getInt("artikel_prijs")); 
               artikelBestelling.setArtikelPojo(artikel);
               artikelBestelling.setArtikelenAantal(BHA_artikelIdOpslag[z][1]);
               artikelBestellingen.add(artikelBestelling);
            }
        }
        closeConnectionStatement();//sluit resultset,statement,connection
        logger.info("artikelbestelling lijst succesvol ingelezen");
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
    public void createBestelling(Bestelling bestelling)throws SQLException{
        try {
           String SQLStatement = "insert into bestelling (klant_klant_id) VALUES (?)";
            connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(SQLStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, bestelling.getKlant_id());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                bestelling.setBestellingID(resultSet.getInt(1));
            }
            SQLStatement = "insert into bestelling_has_artikel (bestelling_bestelling_id) VALUES (?)";
            statement = connection.prepareStatement(SQLStatement);
            statement.setInt(1, bestelling.getBestelling_id());
            statement.executeUpdate();
        }
            catch(SQLException ex){}
            finally{closeConnectionStatement();}
                    logger.info("lege bestelling gecreeerd");
                
                
                
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

