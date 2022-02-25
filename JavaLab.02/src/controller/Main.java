package controller;

import View.Display;
import model.*;
import utility.Helper;
import utility.SingletonDBConnection;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    final private static Logger logger = Logger.getLogger(Main.class.getName());

    static SMSTransactions smsTransaction = new SMSTransactions();
    static PromoTransactions promoTransaction = new PromoTransactions();
    static ValidateSMS validateSMS = new ValidateSMS();
    static Display display = new Display();

    static SMS sms = new SMS();
    static Promo promo = new Promo();

    static String transactionID= "";

    public static void main(String[] args) throws IOException {
        //dataPopulatePromo();
        //dataPopulateSMS();

        //userInput();

        //report generation for promo PISO PIZZA
        smsTransaction.generateReport("PISO PIZZA");
/*
        //retrieve all sms
        display.displaySMS(smsTransaction.retrieveSMS());

        //retrieve sms by start and end dates
        LocalDateTime startDate = null; //specify start date
        LocalDateTime endDate = null; //specify end date
        display.displaySMS(smsTransaction.retrieveSMSStartEndDate(startDate, endDate));

        //retrieve sms by promo code
        String promoCode = "PISO PIZZA";
        display.displaySMS(smsTransaction.retrieveSMSPromoCode(promoCode));

        //retrieve sms by msisdn
        String msisdn = "msisdn 1";
        display.displaySMS(smsTransaction.retrieveSMSMSISDN(msisdn));

        //retrieve sms by several msisdn
        String msisdn[] = {"09688515895", "msisdn 2", "msisdn 6"};
        display.displaySMS(smsTransaction.retrieveSMSSeveralMSISDN(msisdn));


        //retrieve sms sent to system
        display.displaySMS(smsTransaction.retrieveSMSToSystem());

        //retrieve sms sent by system
        display.displaySMS(smsTransaction.retrieveSMSBySystem());

        //retrieve all promos
        display.displayPromo(promoTransaction.retrievePromo());
*/
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


        mobileNumber = Helper.getStringInput("Enter mobile number: ");
        firstName = Helper.getStringInput("Enter first name: ");
        lastName = Helper.getStringInput("Enter last name: ");

        do {
            promoCode = Helper.getStringInput("Enter promo code: ");
            //validate if promoCode exists
            if (validateSMS.validatePromoCode(promoCode)){
                shortCode = Helper.getStringInput("Enter short code: ");
                //validate if shortCode exists
                if (validateSMS.validateShortCode(shortCode)){
                    //validate if correct promoCode and shortCode
                    if (validateSMS.validatePromoShortCode(promoCode, shortCode)){
                        do{
                            confirmation = Helper.getStringInput(("Send \"REGISTER\" to confirm"));
                        }while (!confirmation.equalsIgnoreCase("REGISTER"));
                        //validate sms if successful/ failed
                        transactionID = sms.generateTransactionID(promoCode);
                        sms = new SMS (transactionID,
                                mobileNumber,
                                "System",
                                (firstName + " " + lastName),
                                shortCode,
                                LocalDateTime.now());
                        validateSMS.SMSChecker(sms);
                    } else {
                        logger.log(Level.INFO, "Promo code and Short code didn't match");
                    }
                } else {
                    logger.log(Level.INFO, shortCode + " shortCode doesn't exist");
                }
            } else {
                logger.log(Level.INFO, promoCode + " promoCode doesn't exist");
            }

            choice = Helper.getStringInput("Do you want to try again? [YES/NO]: ");
        } while (choice.equalsIgnoreCase("YES"));
        logger.log(Level.INFO, "Program Terminating ...");
    }

    public static void dataPopulatePromo() {
        //Insert promo
        // Insert Piso Pizza promo
        promo = new Promo("PISO PIZZA",
                "Pizza for 1 peso only. " +
                        "Valid from February 1 - March 1, 2022.",
                "1Pizza",
                LocalDateTime.of(2022, Month.FEBRUARY, 1, 0, 0, 0),
                LocalDateTime.of(2022, Month.MARCH, 1, 0, 0, 0));

        promoTransaction.insertPromo(promo);

        // Insert Free Shipping promo
        promo = new Promo("FREE SHIPPING",
                "Free shipping fee nationwide minimum spent of Php 1000.00. " +
                        "Valid from March 3 - March 4, 2022. ",
                "FreeShipMin1k",
                LocalDateTime.of(2022, Month.MARCH, 3, 0, 0, 0),
                LocalDateTime.of(2022, Month.MARCH, 3, 23, 59, 59));

        promoTransaction.insertPromo(promo);

        // Insert 150 OFF promo
        promo = new Promo("PHP150 OFF, MIN 700",
                "Less Php 150, minimum spent of 700. " +
                        "Valid from February 12 - February 28, 2022.",
                "150Off",
                LocalDateTime.of(2022, Month.FEBRUARY, 12, 0, 0, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 28, 23, 59, 59));

        promoTransaction.insertPromo(promo);

    }

    public static void dataPopulateSMS(){
        //insert 30 SMS for the "PISO PIZZA" promo
        //15 successful, 15 failed
        for(int index = 0; index < 15; index++){
            transactionID = sms.generateTransactionID("PISO PIZZA");
           sms = new SMS(transactionID,
                   "msisdn " + (index+1),
                   "System",
                   "dataPopulation "  + (index+1),
                   "1Pizza",
                   LocalDateTime.now());

            smsTransaction.insertSMS(sms, true);
        }

        for(int index = 0; index < 15; index++){
            transactionID = sms.generateTransactionID("PISO PIZZA");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "System",
                    "dataPopulation "  + (index+1),
                    "1Pizza",
                    LocalDateTime.now());

            smsTransaction.insertSMS(sms, false);
        }

        //insert 15 SMS for the "FREE SHIPPING" promo
        //7 successful, 8 failed
        for(int index = 0; index < 7; index++){
            transactionID = sms.generateTransactionID("FREE SHIPPING");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "System",
                    "dataPopulation "  + (index+1),
                    "FreeShipMin1k",
                    LocalDateTime.now());

            smsTransaction.insertSMS(sms, true);
        }

        for(int index = 0; index < 8; index++){
            transactionID = sms.generateTransactionID("FREE SHIPPING");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "System",
                    "dataPopulation "  + (index+1),
                    "FreeShipMin1k",
                    LocalDateTime.now());

            smsTransaction.insertSMS(sms, false);
        }

        //insert 15 SMS for the "PHP150 OFF, MIN 700" promo
        //7 successful, 8 failed
        for(int index = 0; index < 7; index++){
            transactionID = sms.generateTransactionID("PHP150 OFF, MIN 700");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "System",
                    "dataPopulation "  + (index+1),
                    "150Off",
                    LocalDateTime.now());

            smsTransaction.insertSMS(sms, true);
        }

        for(int index = 0; index < 8; index++){
            transactionID = sms.generateTransactionID("PHP150 OFF, MIN 700");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "System",
                    "dataPopulation "  + (index+1),
                    "150Off",
                    LocalDateTime.now());

            smsTransaction.insertSMS(sms, false);
        }
    }
}
