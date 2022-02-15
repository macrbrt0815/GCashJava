package utility;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingletonDBConnection {
    final private static Logger logger = Logger.getLogger(SingletonDBConnection.class.getName());
    private static Connection connection = null;

    private SingletonDBConnection() {
    }

    private static Connection getConnectionInstance() {

        if(connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_database?useTimezone=true&serverTimezone=UTC", "root", "951236541");
                logger.info("Connected");
            } catch(SQLException sqle) {
                sqle.printStackTrace();
                logger.info("Not Connected");
            } catch (Exception e){
                e.printStackTrace();
                logger.info("Not Connected");
            }
        }
        return connection;
    }

    public static Connection getConnection() {
        return (connection == null)
                ? getConnectionInstance()
                : connection;
    }

    public static void disconnect(){
        try {
            if (connection != null){
                connection.close();
                logger.info("Disconnected");
            }
        } catch (Exception e){
            logger.log(Level.SEVERE, "Not Connected", e);
        }
    }
}
