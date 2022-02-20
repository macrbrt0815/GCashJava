package utility;

import java.io. *;
public class Helper {
    private static BufferedReader getReader() { //Allocate new object
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        return reader;
    }
    public static String getStringInput(String message) throws IOException {
        String input=null;
        System.out.println(message);
        BufferedReader reader = getReader();

        input = reader.readLine();
        return input;
    }

    public static int getIntegerInput(String message) throws IOException {
        int input=0;
        System.out.println(message);

        BufferedReader reader = getReader();
        input = Integer.parseInt(reader.readLine());
        return input;
    }

    public static double getDoubleInput(String message) throws IOException {
        double input=0d;
        System.out.println(message);

        BufferedReader reader = getReader();
        input = Double.parseDouble(reader.readLine());
        return input;
    }
}