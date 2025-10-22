package org.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static LocalDate convertToDate(String stringDate) {
        String[] parts = stringDate.split("\\.");
        List<Integer> dateParts = Arrays.stream(parts).map(Integer::parseInt).toList();
        return LocalDate.of(dateParts.get(2), dateParts.get(1), dateParts.get(0));
    }

    public static DayOfWeek convertToEngDayOfWeek(String rusDayOfWeek) {
        int dayOfWeek = 0;
        dayOfWeek = switch (rusDayOfWeek) {
            case "Понедельник" -> 1;
            case "Вторник" -> 2;
            case "Среда" -> 3;
            case "Четверг" -> 4;
            case "Пятница" -> 5;
            case "Суббота" -> 6;
            case "Воскресенье" -> 7;
            default -> throw new IllegalStateException("Unexpected value: " + rusDayOfWeek);
        };

        return DayOfWeek.of(dayOfWeek);
    }
}
