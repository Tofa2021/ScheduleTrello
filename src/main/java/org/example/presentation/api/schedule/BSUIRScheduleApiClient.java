package org.example.presentation.api.schedule;

import lombok.RequiredArgsConstructor;
import org.example.dto.GroupScheduleDto;
import org.example.presentation.api.HttpClient;

import java.util.Map;

@RequiredArgsConstructor
public class BSUIRScheduleApiClient implements ScheduleApiClient {
    private final String BASE_URL = "https://iis.bsuir.by/api/v1";
    private final HttpClient httpClient;

    @Override
    public GroupScheduleDto getGroupSchedule(String groupId) {
        return httpClient.get(BASE_URL + "/schedule", GroupScheduleDto.class, Map.of("studentGroup", groupId));
    }

    @Override
    public int getCurrentWeekNumber() {
        return httpClient.get(BASE_URL + "/schedule/current-week", Integer.class);
    }
}
