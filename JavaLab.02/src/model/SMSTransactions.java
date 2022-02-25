package model;

import utility.SingletonDBConnection;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMSTransactions implements ManageSMS {
    final private static Logger logger = Logger.getLogger(SMSTransactions.class.getName());
    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Connection connection = SingletonDBConnection.getConnection();

    //collection of all the retrieved SMS
    protected ArrayList<SMS> allSMS = new ArrayList<>();

    //collections for report generation
    protected ArrayList<SMS> failedTransactionList = new ArrayList<>();
    protected ArrayList<SMS> failedSentTransactionList = new ArrayList<>();
    protected ArrayList<SMS> failedReceivedTransactionList = new ArrayList<>();
    protected ArrayList<SMS> successfulTransactionList = new ArrayList<>();
    protected ArrayList<SMS> successfullySentTransactionList = new ArrayList<>();
    protected ArrayList<SMS> successfullyReceivedTransactionList = new ArrayList<>();

    protected Set<String> participants = new HashSet<>();

    protected int receivedSMSCount;
    protected int sentSMSCount;


    String sqlStatement = "";
    Statement statement = null;
    ResultSet resultSet = null;
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

        //retrieve all SMS
        sqlStatement = "SELECT * FROM sms";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
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
        if(allSMS.isEmpty()){
            logger.log(Level.INFO, "SMS database empty.");
        }
        return allSMS;
    }

    @Override //retrieve SMS between given startDate and endDate
    public ArrayList retrieveSMSStartEndDate(LocalDateTime startDate, LocalDateTime endDate) {

        //empty allSMS ArrayList
        allSMS.clear();

        //retrieve all sms between startDate and endDate
        sqlStatement = "SELECT * FROM sms WHERE timeStamp BETWEEN \"" + startDate + "\" AND \"" + endDate + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
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
        if (allSMS.isEmpty()){
            logger.log(Level.INFO, "No SMS between " + startDate + " and " + endDate + " .");
        }
        return allSMS;
    }

    @Override //retrieve SMS using promoCode
    public ArrayList retrieveSMSPromoCode(String promoCode) {

        //empty allSMS ArrayList
        allSMS.clear();

        //retrieve shortCode using promoCode
        PromoTransactions promoTransaction = new PromoTransactions();
        String shortCode = promoTransaction.retrieveShortCodeByPromoCode(promoCode);

        //retrieve all sms using retrieved shortCode
        sqlStatement = "SELECT * FROM sms WHERE shortCode = \"" + shortCode + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                retrievedSMS = new SMS(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        LocalDateTime.parse(resultSet.getString(6), format),
                        resultSet.getBoolean(7));

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
        if(allSMS.isEmpty()){
            logger.log(Level.INFO, "No SMS with " + promoCode + " promoCode.");
        }
        return allSMS;
    }

    @Override //retrieve sms using msisdn
    public ArrayList retrieveSMSMSISDN(String msisdn) {

        //empty allSMS ArrayList
        allSMS.clear();

        //retrieve SMS using msisdn
        sqlStatement = "SELECT * FROM sms WHERE msisdn = \"" + msisdn + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
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
        if (allSMS.isEmpty()){
            logger.log(Level.INFO, "No SMS with " + msisdn + " msisdn.");
        }
        return allSMS;
    }

    @Override
    public ArrayList retrieveSMSSeveralMSISDN(String[] msisd) {
        //empty allSMS ArrayList
        allSMS.clear();


        for(int count = 0; count < msisd.length; count++){
            //retrieve SMS using msisdn
            sqlStatement = "SELECT * FROM sms WHERE msisdn = \"" + msisd[count] + "\"";

            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sqlStatement);

                while (resultSet.next()) {
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
        }
        if (allSMS.isEmpty()){
            logger.log(Level.INFO, "No SMS with given msisdn.");
        }
        return allSMS;
    }

    @Override
    public ArrayList retrieveSMSBySystem() {
        //empty allSMS ArrayList
        allSMS.clear();

        //retrieve SMS using msisdn
        sqlStatement = "SELECT * FROM sms WHERE sender = \"System\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
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
        if (allSMS.isEmpty()){
            logger.log(Level.INFO, "No SMS sent by System");
        }
        return allSMS;
    }

    @Override
    public ArrayList retrieveSMSToSystem() {
        //empty allSMS ArrayList
        allSMS.clear();

        //retrieve SMS using msisdn
        sqlStatement = "SELECT * FROM sms WHERE recipient = \"System\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
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
        if (allSMS.isEmpty()){
            logger.log(Level.INFO, "No SMS sent to System");
        }
        return allSMS;
    }

    public void generateReport(String promoCode){
        retrieveSMSPromoCode(promoCode);

        //list of all failed transactions
        for(SMS sms : allSMS){
            if(!sms.isSuccessful()){
                failedTransactionList.add(sms);
            }
        }

        //list of all failed sent transactions
        for(SMS transaction : failedTransactionList){
            if(transaction.getSender().equals("System")){
                failedSentTransactionList.add(transaction);
            }
        }

        //list of all failed received transactions
        for(SMS transaction : failedTransactionList){
            if(transaction.getRecipient().equals("System")){
                failedReceivedTransactionList.add(transaction);
            }
        }

        //list of all successful transactions
        for(SMS sms : allSMS){
            if(sms.isSuccessful()){
                successfulTransactionList.add(sms);
            }
        }

        //list of all successfully sent transactions
        for(SMS transaction : successfulTransactionList){
            if(transaction.getSender().equals("System")){
                successfullySentTransactionList.add(transaction);
            }
        }

        //list of all successfully received transactions
        for(SMS transaction : successfulTransactionList){
            if(transaction.getRecipient().equals("System")){
                successfullyReceivedTransactionList.add(transaction);
            }
        }

        //list of all participants
        for(SMS sms : allSMS){
            if(!sms.getSender().equals("System")){
                participants.add(sms.getSender());
            }
        }

        receivedSMSCount = failedReceivedTransactionList.size() + successfullyReceivedTransactionList.size();
        sentSMSCount = failedSentTransactionList.size() + successfullySentTransactionList.size();

        generateAllTransactions(promoCode);

        generateFailedTransactions(promoCode);
        generateFailedSentTransactions(promoCode);
        generateFailedReceivedTransactions(promoCode);

        generateSuccessfulTransactions(promoCode);
        generateSuccessfullySentTransactions(promoCode);
        generateSuccessfullyReceivedTransactions(promoCode);

        generateParticipants(promoCode);


    }

    public void generateAllTransactions(String promoCode){
        try{
            String fileNme = promoCode + " " + LocalDateTime.now().format(format) +  ".txt";
            File allTransactions  = new File(fileNme);
            allTransactions.createNewFile();

            PrintWriter generateAllTransactions = new PrintWriter(new FileOutputStream(fileNme));

            generateAllTransactions.write("Promo Code: " + promoCode);
            generateAllTransactions.write("\r\n"); // line breaker
            generateAllTransactions.write("Total Received SMS: " + receivedSMSCount);
            generateAllTransactions.write("\r\n"); // line breaker
            generateAllTransactions.write("Total Sent SMS: " + sentSMSCount);
            generateAllTransactions.write("\r\n"); // line breaker

            generateAllTransactions.write("\r\nALL FAILED TRANSACTIONS\r\n");

            if(failedTransactionList.isEmpty()){
                generateAllTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : failedTransactionList) {
                    generateAllTransactions.write(sms.toString());
                    generateAllTransactions.write("\r\n"); // line breaker
                }
            }

            generateAllTransactions.write("\r\nALL FAILED SENT TRANSACTIONS\r\n");

            if(failedSentTransactionList.isEmpty()){
                generateAllTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : failedSentTransactionList) {
                    generateAllTransactions.write(sms.toString());
                    generateAllTransactions.write("\r\n"); // line breaker
                }
            }

            generateAllTransactions.write("\r\nALL FAILED RECEIVED TRANSACTIONS\r\n");

            if(failedReceivedTransactionList.isEmpty()){
                generateAllTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : failedReceivedTransactionList) {
                    generateAllTransactions.write(sms.toString());
                    generateAllTransactions.write("\r\n"); // line breaker
                }
            }

            generateAllTransactions.write("\r\nALL SUCCESSFUL TRANSACTIONS\r\n");

            if(successfulTransactionList.isEmpty()){
                generateAllTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : successfulTransactionList) {
                    generateAllTransactions.write(sms.toString());
                    generateAllTransactions.write("\r\n"); // line breaker
                }
            }

            generateAllTransactions.write("\r\nALL SUCCESSFULLY SENT TRANSACTIONS\r\n");

            if(successfullySentTransactionList.isEmpty()){
                generateAllTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : successfullySentTransactionList) {
                    generateAllTransactions.write(sms.toString());
                    generateAllTransactions.write("\r\n"); // line breaker
                }
            }

            generateAllTransactions.write("\r\nALL SUCCESSFULLY RECEIVED TRANSACTIONS\r\n");

            if(successfullyReceivedTransactionList.isEmpty()){
                generateAllTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : successfullyReceivedTransactionList) {
                    generateAllTransactions.write(sms.toString());
                    generateAllTransactions.write("\r\n"); // line breaker
                }
            }

            generateAllTransactions.write("\r\nNAMES OF ALL PARTICIPANTS\r\n");

            if(participants.isEmpty()){
                generateAllTransactions.write("[No Entry]\r\n");
            } else{
                for (String name : participants) {
                    generateAllTransactions.write(name);
                    generateAllTransactions.write("\r\n"); // line breaker
                }
            }

            generateAllTransactions.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateFailedTransactions(String promoCode){
        try{
            String fileName = promoCode + " Failed Transactions " + LocalDateTime.now().format(format) +  ".txt";
            File failedTransactions  = new File(fileName);
            failedTransactions.createNewFile();

            PrintWriter generateFailedTransactions = new PrintWriter(new FileOutputStream(fileName));

            generateFailedTransactions.write("Promo Code: " + promoCode);
            generateFailedTransactions.write("\r\n"); // line breaker
            generateFailedTransactions.write("Total Failed Transactions: " + failedTransactionList.size());
            generateFailedTransactions.write("\r\n"); // line breaker

            generateFailedTransactions.write("\r\nALL FAILED TRANSACTIONS\r\n");

            if(failedTransactionList.isEmpty()){
                generateFailedTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : failedTransactionList) {
                    generateFailedTransactions.write(sms.toString());
                    generateFailedTransactions.write("\r\n"); // line breaker
                }
            }
            generateFailedTransactions.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateFailedSentTransactions(String promoCode){
        try{
            String fileName = promoCode + " Failed Sent Transactions " + LocalDateTime.now().format(format) +  ".txt";
            File failedSentTransactions  = new File(fileName);
            failedSentTransactions.createNewFile();

            PrintWriter generateFailedSentTransactions = new PrintWriter(new FileOutputStream(fileName));

            generateFailedSentTransactions.write("Promo Code: " + promoCode);
            generateFailedSentTransactions.write("\r\n"); // line breaker
            generateFailedSentTransactions.write("Total Failed Sent Transactions: " + failedSentTransactionList.size());
            generateFailedSentTransactions.write("\r\n"); // line breaker

            generateFailedSentTransactions.write("\r\nALL FAILED SENT TRANSACTIONS\r\n");

            if(failedSentTransactionList.isEmpty()){
                generateFailedSentTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : failedSentTransactionList) {
                    generateFailedSentTransactions.write(sms.toString());
                    generateFailedSentTransactions.write("\r\n"); // line breaker
                }
            }
            generateFailedSentTransactions.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateFailedReceivedTransactions(String promoCode){
        try{
            String fileName = promoCode + " Failed Received Transactions " + LocalDateTime.now().format(format) +  ".txt";
            File failedReceivedTransactions  = new File(fileName);
            failedReceivedTransactions.createNewFile();

            PrintWriter generateFailedReceivedTransactions = new PrintWriter(new FileOutputStream(fileName));

            generateFailedReceivedTransactions.write("Promo Code: " + promoCode);
            generateFailedReceivedTransactions.write("\r\n"); // line breaker
            generateFailedReceivedTransactions.write("Total Failed Received Transactions: " + failedReceivedTransactionList.size());
            generateFailedReceivedTransactions.write("\r\n"); // line breaker

            generateFailedReceivedTransactions.write("\r\nALL FAILED RECEIVED TRANSACTIONS\r\n");

            if(failedReceivedTransactionList.isEmpty()){
                generateFailedReceivedTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : failedReceivedTransactionList) {
                    generateFailedReceivedTransactions.write(sms.toString());
                    generateFailedReceivedTransactions.write("\r\n"); // line breaker
                }
            }
            generateFailedReceivedTransactions.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateSuccessfulTransactions(String promoCode){
        try{
            String fileName = promoCode + " Successful Transactions " + LocalDateTime.now().format(format) +  ".txt";
            File successfulTransactions  = new File(fileName);
            successfulTransactions.createNewFile();

            PrintWriter generateSuccessfulTransactions = new PrintWriter(new FileOutputStream(fileName));

            generateSuccessfulTransactions.write("Promo Code: " + promoCode);
            generateSuccessfulTransactions.write("\r\n"); // line breaker
            generateSuccessfulTransactions.write("Total Successful Transactions: " + successfulTransactionList.size());
            generateSuccessfulTransactions.write("\r\n"); // line breaker

            generateSuccessfulTransactions.write("\r\nALL SUCCESSFUL TRANSACTIONS\r\n");

            if(successfulTransactionList.isEmpty()){
                generateSuccessfulTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : successfulTransactionList) {
                    generateSuccessfulTransactions.write(sms.toString());
                    generateSuccessfulTransactions.write("\r\n"); // line breaker
                }
            }
            generateSuccessfulTransactions.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateSuccessfullySentTransactions(String promoCode){
        try{
            String fileName = promoCode + " Successfully Sent Transactions " + LocalDateTime.now().format(format) +  ".txt";
            File successfullySentTransactions  = new File(fileName);
            successfullySentTransactions.createNewFile();

            PrintWriter generateSuccessfullySentTransactions = new PrintWriter(new FileOutputStream(fileName));

            generateSuccessfullySentTransactions.write("Promo Code: " + promoCode);
            generateSuccessfullySentTransactions.write("\r\n"); // line breaker
            generateSuccessfullySentTransactions.write("Total Successfully Sent Transactions: " + successfullySentTransactionList.size());
            generateSuccessfullySentTransactions.write("\r\n"); // line breaker

            generateSuccessfullySentTransactions.write("\r\nALL SUCCESSFULLY SENT TRANSACTIONS\r\n");

            if(successfullySentTransactionList.isEmpty()){
                generateSuccessfullySentTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : successfullySentTransactionList) {
                    generateSuccessfullySentTransactions.write(sms.toString());
                    generateSuccessfullySentTransactions.write("\r\n"); // line breaker
                }
            }
            generateSuccessfullySentTransactions.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateSuccessfullyReceivedTransactions(String promoCode){
        try{
            String fileName = promoCode + " Successfully Received Transactions " + LocalDateTime.now().format(format) +  ".txt";
            File successfullyReceivedTransactions  = new File(fileName);
            successfullyReceivedTransactions.createNewFile();

            PrintWriter generateSuccessfullyReceivedTransactions = new PrintWriter(new FileOutputStream(fileName));

            generateSuccessfullyReceivedTransactions.write("Promo Code: " + promoCode);
            generateSuccessfullyReceivedTransactions.write("\r\n"); // line breaker
            generateSuccessfullyReceivedTransactions.write("Total Successfully Received Transactions: " + successfullyReceivedTransactionList.size());
            generateSuccessfullyReceivedTransactions.write("\r\n"); // line breaker

            generateSuccessfullyReceivedTransactions.write("\r\nALL SUCCESSFULLY RECEIVED TRANSACTIONS\r\n");

            if(successfullyReceivedTransactionList.isEmpty()){
                generateSuccessfullyReceivedTransactions.write("[No Entry]\r\n");
            } else{
                for (SMS sms : successfullyReceivedTransactionList) {
                    generateSuccessfullyReceivedTransactions.write(sms.toString());
                    generateSuccessfullyReceivedTransactions.write("\r\n"); // line breaker
                }
            }
            generateSuccessfullyReceivedTransactions.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateParticipants(String promoCode){
        try{
            String fileName = promoCode + " Participants " + LocalDateTime.now().format(format) +  ".txt";
            File allParticipants  = new File(fileName);
            allParticipants.createNewFile();

            PrintWriter generateAllParticipants = new PrintWriter(new FileOutputStream(fileName));

            generateAllParticipants.write("Promo Code: " + promoCode);
            generateAllParticipants.write("\r\n"); // line breaker
            generateAllParticipants.write("Total Participants: " + participants.size());
            generateAllParticipants.write("\r\n"); // line breaker

            generateAllParticipants.write("\r\nALL PARTICIPANTS\r\n");

            if(participants.isEmpty()){
                generateAllParticipants.write("[No Entry]\r\n");
            } else{
                for (String name : participants) {
                    generateAllParticipants.write(name);
                    generateAllParticipants.write("\r\n"); // line breaker
                }
            }
            generateAllParticipants.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}


