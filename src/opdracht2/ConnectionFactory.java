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
        
    private static final String MYSQL_URL =   "jdbc:mysql://localhost/workshopdeel1?&useSSL=false";
    private static final String FIREBIRD_URL =  "jdbc:firebirdsql://localhost:3050/workshopdeel1" ; 
    private static String activeURL = MYSQL_URL;
    
    private static final String USER = "hallo";
    private static final String PASSWORD = "doei";
    
    private static final String DRIVER_CLASS_MYSQL = "com.mysql.jdbc.Driver";
    private static final String DRIVER_CLASS_FIREBIRD = "org.firebirdsql.jdbc.FBDriver";
    private static String activeDriverClass = DRIVER_CLASS_MYSQL;
    
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class); 
    private static boolean usingHikari = true;
    
    //private constructor
    private ConnectionFactory() {
        useHikari();
    }
     
    public static Connection getMySQLConnection() {
        logger.trace("wordt verbinding met " +  activeURL.substring(5,13) + " databse gevraagd ");
        
        return INSTANCE.createConnection();
    }
           
    private Connection createConnection() {
        Connection connection = null;
        
        try {
            if (usingHikari) connection = hds.getConnection();
            else connection = cpds.getConnection();
            logger.debug("is verbinding met " +  activeURL.substring(5,13) + " database gelegd ");
        } 
        catch (SQLException e) {
            logger.error("wordt verbinding geen verbinding met " +  activeURL.substring(5,13) + " gemaakt " + e);
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
            cpds.setDriverClass( activeDriverClass ); //loads the jdbc driver 
            cpds.setJdbcUrl( activeURL ); 
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
            hds.setJdbcUrl( activeURL ); 
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
    
    public static void useFirebird() {
       logger.info("now using firebird");   
       activeURL = FIREBIRD_URL;
       activeDriverClass = DRIVER_CLASS_FIREBIRD;
       if (usingHikari) useHikari();
       else useC3PO();
    }
    
    public static void useMySQL() {
       logger.info("now using MySQL");     
       activeURL = MYSQL_URL;
       activeDriverClass = DRIVER_CLASS_MYSQL;
       if (usingHikari) useHikari();
       else useC3PO();
    }
   
}