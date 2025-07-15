package io.wisoft.unitTest.ch1;

public class CustomTestPhase3 {

    public static void assertEquals(int expected, int actual) throws Exception {
        if(expected != actual) {
            throw new Exception("Expected " + expected + " but was " + actual);
        }
    }

    public static void check(String name, Runnable implementation) {
        try {
            implementation.run();
            System.out.println(name + " passed");
        } catch (Exception e) {
            System.out.println(name + " FAILED");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        check("sum with 2 numbers should sum them up", () -> {
            try {
                int result = NumberParser.sum("1,2");
                assertEquals(3, result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        check("sum with multiple digit numbers should sum them up", () -> {
            try {
                int result = NumberParser.sum("10,20");
                assertEquals(30, result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
