package io.wisoft.javatest.ch3.adapters;

import io.wisoft.javatest.ch3.ports.TimeProvider;

import java.time.DayOfWeek;

public class FakeTimeProvider implements TimeProvider {

    private final DayOfWeek fakeDay;

    public FakeTimeProvider(DayOfWeek fakeDay) {
        this.fakeDay = fakeDay;
    }

    @Override
    public DayOfWeek getDay() {
        return this.fakeDay;
    }
}
