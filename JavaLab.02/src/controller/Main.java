package controller;

import model.*;
import utility.DBConnection;
import utility.Helper;
import utility.SingletonDBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    final private static Logger logger = Logger.getLogger(SMSTransactions.class.getName());

    private static Connection connection = SingletonDBConnection.getConnection();
    private static SMSTransactions smsTransaction = new SMSTransactions();
    private static PromoTransactions promoTransaction = new PromoTransactions();
    private static ValidateSMS validateSMS = new ValidateSMS();

    private static SMS sms;
    private static  Promo promo;

    private static String transactionID= "";



    public static void main(String[] args) throws IOException {
        //dataPopulatePromo(connection);
        //dataPopulateSMS(connection);

        userInput();

        SingletonDBConnection.disconnect();


    }

    public static void userInput() throws IOException {
        String choice = "";

        String mobileNumber = "";
        String firstName = "";
        String lastName = "";

        String promoCode = "";
        String shortCode = "";

        String confirmation = "";

        do {
            mobileNumber = Helper.getStringInput("Enter mobile number: ");
            firstName = Helper.getStringInput("Enter first name: ");
            lastName = Helper.getStringInput("Enter last name: ");

            promoCode = Helper.getStringInput("Enter promo code: ");
            //validate if promoCode exists
            if (validateSMS.validatePromoCode(connection, promoCode)){
                shortCode = Helper.getStringInput("Enter short code: ");
                //validate if shortCode exists
                if (validateSMS.validateShortCode(connection, shortCode)){
                    //validate if correct promoCode and shortCode
                    if (validateSMS.validatePromoShortCode(connection, promoCode, shortCode)){
                        do{
                            confirmation = Helper.getStringInput(("Send \"REGISTER\" to confirm"));
                        }while (!confirmation.equalsIgnoreCase("REGISTER"));
                        //validate sms if successful/ failed
                        transactionID = sms.generateTransactionID(connection, shortCode);
                        sms = new SMS (transactionID,
                                mobileNumber,
                                "System",
                                (firstName + " " + lastName),
                                shortCode,
                                LocalDateTime.now());
                        if (validateSMS.SMSChecker(connection, sms)){
                            //display success
                            System.out.println("success");
                        } else {
                            //display fail
                            System.out.println("fail");
                        }
                    } else {
                        logger.log(Level.INFO, "Incorrect promoCode/ shortCode");
                    }
                } else {
                    logger.log(Level.INFO, shortCode + " shortCode doesn't exist");
                }
            } else {
                logger.log(Level.INFO, promoCode + " promoCode doesn't exist");
            }

            choice = Helper.getStringInput("Do you want to try again? [YES/NO]: ");
        } while (choice.equalsIgnoreCase("YES"));


    }
/*
    public void smsChecker(Map<String, String> sms){
        // not final implementation
        logger.log(Level.INFO, "Mobile Number: " + sms.get("mobileNumber"));
        logger.log(Level.INFO, "Message: " + sms.get("message"));
        logger.log(Level.INFO, "Short Code: " + sms.get("shortCode"));
    }
*/

    public static void dataPopulatePromo(Connection connection) {
        //Insert promo
        // Insert Piso Pizza promo
        promo = new Promo("PISO PIZZA",
                "Pizza for 1 peso only. " +
                        "Valid from February 1 - March 1, 2022.",
                "1Pizza",
                LocalDateTime.of(2022, Month.FEBRUARY, 1, 0, 0, 0),
                LocalDateTime.of(2022, Month.MARCH, 1, 0, 0, 0));

        promoTransaction.insertPromo(connection, promo);

        // Insert Free Shipping promo
        promo = new Promo("FREE SHIPPING",
                "Free shipping fee nationwide minimum spent of Php 1000.00. " +
                        "Valid from March 3 - March 4, 2022. ",
                "FreeShipMin1k",
                LocalDateTime.of(2022, Month.MARCH, 3, 0, 0, 0),
                LocalDateTime.of(2022, Month.MARCH, 3, 23, 59, 59));

        promoTransaction.insertPromo(connection, promo);

        // Insert 150 OFF promo
        promo = new Promo("PHP150 OFF, MIN 700",
                "Less Php 150, minimum spent of 700. " +
                        "Valid from February 12 - February 28, 2022.",
                "150Off",
                LocalDateTime.of(2022, Month.FEBRUARY, 1, 0, 0, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 28, 23, 59, 59));

        promoTransaction.insertPromo(connection, promo);

    }

    public static void dataPopulateSMS(Connection connection){
        //insert 30 SMS for the "PISO PIZZA" promo
        //initial data, to be updated
        sms = new SMS("test",
                "msisdn " ,
                "recipient ",
                "sender " ,
                "1Pizza",
                LocalDateTime.now());

        //smsTransaction.insertSMS(connection,sms);
        for(int index = 0; index < 30; index++){
            transactionID = sms.generateTransactionID(connection, "1Pizza");
           sms = new SMS(transactionID,
                   "msisdn " + (index+1),
                   "recipient "  + (index+1),
                   "sender "  + (index+1),
                   "1Pizza",
                   LocalDateTime.now());

            smsTransaction.insertSMS(connection,sms);
        }

        //insert 15 SMS for the "FREE SHIPPING" promo
        //initial data, to be updated
        for(int index = 0; index < 15; index++){
            transactionID = sms.generateTransactionID(connection, "FreeShipMin1k");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "recipient "  + (index+1),
                    "sender "  + (index+1),
                    "FreeShipMin1k",
                    LocalDateTime.now());

            smsTransaction.insertSMS(connection,sms);
        }

        //insert 15 SMS for the "PHP150 OFF, MIN 700" promo
        //initial data, to be updated
        for(int index = 0; index < 15; index++){
            transactionID = sms.generateTransactionID(connection, "150Off");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "recipient "  + (index+1),
                    "sender "  + (index+1),
                    "150Off",
                    LocalDateTime.now());

            smsTransaction.insertSMS(connection,sms);
        }
    }
}
