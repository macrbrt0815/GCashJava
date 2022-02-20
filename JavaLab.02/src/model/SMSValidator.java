package model;

import java.sql.Connection;

public interface SMSValidator {
    void SMSChecker(Connection connection, SMS sms);

    boolean validatePromoCode(Connection connection, String promoCode);
    boolean validateShortCode(Connection connection, String shortCode);

    boolean validatePromoShortCode(Connection connection, String promoCode, String shortCode);

}
