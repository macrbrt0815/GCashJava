package model;

import java.sql.Connection;

public interface SMSValidator {
    boolean SMSChecker(Connection connection, SMS sms);

    boolean validatePromoCode(Connection connection, String promoCode);
    boolean validateShortCode(Connection connection, String promoCode);

    boolean validatePromoShortCode(Connection connection, String promoCode, String shortCode);

}
