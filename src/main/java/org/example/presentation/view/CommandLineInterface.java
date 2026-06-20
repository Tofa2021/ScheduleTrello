package org.example.presentation.view;

import lombok.RequiredArgsConstructor;
import org.example.model.Subject;
import org.example.service.ScheduleService;
import org.example.service.TaskManagerService;
import org.example.util.scanner.ScannerManager;

import java.util.List;

@RequiredArgsConstructor
public class CommandLineInterface implements Interface {
    private final ScannerManager scannerManager;
    private final TaskManagerService taskManagerService;
    private final ScheduleService scheduleService;
    private final String groupId;
    private final String listId;
    private boolean isStopped = false;

    public void open() {
        isStopped = false;
        show();
    }

    private void show() {
        while (!isStopped) {
            showMenuAction();
            switch (scannerManager.scanInt()) {
                case 1 -> showSubjectLessonsAction();
                case 2 -> createScheduleCardsAction();
                case 3 -> createSubjectCardsAction();
                case 0 -> exit();
                default -> System.out.println("Нет такого пункта");
            }
        }
    }

    private void showMenuAction() {
        System.out.println("Выберите действие:");
        System.out.println("1) Просмотр пар по предмету");
        System.out.println("2) Создать карточки по расписанию");
        System.out.println("3) Создать карточки по предмету");
        System.out.println("0) Выход");
    }

    private void createScheduleCardsAction() {
        List<Subject> subjects = scheduleService.getSubjects(groupId);
        for (Subject subject : subjects) {
            System.out.println("Нужно ли добавлять таски по предмету " + subject.getName());
            if (scannerManager.scanBoolean()) {
                createSubjectTasks(subject);
            }
        }
    }

    private void createSubjectCardsAction() {
        createSubjectTasks(selectSubject());
    }

    private void createSubjectTasks(Subject subject) {
        System.out.println("Сколько работ по предмету " + subject.getName());
        int labCount = scannerManager.scanBorderInt(0, 20);

        taskManagerService.createSubjectTasks(subject, labCount, listId);
    }

    private void showSubjectLessonsAction() {
        Subject subject = selectSubject();

        scannerManager.printStringList(subject.getLessons().stream().map(Object::toString).toList());
    }

    private Subject selectSubject() {
        List<String> subjectNames = scheduleService.getSubjectNames(groupId);
        String selectedSubjectName = scannerManager.select(subjectNames.toArray(String[]::new));

        return scheduleService.getSubject(selectedSubjectName, groupId);
    }

    private void exit() {
        isStopped = true;
    }
}
