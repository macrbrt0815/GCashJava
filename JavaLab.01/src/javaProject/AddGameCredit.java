package javaProject;

import java.time.LocalDateTime;

public class AddGameCredit extends Transaction{
    private String companyName = "";

    // Constructor that accepts data both for inherited and local variables
    public AddGameCredit(String storeId, String transactionId, String accountId, String timeStamp, Float amount,
                         String companyName){
        this.storeId = storeId;
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.timeStamp = timeStamp;
        this.amount = amount;

        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        // typecast object into string to be able to display all object data instead of hashed data
        return "\n[GAME CREDIT] Transaction ID: " + this.getTransactionId() +
                "\nStore ID: " + this.getStoreId() +
                "\nAccount ID: " + this.getAccountId() +
                "\nTime Stamp: " + this.getTimeStamp() +
                "\nAmount: " + this.getAmount() +
                "\nMobile Number: " + this.getCompanyName() +
                "\n";
    }
}
