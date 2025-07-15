package io.wisoft.unitTest.ch1;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NumberParser3 {
    private static int total = 0;
    private static final Logger logger = Logger.getLogger(NumberParser3.class.getName());

    public static int totalSoFar() {
        return total;
    }

    public static int sum(String numbers) {
        String[] parts = numbers.split(",");
        String a = parts[0].trim();
        String b = parts[1].trim();

        logger.log(Level.INFO, String.format(
                "this is a very important log output - firstNumWas: %s, secondNumWas: %s", a, b
        ));

        int result = Integer.parseInt(a) + Integer.parseInt(b);
        total += result;
        return result;
    }
}