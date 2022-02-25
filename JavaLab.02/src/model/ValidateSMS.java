package model;

import View.Display;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValidateSMS implements SMSValidator{
    final private static Logger logger = Logger.getLogger(ValidateSMS.class.getName());
    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    PromoTransactions promoTransaction = new PromoTransactions();
    SMSTransactions smsTransaction = new SMSTransactions();

    @Override
    public void SMSChecker(SMS sms) {
        Map dates = promoTransaction.retrievePromoStartEndDates(sms.getShortCode());
        Map smsChecker = new HashMap();

        Display display = new Display();

        String promoStartDate = String.valueOf(dates.get("startDate"));
        String promoEndDate = String.valueOf(dates.get("endDate"));

        if (sms.getTimeStamp().isAfter(LocalDateTime.parse(promoStartDate, format))
                && sms.getTimeStamp().isBefore(LocalDateTime.parse(promoEndDate, format))){

            logger.log(Level.INFO,"Success, adding SMS transaction to db");
            smsTransaction.insertSMS(sms, true);
            smsChecker.put("message", "PROMO CODE ACCEPTED");

            //insert system response into database
            SMS systemSMS = new SMS("SYSSUCCESS " + sms.getTransactionID(),
                    sms.getMsisdn(),
                    sms.getSender(),
                    "System",
                    sms.shortCode,
                    LocalDateTime.now());

            smsTransaction.insertSMS(systemSMS, true);

        } else {
            logger.log(Level.INFO,"Failed, adding SMS transaction to db");
            smsTransaction.insertSMS(sms, false);
            smsChecker.put("message", "PROMO CODE REJECTED");

            //insert system response into database
            SMS systemSMS = new SMS("SYSFAILED " + sms.getTransactionID(),
                    sms.getMsisdn(),
                    sms.getSender(),
                    "System",
                    sms.shortCode,
                    LocalDateTime.now());

            smsTransaction.insertSMS(systemSMS, false);
        }
        smsChecker.put("mobileNumber", sms.getMsisdn());
        smsChecker.put("shortCode", sms.getShortCode());
        display.displaySMSChecker(smsChecker);
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
