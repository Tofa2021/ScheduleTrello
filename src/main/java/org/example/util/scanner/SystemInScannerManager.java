package org.example.util.scanner;

import java.util.Scanner;

public class SystemInScannerManager implements ScannerManager {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String scanString() {
        return scanner.next();
    }
}
