package org.example;

public enum LessonType {
    LECTURE,
    PRACTICAL,
    LABORATORY,
    ;

    public static LessonType convertToLessonType(String stringLessonType) {
        return switch (stringLessonType) {
            case "ПЗ" -> PRACTICAL;
            case "ЛР" -> LABORATORY;
            case "ЛК" -> LECTURE;
            default -> throw new IllegalStateException("Unexpected value: " + stringLessonType);
        };
    }
}
