package com.camunda.consulting.facade;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class ProcessStarterHelper {

    private final ZeebeClient zeebeClient;

    public ProcessStarterHelper(ZeebeClient zeebeClient){
        this.zeebeClient = zeebeClient;
    }

    //Synchronous Start of an Instance
    public ProcessInstanceEvent startInstanceSynchronously(String procKey){
        log.info("Starting new instance (synchronously).");
        return zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId(procKey)
                .latestVersion()
                .send()
                .join();
    }

    //Synchronous Start of an Instance with Variables
    public ProcessInstanceEvent startInstanceWithVariablesSynchronously(String procKey, Map<String, Object> variables) {
        log.info("Starting new instance (synchronously) with Variables.");
        return zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId(procKey)
                .latestVersion()
                .variables(variables)
                .send()
                .join();
    }
    //Asynchronous Start of an Instance
    public void startInstanceAsynchronously(String procKey) throws RuntimeException {
        log.info("Starting new instance (asynchronously) with Variables");
        zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId(procKey)
                .latestVersion()
                .send()
                .exceptionally(
                        t -> {
                            log.info("Having an error in starting instance!");
                            throw new RuntimeException("Couldn't Start Instance");
                        });
    }

    //Asynchronous Start of an Instance with Variables
    public void startInstanceWithVariablesAsynchronously(String procKey, Map<String, Object> variables) throws RuntimeException {
        log.info("Starting new instance (asynchronously) with Variables");
        zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId(procKey)
                .latestVersion()
                .variables(variables)
                .send()
                .exceptionally(
                        t -> {
                            log.info("Having an error in starting instance!");
                            throw new RuntimeException("Couldn't Start Instance");
                        });
    }
}
