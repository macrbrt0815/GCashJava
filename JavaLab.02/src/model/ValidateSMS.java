package model;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Level;

public class ValidateSMS implements SMSValidator{
    PromoTransactions promoTransaction = new PromoTransactions();

    @Override
    public void SMSChecker(Connection connection, SMS sms) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map dates = promoTransaction.retrievePromoStartEndDates(connection, sms.getShortCode());

        String promoStartDate = String.valueOf(dates.get("startDate"));
        String promoEndDate = String.valueOf(dates.get("endDate"));

        if(sms.getTimeStamp().isAfter(LocalDateTime.parse(promoStartDate, format))
                && sms.getTimeStamp().isBefore(LocalDateTime.parse(promoEndDate, format))){
           //display success
        } else {
            //display failed
        }
    }

    @Override
    public boolean validatePromoCode(Connection connection, String promoCode) {

        //using shortCode retrieval method, verify if a promo with given promoCode exists
        if(promoTransaction.retrieveShortCodeByPromoCode(connection, promoCode) != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean validateShortCode(Connection connection, String shortCode) {

        //using promoCode retrieval method, verify if a promo with given shortCode exists
        if(promoTransaction.retrievePromoCodeByShortCode(connection, shortCode) != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean validatePromoShortCode(Connection connection, String promoCode, String shortCode) {

        //using shortCode retrieval method, verify if given shortCode is correct to the given promoCode
        if(promoTransaction.retrieveShortCodeByPromoCode(connection, promoCode).equals(shortCode)){
            return true;
        } else {
            return false;
        }
    }
}
