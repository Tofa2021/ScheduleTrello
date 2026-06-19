package org.example;

import org.example.presentation.view.Interface;

/*
String studentGroup = "414302";
int subgroupNumber = 1;
String boardId = "68146b5cbb7f1c40fe48f3c4";
String listId = "68146b8a44c2d2ecd675194f";
List<Subject> subjects = ScheduleParser.getSubjects(studentGroup);
*/

public class Main {
    public static void main(String[] args) throws Exception {
        final String groupId = "414302";

        DependencyInjection dependencyInjection = new DependencyInjection();
        Interface userInterface = dependencyInjection.inject(groupId);

        userInterface.open();
    }
}
