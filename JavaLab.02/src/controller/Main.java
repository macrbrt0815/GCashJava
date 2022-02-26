package controller;

import View.Display;
import model.*;
import utility.Helper;
import utility.SingletonDBConnection;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
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
        String menuChoice = "";
        String choice = "";
        String promoCode = "";

        do{
            menuChoice = Helper.getStringInput("What do you want to do? \n" +
                    "1 - Populate Data \n" +
                    "2 - Register to a Promo \n" +
                    "3 - Retrieve Transactions \n" +
                    "4 - Generate a Report \n");

            switch (menuChoice){
                case "1":
                    dataPopulatePromo();
                    dataPopulateSMS();
                    break;
                case "2":
                    userInput();
                    break;
                case "3":
                    retrieveTransactions();
                    break;
                case "4":
                    promoCode = Helper.getStringInput("Enter Promo Code: ");
                    if(validateSMS.validatePromoCode(promoCode)){
                        logger.log(Level.INFO, "Report generated for promo code: " + promoCode);
                        smsTransaction.generateReport(promoCode);
                    }
                    break;
            }
            choice = Helper.getStringInput("Do you want do something else? [YES/NO]: ");
        } while (choice.equalsIgnoreCase("YES"));
        logger.log(Level.INFO, "Program Terminating ...");
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
                }
            }

            choice = Helper.getStringInput("Do you want to Register another promo? [YES/NO]: ");
        } while (choice.equalsIgnoreCase("YES"));
    }

    public static void retrieveTransactions(){
        //retrieve all sms
        logger.log(Level.INFO, "RETRIEVE ALL SMS");
        display.displaySMS(smsTransaction.retrieveSMS());

        //retrieve sms by start and end dates
        logger.log(Level.INFO, "RETRIEVE SMS GIVEN A START AND END DATE");
        LocalDateTime startDate = null; //specify start date
        LocalDateTime endDate = null; //specify end date
        display.displaySMS(smsTransaction.retrieveSMSStartEndDate(startDate, endDate));

        //retrieve sms by promo code
        logger.log(Level.INFO, "RETRIEVE SMS GIVEN A PROMO CODE");
        String promoCode = "PISO PIZZA"; //specify promo code
        display.displaySMS(smsTransaction.retrieveSMSPromoCode(promoCode));

        //retrieve sms by msisdn
        logger.log(Level.INFO, "RETRIEVE SMS GIVEN AN MSISDN");
        String msisdn = "msisdn 1"; //specify msisdn
        display.displaySMS(smsTransaction.retrieveSMSMSISDN(msisdn));

        //retrieve sms by several msisdn
        logger.log(Level.INFO, "RETRIEVE SMS GIVEN SEVERAL MSISDN");
        String arrMSISDN[] = {"09688515895", "msisdn 2", "msisdn 6"}; //specify msisdn
        display.displaySMS(smsTransaction.retrieveSMSSeveralMSISDN(arrMSISDN));

        //retrieve sms sent by system
        logger.log(Level.INFO, "RETRIEVE SMS SENT BY SYSTEM");
        display.displaySMS(smsTransaction.retrieveSMSBySystem());

        //retrieve sms sent to system
        logger.log(Level.INFO, "RETRIEVE SMS RECEIVED BY SYSTEM");
        display.displaySMS(smsTransaction.retrieveSMSToSystem());

        //retrieve all promos
        logger.log(Level.INFO, "RETRIEVE ALL PROMOS");
        display.displayPromo(promoTransaction.retrievePromo());
    }

    public static void dataPopulatePromo() {
        //Insert promo
        // Insert Piso Pizza promo
        promo = new Promo("PISO PIZZA",
                "Pizza for 1 peso only. " +
                        "Valid from February 1 - March 1, 2022.",
                "1111",
                LocalDateTime.of(2022, Month.FEBRUARY, 1, 0, 0, 0),
                LocalDateTime.of(2022, Month.MARCH, 1, 0, 0, 0));

        promoTransaction.insertPromo(promo);

        // Insert Free Shipping promo
        promo = new Promo("FREE SHIPPING",
                "Free shipping fee nationwide minimum spent of Php 1000.00. " +
                        "Valid from March 3 - March 4, 2022. ",
                "2222",
                LocalDateTime.of(2022, Month.MARCH, 3, 0, 0, 0),
                LocalDateTime.of(2022, Month.MARCH, 3, 23, 59, 59));

        promoTransaction.insertPromo(promo);

        // Insert 150 OFF promo
        promo = new Promo("PHP150 OFF",
                "Less Php 150, minimum spent of 700. " +
                        "Valid from February 12 - February 28, 2022.",
                "3333",
                LocalDateTime.of(2022, Month.FEBRUARY, 12, 0, 0, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 28, 23, 59, 59));

        promoTransaction.insertPromo(promo);

    }

    public static void dataPopulateSMS(){
        //insert 30 SMS for the "PISO PIZZA" promo
        //15 successful, 15 failed
        //PISO PIZZA successful data
        for(int index = 0; index < 15; index++){
            transactionID = sms.generateTransactionID("PISO PIZZA");
           sms = new SMS(transactionID,
                   "msisdn " + (index+1),
                   "System",
                   "dataPopulation "  + (index+1),
                   "1111",
                   LocalDateTime.of(2022, Month.FEBRUARY, 26, 0, 0, 0));

            validateSMS.SMSChecker(sms);
        }

        //PISO PIZZA failed data
        for(int index = 0; index < 15; index++){
            transactionID = sms.generateTransactionID("PISO PIZZA");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "System",
                    "dataPopulation "  + (index+1),
                    "1111",
                    LocalDateTime.of(2022, Month.MARCH, 2, 0, 0, 0));

            validateSMS.SMSChecker(sms);
        }

        //insert 15 SMS for the "FREE SHIPPING" promo
        //7 successful, 8 failed
        //FREE SHIPPING successful data
        for(int index = 0; index < 7; index++){
            transactionID = sms.generateTransactionID("FREE SHIPPING");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "System",
                    "dataPopulation "  + (index+1),
                    "2222",
                    LocalDateTime.of(2022, Month.MARCH, 5, 0, 0, 0));

            validateSMS.SMSChecker(sms);
        }

        //FREE SHIPPING failed data
        for(int index = 0; index < 8; index++){
            transactionID = sms.generateTransactionID("FREE SHIPPING");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "System",
                    "dataPopulation "  + (index+1),
                    "2222",
                    LocalDateTime.of(2022, Month.MARCH, 1, 0, 0, 0));

            validateSMS.SMSChecker(sms);
        }

        //insert 15 SMS for the "PHP150 OFF" promo
        //7 successful, 8 failed
        //PHP 150 OFF successful data
        for(int index = 0; index < 7; index++){
            transactionID = sms.generateTransactionID("PHP150 OFF");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "System",
                    "dataPopulation "  + (index+1),
                    "3333",
                    LocalDateTime.of(2022, Month.FEBRUARY, 15, 0, 0, 0));

            validateSMS.SMSChecker(sms);
        }

        //PHP 150 OFF failed data
        for(int index = 0; index < 8; index++){
            transactionID = sms.generateTransactionID("PHP150 OFF");
            sms = new SMS(transactionID,
                    "msisdn " + (index+1),
                    "System",
                    "dataPopulation "  + (index+1),
                    "3333",
                    LocalDateTime.of(2022, Month.MARCH, 15, 0, 0, 0));

            validateSMS.SMSChecker(sms);
        }
    }
}
