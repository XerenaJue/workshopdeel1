package opdracht2;

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
        ResultSet resultSet;


//no args constructor
    
    public BestellingDAO() throws SQLException, ClassNotFoundException{
            Class.forName("com.mysql.jdbc.Driver");
    }
    
    //overloaded getter method voor bestelling_id

    //overloaded getter method voor klant_id
    public Bestelling readBestelling(int klant_id) throws SQLException, ClassNotFoundException{
        System.out.println("geen resultset");
        Bestelling bestelling = new Bestelling();
        String SQLStatement = "SELECT * FROM bestelling WHERE bestelling_id = '3'";
        try{
        createCS(SQLStatement);
        //statement.setInt(1, klant_id);
        resultSet = statement.executeQuery();
        System.out.println(connection.toString());
        while(resultSet.next()){
            bestelling.setBestellingID(resultSet.getInt("bestelling_id"));
            System.out.println("resultsetgetint: " + resultSet.getInt("bestelling_id"));
            bestelling.setKlant(resultSet.getInt("klant_id"));
            bestelling.setAantalArtikel1(resultSet.getInt("artikel_aantal"));
            System.out.println("artikel_aantal: " + resultSet.getInt("artikel_aantal"));
            bestelling.setAantalArtikel2(resultSet.getInt("artikel1_aantal"));
            bestelling.setAantalArtikel3(resultSet.getInt("artikel2_aantal"));
            System.out.println(bestelling.toString());
        }
        }
        catch (SQLException e){}
        finally{closeCS();}
        
        return bestelling; 
    }
    
    public void createCS(String SQLStatement) throws SQLException, ClassNotFoundException{
        connection = ConnectionFactory.getMySQLConnection();
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
    
    public void createBestelling(Klant klant)throws SQLException, ClassNotFoundException{
           String SQLStatement = "Insert into Bestelling (artikel_aantal, artikel2_aantal, artikel3_aantal, klant_id) values (?, ?, ?, ?)" ;
        try {
            Bestelling bestelling = new Bestelling();
            connection = ConnectionFactory.getMySQLConnection();
            statement = connection.prepareStatement(SQLStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, bestelling.getArtikel_aantal());
            statement.setInt(2, bestelling.getAantalArtikel2());
            statement.setInt(3, bestelling.getAantalArtikel3());
            statement.setInt(4, klant.getKlantID());
            statement.executeUpdate();
            
            
            
            //wijs door database gegenereerde id toe aan bestelling
            resultSet = statement.getGeneratedKeys();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                bestelling.setBestellingID(resultSet.getInt(1));
            }
            
            

        }
            catch(SQLException ex){}
            finally{closeCS();}                
                
                
                
    }
    
    //overloaded setter method voor klant_id
    public void updateBestelling(Bestelling bestelling) throws SQLException, ClassNotFoundException{
           String SQLStatement = 
                   "update Bestelling set artikel_aantal = ?, artikel2_aantal = ?, artikel3_aantal = ? where bestelling_id = ?"; 
        try {
            createCS(SQLStatement);
            statement.setInt(1,bestelling.getArtikel_aantal());
            statement.setInt(2,bestelling.getAantalArtikel2());
            statement.setInt(3,bestelling.getAantalArtikel3());
            statement.setInt(4,bestelling.getBestelling_id());
            statement.executeUpdate();
        }
            catch(SQLException ex){}
            finally{closeCS();}                
                
            
        
    }
    
    public void createBestellingRow(int klant_id, String... rowInserts){
        
    }
    
    public void deleteBestelling(Bestelling bestelling) throws SQLException, ClassNotFoundException{
    
               String SQLStatement = 
                   "delete from Bestelling where bestelling_id = ?"; 
        try {
            createCS(SQLStatement);
            statement.setInt(1,bestelling.getBestelling_id());
            statement.executeUpdate();
        }
            catch(SQLException ex){}
            finally{closeCS();}                
                
            
        
    }
    public static void main(String args[]) throws SQLException, ClassNotFoundException{
        BestellingDAO test = new BestellingDAO();
        Bestelling bestelling = test.readBestelling(3);
        System.out.println("BestellingsID: " + bestelling.getBestelling_id());
        System.out.println("Artikel aantal: " + bestelling.getArtikel_aantal());
                System.out.println("tostring"+ bestelling.toString());
                    System.out.println("blablablabla");
    }
            
}
