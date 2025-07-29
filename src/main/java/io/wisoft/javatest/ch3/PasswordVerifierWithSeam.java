package io.wisoft.javatest.ch3;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

public class PasswordVerifierWithSeam {

    static class Dependencies {
        Supplier<DayOfWeek> dayOfWeekSupplier;
    }

    private static final Dependencies originalDependencies = new Dependencies();

    static {
        originalDependencies.dayOfWeekSupplier = () ->
                LocalDate.now().getDayOfWeek();
    }

    private static Dependencies dependencies = originalDependencies;

    public static void inject(Dependencies fakes) {
        dependencies = fakes;
    }

    public static void reset() {
        dependencies = originalDependencies;
    }

    public List<String> verifyPassword(String input) {
        DayOfWeek dayOfWeek = dependencies.dayOfWeekSupplier.get();

        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalStateException("It's the weekend!");
        }

        //...

        return List.of();
    }
}
