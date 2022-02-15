package utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    // add external lib in IntelliJ
    // File > Project Structure > Libraries > add (+) > Java > select library
    final private static Logger logger = Logger.getLogger(DBConnection.class.getName());
    private static Connection connection = null;

    public static void main(String[] args) {
        //DBConnection.connect();

        //DatabaseConnect.insertData("Sample");

        //ArrayList<String> data = DBConnection.retrieveData();

        //DBConnection.disconnect();
    }

    public static void connect() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_db?useTimezone=true&serverTimezone=UTC", "root", "951236541");
            //connection =  DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/Dq41pHZbDx?useTimezone=true&serverTimezone=UTC", "Dq41pHZbDx", "NuLh0BhDzw");
            logger.info("Connected");
        }catch (Exception e){
            logger.log(Level.SEVERE, "Not Connected", e);
        }
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

    public static void insertData(String data){
        String insertQuery = "Insert into datatable (data) values ('" + data + "')";
        Statement statement = null;
        int result = 0;

        try {
            statement = connection.createStatement();
            result = statement.executeUpdate(insertQuery);
        } catch (SQLException e){
            logger.log(Level.SEVERE, "SQLException ", e);
        } finally {
            try {
                if (statement != null){
                    statement.close();
                }
            } catch (Exception e){
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }

        logger.log(Level.INFO, "Inserted: {0}", result);
    }

    public static ArrayList<String> retrieveData(){
        String selectQuery = "Select * from datatable";
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> result = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectQuery);

            while(resultSet.next()){
                logger.log(Level.INFO, resultSet.getString(1) + " : " + resultSet.getString(2));
                result.add(resultSet.getInt(1) + " : " + resultSet.getString(2));
            }
        } catch (SQLException e){
            logger.log(Level.SEVERE, "SQLException", e);
        } finally {
            try{
                if (statement != null){
                    statement.close();
                } if (resultSet != null){
                    resultSet.close();
                }
            } catch (Exception e){
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }
        logger.log(Level.INFO, " retrieved: {0}", result);
        return result;
    }

}
