package org.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static Scanner scanner = new Scanner(System.in);

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

    public static int scanBorderInt(int lowerBorder, int upperBorder) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= lowerBorder && input <= upperBorder) {
                    return input;
                } else {
                    System.out.println("Вводимое число должно быть между " + lowerBorder + " и " + upperBorder);
                }
            } catch (InputMismatchException exception) {
                System.out.println("Ввод должны быть числом");
                scanner.next();
            }
        }
    }

    public static int scanInt() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException exception) {
                System.out.println("Ввод должны быть числом");
                scanner.next();
            }
        }
    }

    public static <T extends Nameable> T select(T[] values) {
        for (int i = 0; i < values.length; i++) {
            System.out.print(i + 1);
            System.out.print(") ");
            System.out.println(values[i].getName());
        }
        int chosenIndex = scanBorderInt(1, values.length) - 1;
        return values[chosenIndex];
    }
}
