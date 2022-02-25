package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SMS {
    protected String transactionID = "";
    protected String msisdn = "";
    protected String recipient = "";
    protected String sender = "";
    protected String shortCode = "";
    protected LocalDateTime timeStamp;

    protected boolean isSuccessful;


    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SMS(){
    }

    public SMS(String transactionID, String msisdn, String recipient, String sender , String shortCode, LocalDateTime timeStamp){
        this.transactionID = transactionID;
        this.msisdn = msisdn;
        this.recipient = recipient;
        this.sender = sender;
        this.shortCode = shortCode;
        this.timeStamp = timeStamp;
    }

    public SMS(String transactionID, String msisdn, String recipient, String sender , String shortCode, LocalDateTime timeStamp, boolean isSuccessful){
        this.transactionID = transactionID;
        this.msisdn = msisdn;
        this.recipient = recipient;
        this.sender = sender;
        this.shortCode = shortCode;
        this.timeStamp = timeStamp;
        this.isSuccessful = isSuccessful;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isSuccessful() { return isSuccessful; }

    public void setSuccessful(boolean successful) { isSuccessful = successful; }

    public String generateTransactionID(String promoCode){
        //retrieve all sms in the db with the given shortcode
        SMSTransactions smsTransaction = new SMSTransactions();
        ArrayList transactionList  = smsTransaction.retrieveSMSPromoCode(promoCode);

        //return promoCode + size of transaction list + 1 (transactionID format)
        if (!transactionList.isEmpty()){
            return (promoCode + " " + String.valueOf(transactionList.size() + 1));
        } else {
            return (promoCode + " 1");
        }
    }

    @Override
    public String toString() {
        return "SMS" +
                "\nTransaction ID: " + this.transactionID +
                "\nMSISDN: " + this.msisdn +
                "\nRecipient: " + this.recipient +
                "\nSender: " + this.sender +
                "\nShort Code: " + this.shortCode +
                "\nTime Stamp: " + this.timeStamp.format(format) + "\n";
    }
}
