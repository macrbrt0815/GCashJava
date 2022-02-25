package utility;

import controller.Main;

import java.io. *;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Helper {
    final private static Logger logger = Logger.getLogger(Main.class.getName());

    private static BufferedReader getReader() { //Allocate new object
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        return reader;
    }
    public static String getStringInput(String message) throws IOException {
        String input = null;
        logger.log(Level.INFO, message);
        BufferedReader reader = getReader();

        input = reader.readLine();
        return input;
    }
}