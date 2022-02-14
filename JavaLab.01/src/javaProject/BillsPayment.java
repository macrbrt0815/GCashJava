package javaProject;

public class BillsPayment extends Transaction{
    private String companyName = "";
    private Float billsCharge;

    // Constructor that accepts data both for inherited and local variables
    public BillsPayment(String storeId, String transactionId, String accountId, String timeStamp, Float amount,
                        String companyName, Float billsCharge){
        this.storeId = storeId;
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.timeStamp = timeStamp;
        this.amount = amount;

        this.companyName = companyName;
        this.billsCharge = billsCharge;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Float getBillsCharge() {
        return billsCharge;
    }

    public void setBillsCharge(Float billsCharge) {
        this.billsCharge = billsCharge;
    }

    @Override
    // typecast object into string to be able to display all object data instead of hashed data
    public String toString() {
        return "\n[BILLS PAYMENT] Transaction ID: " + this.getTransactionId() +
                "\nStore ID: " + this.getStoreId() +
                "\nAccount ID: " + this.getAccountId() +
                "\nTime Stamp: " + this.getTimeStamp() +
                "\nAmount: " + this.getAmount() +
                "\nCompany Name: " + this.getCompanyName() +
                "\nBills Charge: " + this.getBillsCharge() +
                "\n";

    }
}
