package org.example;

import org.example.presentation.api.HttpClient;
import org.example.presentation.api.HttpClientImpl;
import org.example.presentation.api.schedule.BSUIRScheduleApiClient;
import org.example.presentation.api.schedule.ScheduleApiClient;
import org.example.presentation.api.task_manager.TaskManagerApiClient;
import org.example.presentation.api.task_manager.TrelloApiClient;
import org.example.presentation.view.CommandLineInterface;
import org.example.presentation.view.Interface;
import org.example.service.*;
import org.example.util.PropertiesUtil;
import org.example.util.json.GsonMapper;
import org.example.util.json.JsonMapper;
import org.example.util.scanner.ScannerManager;
import org.example.util.scanner.SystemInScannerManager;

public class DependencyInjection {
    public Interface inject() {
        final String groupId = PropertiesUtil.getProperty("groupId");
        final String listId = PropertiesUtil.getProperty("trelloListId");

        ScannerManager scannerManager = new SystemInScannerManager();

        JsonMapper jsonMapper = new GsonMapper();

        HttpClient httpClient = new HttpClientImpl(jsonMapper);

        TaskManagerApiClient taskManagerApiClient = new TrelloApiClient(httpClient);
        ScheduleApiClient scheduleApiClient = new BSUIRScheduleApiClient(httpClient);


        DateCalculator dateCalculator = new DateCalculatorImpl();
        LessonConverter lessonConverter = new LessonConvertorImpl(dateCalculator);
        SubjectConverter subjectConverter = new SubjectConverterImpl(lessonConverter);

        ScheduleService scheduleService = new ScheduleServiceImpl(scheduleApiClient, subjectConverter);
        TaskManagerService taskManagerService = new TaskManagerServiceImpl(taskManagerApiClient);

        CommandLineInterface commandLineInterface = new CommandLineInterface(scannerManager, taskManagerService, scheduleService, groupId, listId);
        return commandLineInterface;
    }
}
