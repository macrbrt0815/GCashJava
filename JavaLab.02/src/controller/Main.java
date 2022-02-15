package controller;

import model.SMS;
import utility.DBConnection;
import utility.SingletonDBConnection;

import java.sql.Connection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Connection connection = SingletonDBConnection.getConnection();
        dataPopulation(connection);

        SingletonDBConnection.disconnect();
    }

    public void smsChecker(Map<String, String> sms){
        // not final implementation
        logger.log(Level.INFO, "Mobile Number: " + sms.get("mobileNumber"));
        logger.log(Level.INFO, "Message: " + sms.get("message"));
        logger.log(Level.INFO, "Short Code: " + sms.get("shortCode"));
    }

    public static void dataPopulation(Connection connection){
        SMS sms;

        //Insert promo


        //insert 30 SMS for the "PISO PIZZA" promo
        //initial data, to be updated
        for(int index = 0; index < 30; index++){
           sms = new SMS("msisdn " + (index+1),
                   "recipient "  + (index+1),
                   "sender "  + (index+1),
                   "1Pizza",
                   "timeStamp "  + (index+1));

            if (sms.addSMS(connection)) {
                System.out.println("Employee successfully inserted to DB");

            } else {
                System.out.println("Something went wrong. Please Try Again.");
            }
        }


    }


}
