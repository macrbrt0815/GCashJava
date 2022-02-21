package model;

import utility.SingletonDBConnection;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValidateSMS implements SMSValidator{
    final private static Logger logger = Logger.getLogger(SMSTransactions.class.getName());
    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static Connection connection = SingletonDBConnection.getConnection();

    PromoTransactions promoTransaction = new PromoTransactions();
    SMSTransactions smsTransaction = new SMSTransactions();

    @Override
    public void SMSChecker(SMS sms) {
        Map dates = promoTransaction.retrievePromoStartEndDates(sms.getShortCode());

        String promoStartDate = String.valueOf(dates.get("startDate"));
        String promoEndDate = String.valueOf(dates.get("endDate"));

        if(sms.getTimeStamp().isAfter(LocalDateTime.parse(promoStartDate, format))
                && sms.getTimeStamp().isBefore(LocalDateTime.parse(promoEndDate, format))){
            System.out.println("Success, adding SMS transaction to db");
            smsTransaction.insertSMS(sms, true);
           //display success
        } else {
            //display failed
            System.out.println("Failed, adding SMS transaction to db");
            smsTransaction.insertSMS(sms, false);
        }
    }

    @Override
    public boolean validatePromoCode(String promoCode) {

        //using shortCode retrieval method, verify if a promo with given promoCode exists
        if(promoTransaction.retrieveShortCodeByPromoCode(promoCode) != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean validateShortCode(String shortCode) {

        //using promoCode retrieval method, verify if a promo with given shortCode exists
        if(promoTransaction.retrievePromoCodeByShortCode(shortCode) != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean validatePromoShortCode(String promoCode, String shortCode) {

        //using shortCode retrieval method, verify if given shortCode is correct to the given promoCode
        if(promoTransaction.retrieveShortCodeByPromoCode(promoCode).equals(shortCode)){
            return true;
        } else {
            return false;
        }
    }
}
