package model;

import java.sql.Connection;

public interface SMSValidator {
    void SMSChecker(SMS sms);

    boolean validatePromoCode(String promoCode);
    boolean validateShortCode(String shortCode);

    boolean validatePromoShortCode(String promoCode, String shortCode);

}
