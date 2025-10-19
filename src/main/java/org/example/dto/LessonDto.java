package org.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class LessonDto {
    private List<Integer> weekNumber;
    private List<SimpleStudentGroupDto> studentGroups;
    private int numSubgroup; //Если 0 - значит подгруппы у пары нету
    private List<String> auditories;
    private String startLessonTime;
    private String endLessonTime;
    private String subject;
    private String subjectFullName;
    private String note;
    private String lessonTypeAbbrev;
    private String dateLesson;
    private String startLessonDate;
    private String endLessonDate;
    private boolean announcement;
    private boolean split;
    private List<EmployeeDto> employees;
}
