package io.wisoft.javatest.ch4.external;

public class FakeComplicatedLogger implements IComplicatedLogger {
    public String infoWritten = "";
    public String debugWritten = "";
    public String warnWritten = "";
    public String errorWritten = "";

    // PasswordVerifier2 테스트는 이 메서드만 필요로 한다.
    @Override
    public void info(String text) {
        this.infoWritten = text;
    }

    @Override
    public void debug(String text, Object obj) {
        this.debugWritten = text;
    }

    @Override
    public void warn(String text) {
        this.warnWritten = text;
    }

    @Override
    public void error(String text, String location, String stacktrace) {
        this.errorWritten = text;
    }
}
