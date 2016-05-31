package opdracht2;

import com.mchange.v2.c3p0.*;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author jeroen
 */
public class ConnectionFactory {
    //static reference to itself
    private final static ConnectionFactory INSTANCE = new ConnectionFactory();
    private static ComboPooledDataSource cpds;  
    private static HikariDataSource hds ;
        
    public static final String URL = "jdbc:mysql://localhost/workshopdeel1?&useSSL=false";
    public static final String USER = "hallo";
    public static final String PASSWORD = "doei";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class); 
    private static boolean usingHikari = true;
    //private constructor
    private ConnectionFactory() {
        useHikari();
    }
     
    public static Connection getMySQLConnection() {
        logger.trace("wordt verbinding met MySql databse gevraagd ");
        return INSTANCE.createConnection();
    }
    
    private Connection createConnection() {
        Connection connection = null;
        try {
            if (usingHikari) connection = hds.getConnection();
            else connection = cpds.getConnection();
            logger.debug("is verbinding met MySql databse gelegd ");
        } 
        catch (SQLException e) {
            logger.error("wordt verbinding geen verbinding met MySQl gemaakt " + e);
            e.printStackTrace();
        }
        return connection;
    }   
    
    public static void changeConnectionPool() {
        if (usingHikari) useC3PO();
        else useHikari();
    }
    
    private static void useC3PO() {
        closeConnectionPool();
        usingHikari = false;
        try {
            cpds = new ComboPooledDataSource(); 
            cpds.setDriverClass( DRIVER_CLASS ); //loads the jdbc driver 
            cpds.setJdbcUrl( URL ); 
            cpds.setUser(USER);
            cpds.setPassword(PASSWORD); 

            cpds.setMinPoolSize(5);
            cpds.setMaxPoolSize(10); 
        }
        catch (Exception e) {
            logger.error("probleem setting up C3PO " + e);
            e.printStackTrace();
        }
    } 
    
    private static void useHikari() {
        closeConnectionPool();
        usingHikari = true;
        try {
            hds = new HikariDataSource();
            hds.setJdbcUrl( URL ); 
            hds.setUsername(USER);
            hds.setPassword(PASSWORD); 
        }
        catch (Exception e) {
            logger.error("probleem setting up Hikari " + e);
            e.printStackTrace();
        }
    } 
    public static void closeConnectionPool() {
        if (cpds != null) cpds.close();
        if (hds != null) hds.close();
    }
   
}