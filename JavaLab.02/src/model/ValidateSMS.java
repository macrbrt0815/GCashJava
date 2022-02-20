package model;

import java.sql.Connection;

public class ValidateSMS implements SMSValidator{
    @Override
    public boolean SMSChecker(Connection connection, SMS sms) {
        return false;
    }

    @Override
    public boolean validatePromoCode(Connection connection, String promoCode) {
        return false;
    }

    @Override
    public boolean validateShortCode(Connection connection, String promoCode) {
        return false;
    }

    @Override
    public boolean validatePromoShortCode(Connection connection, String promoCode, String shortCode) {
        return false;
    }
}
