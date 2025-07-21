package io.wisoft.javatest.ch1;

public class CustomTestPhase1 {

    public static void parserTest() {
        try {
            int result = sum("1,2");

            if(result == 3) {
                System.out.println("parserTest Example 1 PASSED");
            } else {
                throw new RuntimeException("parserTest: expected 3 but was " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int sum(String numbers) {
        String[] parts = numbers.split(",");
        return Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
    }

    public static void main(String[] args) {
        parserTest();
    }
}
