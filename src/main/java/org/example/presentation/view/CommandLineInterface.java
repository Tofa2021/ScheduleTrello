package org.example.presentation.view;

import lombok.RequiredArgsConstructor;
import org.example.model.Lesson;
import org.example.model.LessonType;
import org.example.model.Subject;
import org.example.service.ScheduleService;
import org.example.service.TaskManagerService;
import org.example.util.scanner.ScannerManager;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class CommandLineInterface implements Interface {
    private final ScannerManager scannerManager;
    private final TaskManagerService taskManagerService;
    private final ScheduleService scheduleService;
    private final String groupId;
    private final String listToCreateId;
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

    private Subject selectSubject() {
        List<String> subjectNames = scheduleService.getSubjectNames(groupId);
        String selectedSubjectName = scannerManager.select(subjectNames.toArray(String[]::new));

        return scheduleService.getSubject(selectedSubjectName, groupId);
    }

    private void showSubjectAllLessonsAction() {
        Subject subject = selectSubject();

        scannerManager.printStringList(subject.getLessonsByTypes(selectLessonTypes()).stream().map(Object::toString).toList());
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
        Subject selectedSubject = selectSubject();
        createSubjectTasks(selectedSubject.getLessonsByTypes(selectLessonTypes()), selectedSubject.getName());
    }

    private void createSubjectRemainingLessonsAction() {
        Subject selectedSubject = selectSubject();
        createSubjectTasks(selectedSubject.getRemainingAndTypesLessons(LocalDate.now(), selectLessonTypes()), selectedSubject.getName());
    }

    private void createAllSubjectsAllLessonsAction() {
        List<Subject> subjects = scheduleService.getSubjects(groupId);
        for (Subject subject : subjects) {
            String subjectName = subject.getName();
            System.out.println("Нужно ли добавлять таски по предмету " + subjectName);
            if (scannerManager.scanBoolean()) {
                createSubjectTasks(subject.getLessonsByTypes(selectLessonTypes()), subjectName);
            }
        }
    }

    private void createAllSubjectsRemainingLessonsAction() {
        List<Subject> subjects = scheduleService.getSubjects(groupId);
        for (Subject subject : subjects) {
            String subjectName = subject.getName();
            System.out.println("Нужно ли добавлять оставшиеся таски по предмету " + subjectName);
            if (scannerManager.scanBoolean()) {
                createSubjectTasks(subject.getRemainingAndTypesLessons(LocalDate.now(), selectLessonTypes()), subjectName);
            }
        }
    }

    private Set<LessonType> selectLessonTypes() {
        System.out.println("Выберите виды занятий");
        List<LessonType> types = scannerManager.multiSelect(LessonType.values());
        return new HashSet<>(types);
    }

    private void createSubjectTasks(List<Lesson> lessons, String subjectName) {
        System.out.println("Сколько работ по предмету " + subjectName);
        int labCount = scannerManager.scanBorderInt(0, 20);

        taskManagerService.createTasks(lessons, subjectName, labCount, listToCreateId);
    }

    private void exit() {
        isStopped = true;
    }
}
