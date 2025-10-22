package org.example;

import org.example.dto.GroupScheduleDto;

import java.io.IOException;
import java.util.Collections;

public class ScheduleService {
    public static GroupScheduleDto getGroupSchedule(String studentGroupId) throws IOException {
        return ApiClient.get("/schedule", GroupScheduleDto.class, Collections.singletonMap("studentGroup", studentGroupId));
    }

    public static int getCurrentWeekNumber() throws IOException {
        return ApiClient.get("/schedule/current-week", Integer.class, null);
    }
}
