package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class SMS {
    protected String transactionID = "";
    protected String msisdn = "";
    protected String recipient = "";
    protected String sender = "";
    protected String shortCode = "";
    protected LocalDateTime timeStamp;

    public SMS(String msisdn, String recipient, String sender , String shortCode, LocalDateTime timeStamp){
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

    public void addSMS(Connection connection){
        String sqlStatement = "insert into "
                + "sms(msisdn, recipient, sender, shortCode, timeStamp)"
                + "values (?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, msisdn);
            preparedStatement.setString(2, recipient);
            preparedStatement.setString(3, sender);
            preparedStatement.setString(4, shortCode);
            preparedStatement.setString(5, timeStamp.toString());

            preparedStatement.executeUpdate();
            System.out.println("SMS successfully added to DB");
        }catch(SQLException sqle) {
            System.out.println(sqle);
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}
