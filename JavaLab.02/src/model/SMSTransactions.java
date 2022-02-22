package model;

import utility.SingletonDBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMSTransactions implements ManageSMS {
    final private static Logger logger = Logger.getLogger(SMSTransactions.class.getName());
    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Connection connection = SingletonDBConnection.getConnection();

    //collection of all the retrieved SMS
    protected ArrayList<SMS> allSMS = new ArrayList<>();

    String sqlStatement = "";
    Statement statement = null;
    ResultSet resultSet = null;
    boolean isSMSEmpty;
    SMS retrievedSMS;

    @Override //insert sms into database
    public void insertSMS(SMS sms, boolean isSuccessful) {
         sqlStatement = "INSERT INTO "
                + "sms(transactionID, msisdn, recipient, sender, shortCode, timeStamp, isSuccessful)"
                + "values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, sms.getTransactionID());
            preparedStatement.setString(2, sms.getMsisdn());
            preparedStatement.setString(3, sms.getRecipient());
            preparedStatement.setString(4, sms.getSender());
            preparedStatement.setString(5, sms.getShortCode());
            preparedStatement.setString(6, sms.getTimeStamp().toString());
            preparedStatement.setBoolean(7, isSuccessful);

            preparedStatement.executeUpdate();
            logger.log(Level.INFO, "SMS [" + sms.getTransactionID() + "] added.");

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
    public ArrayList retrieveSMS() {

        //empty allSMS ArrayList
        allSMS.clear();
        isSMSEmpty = true;

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
            logger.log(Level.INFO, "SMS database empty.");
            return null;
        } else {
            return allSMS;
        }
    }

    @Override //retrieve SMS between given startDate and endDate
    public ArrayList retrieveSMSStartEndDate(LocalDateTime startDate, LocalDateTime endDate) {

        //empty allSMS ArrayList
        allSMS.clear();
        isSMSEmpty = true;

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
    public ArrayList retrieveSMSPromoCode(String promoCode) {

        //empty allSMS ArrayList
        allSMS.clear();
        isSMSEmpty = true;

        //retrieve shortCode using promoCode
        PromoTransactions promoTransaction = new PromoTransactions();
        String shortCode = promoTransaction.retrieveShortCodeByPromoCode(promoCode);

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
            logger.log(Level.INFO, "No SMS with " + promoCode + " promoCode.");
            return null;
        } else {
            return allSMS;
        }
    }

    @Override //retrieve sms using msisdn
    public ArrayList retrieveSMSMSISDN(String msisdn) {

        //empty allSMS ArrayList
        allSMS.clear();
        isSMSEmpty = true;

        //retrieve SMS using msisdn
        sqlStatement = "SELECT * FROM sms WHERE msisdn = \"" + msisdn + "\"";

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
            logger.log(Level.INFO, "No SMS with " + msisdn + " msisdn.");
            return null;
        } else {
            return allSMS;
        }
    }

    @Override
    public ArrayList retrieveSMSBySystem() {
        //empty allSMS ArrayList
        allSMS.clear();
        isSMSEmpty = true;

        //retrieve SMS using msisdn
        sqlStatement = "SELECT * FROM sms WHERE sender = \"System\"";

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
            logger.log(Level.INFO, "No SMS sent by System");
            return null;
        } else {
            return allSMS;
        }
    }

    @Override
    public ArrayList retrieveSMSToSystem() {
        //empty allSMS ArrayList
        allSMS.clear();
        isSMSEmpty = true;

        //retrieve SMS using msisdn
        sqlStatement = "SELECT * FROM sms WHERE recipient = \"System\"";

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
            logger.log(Level.INFO, "No SMS sent to System");
            return null;
        } else {
            return allSMS;
        }
    }
}


