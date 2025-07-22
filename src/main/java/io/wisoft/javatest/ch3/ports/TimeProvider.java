package io.wisoft.javatest.ch3.ports;

import java.time.DayOfWeek;

public interface TimeProvider {
    DayOfWeek getDay();
}
