package io.wisoft.javatest.ch4.external;

public class RealLogger implements ILogger {
    @Override
    public void info(String text) {
        System.out.println("INFO: " + text);
    }

    @Override
    public void debug(String text) {
        System.out.println("DEBUG: " + text);
    }
}

