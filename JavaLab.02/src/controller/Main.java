package controller;

import model.Promo;
import model.SMS;
import utility.DBConnection;
import utility.SingletonDBConnection;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    Logger logger = Logger.getLogger(Main.class.getName());
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

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
        Promo promo;

        //Insert promo
        // Insert Piso Pizza promo
        promo = new Promo("PISO PIZZA",
                "Pizza for 1 peso only. " +
                        "Valid from February 1 - March 1, 2022.",
                "1Pizza",
                LocalDateTime.of(2022, Month.FEBRUARY, 1, 0,0,0),
                LocalDateTime.of(2022, Month.MARCH, 1,0,0,0));

        promo.addPromo(connection, promo);

        // Insert Free Shipping promo
        promo = new Promo("FREE SHIPPING",
                "Free shipping fee nationwide minimum spent of Php 1000.00. " +
                        "Valid from March 3 - March 4, 2022. ",
                "FreeShipMin1k",
                LocalDateTime.of(2022, Month.MARCH, 3,0,0,0),
                LocalDateTime.of(2022, Month.MARCH, 3, 23, 59,59));

        promo.addPromo(connection, promo);

        // Insert 150 OFF promo
        promo = new Promo("PHP150 OFF, MIN 700",
                "Less Php 150, minimum spent of 700. " +
                        "Valid from February 12 - February 28, 2022.",
                "150Off",
                LocalDateTime.of(2022, Month.FEBRUARY, 1,0,0,0),
                LocalDateTime.of(2022, Month.FEBRUARY, 28, 23, 59,59));

        promo.addPromo(connection, promo);

        //insert 30 SMS for the "PISO PIZZA" promo
        //initial data, to be updated
        for(int index = 0; index < 30; index++){
           sms = new SMS("msisdn " + (index+1),
                   "recipient "  + (index+1),
                   "sender "  + (index+1),
                   "1Pizza",
                   LocalDateTime.now());

            sms.addSMS(connection);
        }

        //insert 15 SMS for the "FREE SHIPPING" promo
        //initial data, to be updated
        for(int index = 0; index < 15; index++){
            sms = new SMS("msisdn " + (index+1),
                    "recipient "  + (index+1),
                    "sender "  + (index+1),
                    "FreeShipMin1k",
                    LocalDateTime.now());

            sms.addSMS(connection);
        }

        //insert 15 SMS for the "PHP150 OFF, MIN 700" promo
        //initial data, to be updated
        for(int index = 0; index < 15; index++){
            sms = new SMS("msisdn " + (index+1),
                    "recipient "  + (index+1),
                    "sender "  + (index+1),
                    "150Off",
                    LocalDateTime.now());

            sms.addSMS(connection);
        }

    }


}
