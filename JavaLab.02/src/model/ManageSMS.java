package model;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ManageSMS {

    void insertSMS(Connection connection, SMS sms);
    ArrayList retrieveSMS(Connection connection);

    ArrayList retrieveSMSStartEndDate(Connection connection, LocalDateTime startDate, LocalDateTime endDate);
    ArrayList retrieveSMSPromoCode(Connection connection, String shortCode);
    ArrayList retrieveSMSMSISDN(Connection connection, String msisdn);

    ArrayList retrieveSMSBySystem();
    ArrayList retrieveSMSToSystem();
}
