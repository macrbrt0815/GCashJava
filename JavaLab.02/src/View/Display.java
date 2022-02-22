package View;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Display {
    final private static Logger logger = Logger.getLogger(Display.class.getName());
    public void displaySMSChecker(Map smsChecker){
        logger.log(Level.INFO, "Mobile Number: " + String.valueOf(smsChecker.get("mobileNumber")) +
                "\nMessage: " + String.valueOf(smsChecker.get("message")) +
                "\nShort Code: " + String.valueOf(smsChecker.get("shortCode")));
    }
    public void displayPromo(ArrayList allPromo){
        if (allPromo != null) {
            for(Object promo : allPromo){
                logger.log(Level.INFO, promo.toString());
            }
        }

    }
    public void displaySMS(ArrayList allSMS){
        if (allSMS != null){
            for(Object promo : allSMS){
                logger.log(Level.INFO, promo.toString());
            }
        }
    }
}
