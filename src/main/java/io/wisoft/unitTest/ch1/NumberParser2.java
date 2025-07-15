package io.wisoft.unitTest.ch1;

public class NumberParser2 {

    private static int total = 0;

    public static int totalSoFar() {
        return total;
    }

    public static int sum(String numbers) {
        String[] parts = numbers.split(",");
        int a = Integer.parseInt(parts[0].trim());
        int b = Integer.parseInt(parts[1].trim());

        int result = a + b;
        total += result;
        return result;
    }
}
