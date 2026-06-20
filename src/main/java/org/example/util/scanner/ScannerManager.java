package org.example.util.scanner;

import org.example.model.Nameable;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public interface ScannerManager {
    String scanString();

    default int scanInt() {
        while (true) {
            try {
                return Integer.parseInt(scanString());
            } catch (InputMismatchException exception) {
                System.out.println("Ввод должны быть числом");
            }
        }
    }

    default int scanBorderInt(int min, int max) {
        while (true) {
            int input = scanInt();
            if (input < min || input > max) {
                System.out.println("Вводимое число должно быть между " + min + " и " + max);
                continue;
            }

            return input;
        }
    }

    default <T extends Nameable> T select(T[] values) {
        printStringList(Arrays.stream(values)
                .map(T::getName)
                .toArray(String[]::new));

        int chosenIndex = scanBorderInt(1, values.length) - 1;
        return values[chosenIndex];
    }

    default <T extends Nameable> T select(List<T> values) {
        printStringList(values.stream().map(T::getName).toList());

        int chosenIndex = scanBorderInt(1, values.size()) - 1;
        return values.get(chosenIndex);
    }

    default String select(String[] values) {
        printStringList(values);
        int chosenIndex = scanBorderInt(1, values.length) - 1;
        return values[chosenIndex];
    }

    default void printStringList(List<String> values) {
        printStringList(values.toArray(String[]::new));
    }

    default void printStringList(String[] values) {
        for (int i = 0; i < values.length; i++) {
            System.out.print(i + 1);
            System.out.print(") ");
            System.out.println(values[i]);
        }
    }
}
