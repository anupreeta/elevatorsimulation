package com.tingco.codechallenge.elevator.config;

import java.util.concurrent.*;

import com.tingco.codechallenge.elevator.api.ElevatorService;
import com.tingco.codechallenge.elevator.event.ElevatorEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/**
 * Preconfigured Spring Application boot class.
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.tingco.codechallenge.elevator" })
@EnableAutoConfiguration
@PropertySources({ @PropertySource("classpath:application.properties") })
public class ElevatorApplication {

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

    /**
     * Start method that will be invoked when starting the Spring context.
     *
     * @param args
     *            Not in use
     */
    public static void main(final String[] args) {
        SpringApplication.run(ElevatorApplication.class, args);
    }

    /**
     * Create a default thread pool for your convenience.
     *
     * @return Executor thread pool
     */
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolExecutor elevatorPool() {
        return new ThreadPoolExecutor(numberOfElevators, numberOfElevators,
                5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(numberOfElevators));
    }

    /**
     * Create an event bus for your convenience.
     *
     * @return EventBus for async task execution
     */
    @Bean
    public EventBus eventBus(ElevatorEventListener listener) {
        EventBus bus = new AsyncEventBus(Executors.newSingleThreadExecutor());
        bus.register(listener);
        return bus;
    }

    @Bean
    public ElevatorService service() {
        return new ElevatorService(numberOfElevators, numberOfFloors);
    }
}
