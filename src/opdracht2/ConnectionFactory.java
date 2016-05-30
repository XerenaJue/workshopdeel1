package opdracht2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

/* from theopentutorials.com
 
 */


/**
 *
 * @author jeroen
 */


 
public class ConnectionFactory {
    //static reference to itself
    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mysql://localhost/workshopdeel1?&useSSL=false";
    public static final String USER = "hallo";
    public static final String PASSWORD = "doei";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class); 
     
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
            logger.debug("is verbinding met MySql databse gelegd ");
        } catch (SQLException e) {
            logger.error("wordt verbinding geen verbinding met MySQl gemaakt " + e);
            e.printStackTrace();
        }
        return connection;
    }   
     
    public static Connection getMySQLConnection() {
        logger.trace("wordt verbinding met MySql databse gevraagd ");
        return instance.createConnection();
    }
}