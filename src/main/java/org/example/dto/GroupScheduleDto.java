package org.example.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GroupScheduleDto {
    private EmployeeDto employeeDto;
    private StudentGroupDto studentGroupDto;
    private Map<String, List<LessonDto>> schedules;
    private List<LessonDto> exams;
    private String startDate;
    private String endDate;
    private String startExamsDate;
    private String endExamsDate;
}
