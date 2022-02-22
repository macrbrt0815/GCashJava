package model;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ManageSMS {

    void insertSMS(SMS sms, boolean isSuccessful);

    ArrayList retrieveSMS();

    ArrayList retrieveSMSStartEndDate(LocalDateTime startDate, LocalDateTime endDate);
    ArrayList retrieveSMSPromoCode(String shortCode);
    ArrayList retrieveSMSMSISDN(String msisdn);

    ArrayList retrieveSMSBySystem();
    ArrayList retrieveSMSToSystem();
}
