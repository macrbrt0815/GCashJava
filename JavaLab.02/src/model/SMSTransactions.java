package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMSTransactions implements ManageSMS {
    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    final private static Logger logger = Logger.getLogger(SMSTransactions.class.getName());

    protected ArrayList<SMS> allSMS = new ArrayList<>();

    String sqlStatement = "";
    Statement statement = null;
    ResultSet resultSet = null;
    boolean isSMSEmpty;
    SMS retrievedSMS;

    @Override //insert sms into database
    public void insertSMS(Connection connection, SMS sms) {
         sqlStatement = "INSERT INTO "
                + "sms(transactionID, msisdn, recipient, sender, shortCode, timeStamp, isSucessful)"
                + "values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, sms.getTransactionID());
            preparedStatement.setString(2, sms.getMsisdn());
            preparedStatement.setString(3, sms.getRecipient());
            preparedStatement.setString(4, sms.getSender());
            preparedStatement.setString(5, sms.getShortCode());
            preparedStatement.setString(6, sms.getTimeStamp().toString());
            preparedStatement.setBoolean(7, true);

            preparedStatement.executeUpdate();
            logger.log(Level.INFO, "SMS added");

        } catch (SQLException sqle){
            logger.log(Level.SEVERE, "SQLException", sqle);
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
    }

    @Override //retrieve ALL SMS from database
    public ArrayList retrieveSMS(Connection connection) {
        isSMSEmpty = true;

        //empty allSMS ArrayList
        allSMS.clear();

        //retrieve all SMS
        sqlStatement = "SELECT * FROM sms";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                isSMSEmpty = false;
                retrievedSMS = new SMS(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        LocalDateTime.parse(resultSet.getString(6), format));

                allSMS.add(retrievedSMS);
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
        if (isSMSEmpty){
            logger.log(Level.INFO, "SMS database empty.");
            return null;
        } else {
            return allSMS;
        }
    }

    @Override //retrieve SMS between given startDate and endDate
    public ArrayList retrieveSMSStartEndDate(Connection connection, LocalDateTime startDate, LocalDateTime endDate) {
        isSMSEmpty = true;

        //empty allSMS ArrayList
        allSMS.clear();

        //retrieve all sms between startDate and endDate
        sqlStatement = "SELECT * FROM sms WHERE timeStamp BETWEEN \"" + startDate + "\" AND \"" + endDate + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                isSMSEmpty = false;
                retrievedSMS = new SMS(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        LocalDateTime.parse(resultSet.getString(6), format));

                allSMS.add(retrievedSMS);
            }
        } catch (SQLException sqle){
            logger.log(Level.SEVERE, "SQLException", sqle);
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
        if (isSMSEmpty){
            logger.log(Level.INFO, "No SMS between " + startDate + " and " + endDate + " .");
            return null;
        } else {
            return allSMS;
        }
    }

    @Override //retrieve SMS using promoCode
    public ArrayList retrieveSMSPromoCode(Connection connection, String promoCode) {
        isSMSEmpty = true;

        //empty allSMS ArrayList
        allSMS.clear();

        //retrieve shortCode using promoCode
        PromoTransactions promoTransaction = new PromoTransactions();
        String shortCode = promoTransaction.retrieveShortCode(connection, promoCode);

        //retrieve all sms using retrieved shortCode
        sqlStatement = "SELECT * FROM sms WHERE shortCode = \"" + shortCode + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                isSMSEmpty = false;
                retrievedSMS = new SMS(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        LocalDateTime.parse(resultSet.getString(6), format));

                allSMS.add(retrievedSMS);
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
        if (isSMSEmpty){
            logger.log(Level.INFO, "No SMS with " + promoCode + " promoCode.");
            return null;
        } else {
            return allSMS;
        }
    }

    @Override //retrieve sms using msisdn
    public ArrayList retrieveSMSMSISDN(Connection connection, String msisdn) {
        isSMSEmpty = true;

        //empty allSMS ArrayList
        allSMS.clear();

        //retrieve SMS using msisdn
        String sqlStatement = "SELECT * FROM sms WHERE msisdn = \"" + msisdn + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                isSMSEmpty = false;
                retrievedSMS = new SMS(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        LocalDateTime.parse(resultSet.getString(6), format));

                allSMS.add(retrievedSMS);
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
        if (isSMSEmpty){
            logger.log(Level.INFO, "No SMS with " + msisdn + " msisdn.");
            return null;
        } else {
            return allSMS;
        }
    }

    @Override
    public ArrayList retrieveSMSBySystem() {
        return null;
    }

    @Override
    public ArrayList retrieveSMSToSystem() {
        return null;
    }
}


