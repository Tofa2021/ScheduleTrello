package org.example;

import org.example.presentation.view.Interface;

public class Main {
    public static void main(String[] args) {
        DependencyInjection dependencyInjection = new DependencyInjection();
        Interface userInterface = dependencyInjection.inject();

        userInterface.open();
    }
}
