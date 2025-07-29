package io.wisoft.javatest.ch3.time;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class RealTimeProvider implements TimeProvider {
    @Override
    public DayOfWeek getDay() {
        return LocalDate.now().getDayOfWeek();
    }
}
