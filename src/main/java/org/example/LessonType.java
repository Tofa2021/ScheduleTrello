package org.example;

import javax.naming.Name;

public enum LessonType implements Nameable {
    LECTURE("ЛК"),
    PRACTICAL("ПЗ"),
    LABORATORY("ЛР"),
    ;

    private final String name;

    LessonType(String name) {
        this.name = name;
    }

    public static LessonType convertToLessonType(String stringLessonType) {
        return switch (stringLessonType) {
            case "ПЗ" -> PRACTICAL;
            case "ЛР" -> LABORATORY;
            case "ЛК" -> LECTURE;
            default -> throw new IllegalStateException("Unexpected value: " + stringLessonType);
        };
    }

    @Override
    public String getName() {
        return name;
    }
}
