package javaProject;

import java.time.LocalDateTime;

public class BuyLoad extends Transaction{
    private String mobileNumber = "";

    // Constructor that accepts data both for inherited and local variables
    public BuyLoad(String storeId, String transactionId, String accountId, String timeStamp, Float amount,
                   String mobileNumber){
        this.storeId = storeId;
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.timeStamp = timeStamp;
        this.amount = amount;

        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    // typecast object into string to be able to display all object data instead of hashed data
    public String toString() {
        return "\n[BUY LOAD] Transaction ID: " + this.getTransactionId() +
                "\nStore ID: " + this.getStoreId() +
                "\nAccount ID: " + this.getAccountId() +
                "\nTime Stamp: " + this.getTimeStamp() +
                "\nAmount: " + this.getAmount() +
                "\nMobile Number: " + this.getMobileNumber() +
                "\n";
    }

}
