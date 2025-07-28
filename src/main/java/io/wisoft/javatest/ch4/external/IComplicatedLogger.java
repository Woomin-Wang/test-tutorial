package io.wisoft.javatest.ch4.external;

public interface IComplicatedLogger {
    void info(String text);
    void debug(String text, Object obj);
    void warn(String text);
    void error(String text, String location, String stacktrace);
}
