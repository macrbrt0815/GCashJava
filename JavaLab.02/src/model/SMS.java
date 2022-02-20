package model;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SMS {
    protected String transactionID = "";
    protected String msisdn = "";
    protected String recipient = "";
    protected String sender = "";
    protected String shortCode = "";
    protected LocalDateTime timeStamp;

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

    public String generateTransactionID(Connection connection, String promoCode){
        //retrieve all sms in the db with the given shortcode
        SMSTransactions smsTransaction = new SMSTransactions();
        ArrayList transactionList  = smsTransaction.retrieveSMSPromoCode(connection, promoCode);

        //return promocode + size of transaction list + 1 (transactionID format)
        if (transactionList != null){
            return (promoCode + " " + String.valueOf(transactionList.size() + 1));
        } else {
            return (promoCode + " 1");
        }
    }

    @Override
    public String toString() {
        return "SMS{" +
                "transactionID='" + this.transactionID + '\'' +
                ", msisdn='" + this.msisdn + '\'' +
                ", recipient='" + this.recipient + '\'' +
                ", sender='" + this.sender + '\'' +
                ", shortCode='" + this.shortCode + '\'' +
                ", timeStamp=" + this.timeStamp +
                '}';
    }
}
