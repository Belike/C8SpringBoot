package com.camunda.consulting.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ExampleService {

    public boolean trueFalseExample(){
        return ThreadLocalRandom.current().nextBoolean();
    }
}
