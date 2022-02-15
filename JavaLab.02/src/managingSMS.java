import java.time.LocalDate;

public interface managingSMS {

    void insertSMS();
    void retrieveSMSStartEndDate();
    void retrieveSMSPromoCode();
    void retrieveSMSMSISDN();
    void retrieveSMSBySystem();
    void retrieveSMSToSystem();
    void retrieveSMS();
}
