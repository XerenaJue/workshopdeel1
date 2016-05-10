package opdracht2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 

/* from theopentutorials.com
 
 */


/**
 *
 * @author jeroen
 */


 
public class ConnectionFactory {
    //static reference to itself
    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mysql://localhost/workshopdeel1";
    public static final String USER = "hallo";
    public static final String PASSWORD = "doei";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
     
    //private constructor
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }   
     
    public static Connection getMySQLConnection() {
        return instance.createConnection();
    }
}