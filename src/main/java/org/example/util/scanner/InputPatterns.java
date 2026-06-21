package org.example.util.scanner;

import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum InputPatterns {
    TRUE_BOOLEAN(Set.of("true", "yes", "да", "+", "1")),
    FALSE_BOOLEAN(Set.of("false", "no", "нет", "-", "0")),
    BOOLEAN(Stream.concat(TRUE_BOOLEAN.patterns.stream(), FALSE_BOOLEAN.patterns.stream()).collect(Collectors.toSet())),

    DATE(Set.of("dd.MM.yyyy", "dd-MM-yyyy")),
    ;

    private final Set<String> patterns;

    public boolean match(String a) {
        return patterns.contains(a.toLowerCase().trim());
    }

    public String getPatternsAsString() {
        return String.join(", ", patterns);
    }
}
