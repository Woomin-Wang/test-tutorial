package io.wisoft.javatest.ch4.external;

public class FakeLogger implements ILogger {
    public String infoWritten = "";
    public String debugWritten = "";


    @Override
    public void info(String text) {
        this.infoWritten = text;
    }

    @Override
    public void debug(String text) {
        this.debugWritten = text;
    }
}
