package org.example.presentation.view;

import lombok.RequiredArgsConstructor;
import org.example.model.Lesson;
import org.example.model.Subject;
import org.example.service.ScheduleService;
import org.example.service.TaskManagerService;
import org.example.util.scanner.ScannerManager;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class CommandLineInterface implements Interface {
    private final ScannerManager scannerManager;
    private final TaskManagerService taskManagerService;
    private final ScheduleService scheduleService;
    private final String groupId;
    private final String listId;
    private final int subgroupNumber;
    private boolean isStopped = false;

    public void open() {
        isStopped = false;
        show();
    }

    private void show() {
        while (!isStopped) {
            showMenuAction();
            switch (scannerManager.scanInt()) {
                case 1 -> showSubjectAllLessonsAction();
                case 2 -> showSubjectRemainingLessonsAction();
                case 3 -> showSubjectAllSubgroupLessonsAction();
                case 4 -> showSubjectRemainingSubgroupLessonsAction();

                case 5 -> showTodayScheduleAction();
                case 6 -> showCurrentWeekScheduleAction();
                case 7 -> showDayScheduleAction();
                case 8 -> showWeekScheduleAction();

                case 9 -> createSubjectAllLessonsAction();
                case 10 -> createSubjectRemainingLessonsAction();
                case 11 -> createAllSubjectsAllLessonsAction();
                case 12 -> createAllSubjectsRemainingLessonsAction();

                case 0 -> exit();
                default -> System.out.println("Нет такого пункта");
            }
        }
    }

    private void showMenuAction() {
        System.out.println("Выберите действие:");
        System.out.println("1) Просмотреть всех пар по предмету");
        System.out.println("2) Просмотреть оставшихся пар по предмету");
        System.out.println("3) Просмотреть все пары подгруппы по предмету");
        System.out.println("4) Просмотреть оставшиеся пары подгруппы по предмету");

        System.out.println("5) Просмотреть сегодняшние расписание");
        System.out.println("6) Просмотреть расписание на текущую неделю");
        System.out.println("7) Просмотреть расписание на выбранный день");
        System.out.println("8) Просмотреть расписание на выбранную неделю");

        System.out.println("9) Создать карточки всех пар по предмету");
        System.out.println("10) Создать карточки оставшихся пар по предмету");
        System.out.println("11) Создать карточки всех пар по всем предметам");
        System.out.println("12) Создать карточки оставшихся пар по всем предметам");

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

    private Subject selectSubject() {
        List<String> subjectNames = scheduleService.getSubjectNames(groupId);
        String selectedSubjectName = scannerManager.select(subjectNames.toArray(String[]::new));

        return scheduleService.getSubject(selectedSubjectName, groupId);
    }

    private void showSubjectAllLessonsAction() {
        Subject subject = selectSubject();

        scannerManager.printStringList(subject.getLessons().stream().map(Object::toString).toList());
    }

    private void showSubjectRemainingLessonsAction() {
        Subject subject = selectSubject();
        LocalDate date = LocalDate.now();

        List<Lesson> lessons = subject.getRemainingLessons(date);
        scannerManager.printStringList(lessons);
    }

    private void showSubjectAllSubgroupLessonsAction() {
        Subject subject = selectSubject();

        List<Lesson> lessons = subject.getNotOnlySubgroupLessons(subgroupNumber);
        scannerManager.printStringList(lessons);
    }

    private void showSubjectRemainingSubgroupLessonsAction() {
        Subject subject = selectSubject();
        LocalDate dateFrom = LocalDate.now();

        List<Lesson> lessons = subject.getRemainingNotOnlySubgroupLessons(subgroupNumber, dateFrom);
        scannerManager.printStringList(lessons);
    }

    private void showTodayScheduleAction() {
        // Показать расписание на сегодня
    }

    private void showCurrentWeekScheduleAction() {
        // Показать расписание на текущую неделю
    }

    private void showDayScheduleAction() {
        // Показать расписание на выбранный день
    }

    private void showWeekScheduleAction() {
        // Показать расписание на выбранную неделю
    }

    private void createSubjectAllLessonsAction() {
        // Создать карточки для всех занятий по выбранному предмету
    }

    private void createSubjectRemainingLessonsAction() {
        // Создать карточки для оставшихся занятий по выбранному предмету
    }

    private void createAllSubjectsAllLessonsAction() {
        // Создать карточки для всех занятий по всем предметам
    }

    private void createAllSubjectsRemainingLessonsAction() {
        // Создать карточки для оставшихся занятий по всем предметам
    }

    private void exit() {
        isStopped = true;
    }
}
