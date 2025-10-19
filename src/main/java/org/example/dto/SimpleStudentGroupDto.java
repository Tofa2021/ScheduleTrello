package org.example.dto;

import lombok.Data;

@Data
public class SimpleStudentGroupDto {
    private String specialityName;
    private String specialityCode;
    private int numberOfStudents;
    private String name;
    private int educationDegree;
}
