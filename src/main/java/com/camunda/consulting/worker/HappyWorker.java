package com.camunda.consulting.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HappyWorker {

    @JobWorker(type = "invokeHappiness")
    public void invokeHappiness(@Variable Boolean Happy){
        log.info("We are all happy, because it is {}", Happy);
    }
}
