package org.example.dto;

import lombok.Data;

@Data
public class StudentGroupDto {
    private String name;
    private int facultyId;
    private String facultyAbbrev;
    private int specialityDepartmentEducationFormId;
    private String specialityName;
    private String specialityAbbrev;
    private int course;
    private int id;
    private String calendarId;
    private int educationDegree;
}
