package javaProject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionProcessing {
    private static ArrayList transactionList = new ArrayList<>();
    private static TransactionTypes transactionType;

    final private static Logger logger = Logger.getLogger(TransactionProcessing.class.getName());
    private static DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void main(String[] args) {
        init();

        //display all data in transactionList
        logger.log(Level.INFO, "Displaying all Transactions");
        showTransaction();

        //display data by transaction type [MONEY TRANSFER]
        transactionType = TransactionTypes.MoneyTransfer;
        logger.log(Level.INFO, "Displaying all Money Transfer Transactions");
        showTransactionType(transactionType);

        //display data by data type [BILLS PAYMENT]
        transactionType = TransactionTypes.BillsPayment;
        logger.log(Level.INFO, "Displaying all Bills Payment Transactions");
        showTransactionType(transactionType);

        //display data by data type [BUY LOAD]
        transactionType = TransactionTypes.BuyLoad;
        logger.log(Level.INFO, "Displaying all Buy Load Transactions");
        showTransactionType(transactionType);

        //display data by data type [ACCOUNT CREDITS]
        transactionType = TransactionTypes.AddAccountCredit;
        logger.log(Level.INFO, "Displaying all Account Credits Transactions");
        showTransactionType(transactionType);

        //display data by data type [GAME CREDITS]
        transactionType = TransactionTypes.AddGameCredit;
        logger.log(Level.INFO, "Displaying all Game Credits Transactions");
        showTransactionType(transactionType);

        //display all unique and duplicate Bills Payment Transactions
        uniqueDuplicateBills();

    }

    public static void init(){
        //invoke all add functionality
        addMoneyTransfer();
        addBillsPayment();
        addBuyLoad();
        addAccountCredits();
        addGameCredits();
    }

    public static void addMoneyTransfer(){
        MoneyTransfer moneyTransfer;

        LocalDateTime timeStamp = LocalDateTime.now();

        //Add 5 Money Transfer objects in the Transaction ArrayList
        for(int mt = 0; mt < 5; mt++){
            moneyTransfer = new MoneyTransfer("Store " + (mt+1),
                     "MT" + (mt+1),
                    "Account " + (mt+1),
                    timeStamp.now().format(format),
                    100f,
                    "Recipient " + (mt+1));

            transactionList.add(moneyTransfer);
        }
    }

    public static void addBillsPayment(){
        BillsPayment billsPayment;

        LocalDateTime timeStamp = LocalDateTime.now();
        //Add 3 Bills Payment objects in the Transaction ArrayList

        //Add duplicating Bills Payment transactions
        for(int bp = 0; bp < 3; bp++){
            billsPayment = new BillsPayment("Store ",
                     "BP",
                    "Account ",
                    timeStamp.format(format),
                    100f,
                    "Company ",
                    100f);

            transactionList.add(billsPayment);
        }

        //add varying Bills Payment transactions
        for(int bp = 0; bp < 3; bp++){
            billsPayment = new BillsPayment("Store " + (bp+1),
                    "BP " + (bp+1),
                    "Account " + (bp+1),
                    timeStamp.now().format(format),
                    100f,
                    "Company " + (bp+1),
                    100f);

            transactionList.add(billsPayment);
        }
    }

    public static void addBuyLoad(){
        BuyLoad buyLoad;

        LocalDateTime timeStamp = LocalDateTime.now();

        //Add 5 Buy Load objects in the Transaction ArrayList
        for(int bl = 0; bl < 5; bl++){
            buyLoad = new BuyLoad("Store " + (bl+1),
                    "BL " + (bl+1),
                    "Account " + (bl+1),
                    timeStamp.now().format(format),
                    100f,
                    "Mobile Number " + (bl+1));

            transactionList.add(buyLoad);
        }
    }

    public static void addAccountCredits(){
        AddAccountCredit accountCredit;

        LocalDateTime timeStamp = LocalDateTime.now();

        //Add 2 Add Account Credits objects in the Transaction ArrayList
        for(int ac = 0; ac < 2; ac++){
            accountCredit = new AddAccountCredit("Store " + (ac+1),
                    "AAC " + (ac+1),
                    "Account " + (ac+1),
                    timeStamp.now().format(format),
                    100f,
                    "Mobile Number " + (ac+1));
            transactionList.add(accountCredit);
        }
    }

    public static void addGameCredits(){
        AddGameCredit gameCredit;

        LocalDateTime timeStamp = LocalDateTime.now();

        //Add 5 Add Game Credits objects in the Transaction ArrayList
        for(int gc = 0; gc < 5; gc++){
            gameCredit = new AddGameCredit("Store " + (gc+1),
                    "AGC " +  (gc+1),
                    "Account " + (gc+1),
                    timeStamp.now().format(format),
                    100f,
                    "Company Name " + (gc+1));
            transactionList.add(gameCredit);
        }
    }

    //show all transactions
    public static void showTransaction(){
        for(Object transaction: transactionList) {
            logger.log(Level.INFO, transaction.toString());
        }
    }

    //show transactions by Type
    public static void showTransactionType(Object transactionType){
        for(Object transaction: transactionList) {
            //filter transactions by transaction type
            if(transaction.getClass().getName().contains(transactionType.toString())) {
                logger.log(Level.INFO, transaction.toString());
            }
        }
    }

    public static void uniqueDuplicateBills(){
        ArrayList duplicateBills = new ArrayList();
        HashSet<Object> uniqueBills = new HashSet();

        //Filter bills payment transactions
        for(Object transaction : transactionList){
            //filter bills payment transactions
            if(transaction.getClass().getName().contains(TransactionTypes.BillsPayment.toString())){
                if(uniqueBills.contains(transaction.toString())){
                    //verify if transaction is already in the uniqueBills HashSet
                    //if true, transaction will be considered as duplicate
                    duplicateBills.add(transaction);
                }else {
                    //else, transaction will be considered as unique
                    uniqueBills.add(transaction.toString());
                }
            }
        }

        //display unique and duplicate transactions
        //show all unique bills
        logger.log(Level.INFO, "Displaying all unique Bills");
        for(Object unique : uniqueBills){
            logger.log(Level.INFO, unique.toString());
        }
        //show all duplicates
        logger.log(Level.INFO, "Displaying all duplicate Bills");
        for(Object duplicate : duplicateBills){
            logger.log(Level.INFO, duplicate.toString());
        }
    }

    public ArrayList getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(ArrayList transactionList) {
        this.transactionList = transactionList;
    }
}
