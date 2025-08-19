package io.wisoft.javatest.ch5;

public interface IComplicatedLogger {
    void info(String text, String method);

    void debug(String text, String method);

    void warn(String text, String method);

    void error(String text, String method);
}