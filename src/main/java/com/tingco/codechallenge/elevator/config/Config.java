package com.tingco.codechallenge.elevator.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.tingco.codechallenge.elevator.api.ElevatorService;
import com.tingco.codechallenge.elevator.event.ElevatorEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class Config {

    @Value("${com.tingco.elevator.numberofelevators}")
    private int numberOfElevators;

    @Value("${com.tingco.elevator.numberoffloors}")
    private int numberOfFloors;

    public int getNumberOfElevators() {
        return numberOfElevators;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

}
