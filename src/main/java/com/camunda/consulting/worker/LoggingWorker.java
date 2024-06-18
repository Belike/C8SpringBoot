package com.camunda.consulting.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;

@Component
@Slf4j
public class LoggingWorker {

    @JobWorker(type = "logging", autoComplete = true)
    public HashMap<String, Object> logging(ActivatedJob job){
        log.info("Logging initiated");

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("logging", "Yes, logging");
        variables.put("timestamp", Calendar.getInstance().getTime());

        return variables;
    }
}
