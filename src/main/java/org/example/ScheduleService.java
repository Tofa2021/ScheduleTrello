package org.example;

import org.example.dto.GroupScheduleDto;

import java.io.IOException;
import java.util.Collections;

public class ScheduleService {
    private static final ApiClient bsuirApiClient = new ApiClient("https://iis.bsuir.by/api/v1");

    public static GroupScheduleDto getGroupSchedule(String studentGroupId) throws IOException {
        return bsuirApiClient.get("/schedule", GroupScheduleDto.class, Collections.singletonMap("studentGroup", studentGroupId));
    }

    public static int getCurrentWeekNumber() throws IOException {
        return bsuirApiClient.get("/schedule/current-week", Integer.class, null);
    }
}
