package org.example.presentation.api.schedule;

import org.example.dto.GroupScheduleDto;

public interface ScheduleApiClient {
    GroupScheduleDto getGroupSchedule(String groupId);

    int getCurrentWeekNumber();
}
