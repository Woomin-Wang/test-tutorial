package io.wisoft.javatest.ch3.adapters;

import io.wisoft.javatest.ch3.ports.TimeProvider;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class RealTimeProvider implements TimeProvider {
    @Override
    public DayOfWeek getDay() {
        return LocalDate.now().getDayOfWeek();
    }
}
