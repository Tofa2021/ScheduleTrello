package org.example.presentation.view;

import lombok.RequiredArgsConstructor;
import org.example.model.Subject;
import org.example.service.ScheduleService;
import org.example.service.TaskManagerService;
import org.example.util.Utils;

import java.util.List;

@RequiredArgsConstructor
public class CommandLineInterface implements Interface {
    private final TaskManagerService taskManagerService;
    private final ScheduleService scheduleService;
    private final String groupId;
    private boolean isStopped = false;

    public void open() {
        isStopped = false;
        show();
    }

    private void show() {
        while (!isStopped) {
            showMenu();
            switch (Utils.scanInt()) {
                case 1 -> createScheduleCards();
                case 2 -> showSubjectLessons();
                case 3 -> exit();
                default -> System.out.println("Нет такого пункта");
            }
        }
    }

    private void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1) Создать карточки по расписанию");
        System.out.println("2) Просмотр пар по предмету");
        System.out.println("0) Выход");
    }

    private void createScheduleCards() {
//        taskManagerService.createTasks();
    }

    private void showSubjectLessons() {
        List<String> subjectNames = scheduleService.getSubjectNames(groupId);
        String selectedSubjectName = Utils.select(subjectNames.toArray(String[]::new));

        Subject subject = scheduleService.getSubject(selectedSubjectName, groupId);

        Utils.printStringList(subject.getLessons().stream().map(Object::toString).toList());
    }

    private void exit() {
        isStopped = true;
    }
}
