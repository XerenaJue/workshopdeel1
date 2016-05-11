package nl.rsvier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author maurice
 */

import java.sql.*;
public class BestellingDAO{
	public static final String URL = "jdbc:mysql://localhost/WorkshopDeel1";
	public static final String USER = "hallo";
	public static final String PASSWORD = "doei";
        Connection connection;
        PreparedStatement statement;
        RowSet resultSet;


//no args constructor
    
    public BestellingDAO() throws SQLException, ClassNotFoundException{
            Class.forName("com.mysql.jdbc.Driver");
    }
    
    //overloaded getter method voor bestelling_id

    //overloaded getter method voor klant_id
    public Bestelling readBestelling(int klant_id) throws SQLException, ClassNotFoundException{
        Bestelling bestelling = new Bestelling();
        String SQLStatement = "select * from Bestelling where klant_id =?";
        try{
        createCS(SQLStatement);
        statement.setInt(1, klant_id);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            bestelling.setBestelling(resultSet.getInt("bestelling_id"));
            bestelling.setKlant(resultSet.getInt("klant_id"));
            bestelling.setAantalArtikel1(resultSet.getInt("artikel_aantal"));
            bestelling.setAantalArtikel2(resultSet.getInt("artikel1_aantal"));
            bestelling.setAantalArtikel3(resultSet.getInt("artikel2_aantal"));        
        }
        }
        catch (SQLException e){}
        finally{closeCS();}
        
        return bestelling; 
    }
    
    public void createCS(String SQLStatement) throws SQLException, ClassNotFoundException{
        connection = DriverManager.getConnection(URL,USER,PASSWORD);
        statement = connection.prepareStatement(SQLStatement);
    }
    
    public void closeCS(){
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
    //public void createCS(String SQLStatement,boolean RSflag) throws SQLException, ClassNotFoundException{
      //  connection = DriverManager.getConnection(URL,USER,PASSWORD);
      //  statement = connection.prepareStatement(SQLStatement);
       // switch(RSflag){
        //    case true: resultSet = statement.executeQuery(); break;
       //     case false: resultSet = statement.executeUpdate(); break;
       // }
    //}
    
    public boolean create(Klant klant, Bestelling bestelling)throws SQLException, ClassNotFoundException{
           String SQLStatement = "Insert into Bestelling (artikel_aantal, artikel2_aantal, artikel3_aantal) values (?, ?, ?)";
        try {
            connection = DriverManager.getConnection(URL , USER, PASSWORD);
            statement = connection.prepareStatement(SQLStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, bestelling.getAantalArtikel1());
            statement.setInt(2, bestelling.getAantalArtikel2());
            statement.setInt(3, bestelling.getAantalArtikel3());
            resultSet = statement.executeUpdate();
            
            
            
            //wijs door database gegenereerde id toe aan klant
            resultSet = statement.getGeneratedKeys();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                klant.setKlantID(resultSet.getInt(1));        
                
                
                
                
    }
    
    //overloaded setter method voor klant_id
    public void updateBestelling(int klant_id, String... columnsAdjust){
        
    }
    
    public void createBestellingRow(int klant_id, String... rowInserts){
        
    }
    
    public void deleteBestellingRow(int klant_id){
    
    }
            
}
