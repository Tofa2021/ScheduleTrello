package org.example;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DateConverter {
    public static LocalDate convertToDate(String stringDate) {
        String[] parts = stringDate.split("\\.");
        List<Integer> dateParts = Arrays.stream(parts).map(Integer::parseInt).toList();
        return LocalDate.of(dateParts.get(2), dateParts.get(1), dateParts.get(0));
    }
}
