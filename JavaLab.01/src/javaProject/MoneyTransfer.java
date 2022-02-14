package javaProject;

import java.time.LocalDateTime;

public class MoneyTransfer extends Transaction{
    private String recipient = "";

    // Constructor that accepts data both for inherited and local variables
    public MoneyTransfer(String storeId, String transactionId, String accountId, String timeStamp, Float amount,
                         String recipient) {
        this.storeId = storeId;
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.timeStamp = timeStamp;
        this.amount = amount;

        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    // typecast object into string to be able to display all object data instead of hashed data
    public String toString() {
        return "\n[MONEY TRANSFER] Transaction ID: " + this.getTransactionId() +
                "\nStore ID: " + this.getStoreId() +
                "\nAccount ID: " + this.getAccountId() +
                "\nTime Stamp: " + this.getTimeStamp() +
                "\nAmount: " + this.getAmount() +
                "\nRecipient: " + this.getRecipient() +
                "\n";
    }
}
