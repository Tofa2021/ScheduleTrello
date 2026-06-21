package org.example.util.scanner;

import org.example.model.Nameable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public interface ScannerManager {
    String scanString();

    default int scanInt() {
        while (true) {
            try {
                return Integer.parseInt(scanString());
            } catch (InputMismatchException | NumberFormatException exception) {
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

    default boolean scanBoolean() {
        while (true) {
            String input = scanString();
            if (InputPatterns.FALSE_BOOLEAN.match(input)) {
                return false;
            }
            if (InputPatterns.TRUE_BOOLEAN.match(input)) {
                return true;
            }
            System.out.println("Не удалось понять согласие или отрицание");
        }
    }

    default LocalDate scanDate() {
        while (true) {
            String dateString = scanString();
            try {
                return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("Формат даты должен быть такой (дд.мм.гггг)");
            }
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

    default <T extends Nameable> List<T> multiSelect(T[] values, int selectionNumber) {
        if (values.length < selectionNumber) {
            throw new IllegalArgumentException("Selection number cannot be bigger that values length");
        }

        List<T> result = new ArrayList<>();
        List<T> cloned = new ArrayList<>(List.of(values));
        while (selectionNumber-- > 0) {
            printStringList(cloned);
            T selected = select(cloned);
            result.add(selected);
            cloned.remove(selected);
        }

        return result;
    }

    default <T extends Nameable> List<T> multiSelect(T[] values) {
        System.out.println("Для завершения выбора нажмите ENTER");

        List<T> copiedElements = new ArrayList<>(Arrays.asList(values));
        List<T> selectedElements = new ArrayList<>();

        while (!copiedElements.isEmpty()) {
            printStringList(copiedElements);
            String input = scanString();
            if (input.isEmpty()) {
                break;
            }

            try {
                int index = Integer.parseInt(input) - 1;
                if (index < 0 || index >= copiedElements.size()) {
                    System.out.println("Число выходит за границу диапазона. Повторите попытку");
                    continue;
                }

                T element = copiedElements.get(index);
                copiedElements.remove(index);
                selectedElements.add(element);
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Ввод должны быть числом");
            }
        }

        return selectedElements;
    }

    default <T> void printStringList(List<T> values) {
        printStringList(values.stream().map(Object::toString).toArray(String[]::new));
    }

    default void printStringList(String[] values) {
        if (values.length == 0) {
            System.out.println("Пусто");
        }

        for (int i = 0; i < values.length; i++) {
            System.out.print(i + 1);
            System.out.print(") ");
            System.out.println(values[i]);
        }
    }
}
