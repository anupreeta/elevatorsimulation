package com.tingco.codechallenge.elevator;

import com.google.common.eventbus.EventBus;
import com.tingco.codechallenge.elevator.config.Config;
import com.tingco.codechallenge.elevator.event.ElevatorEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tingco.codechallenge.elevator.config.ElevatorApplication;

import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Boiler plate test class to get up and running with a test faster.
 *
 * @author Sven Wesley
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElevatorApplication.class)
public class IntegrationTest {

    @Autowired
    private EventBus requests;

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private Config config;

    @Test
    public void simulateAnElevatorShaft() throws InterruptedException {
        int totalIncomingRequests = 50;
        int i = 0;
        while (i < totalIncomingRequests) {
            requests.post(new ElevatorEvent(generateRandomElevatorId(), generateRandomFloor()));
            i++;
        }
        do {
            Thread.sleep(100);
        } while (executor.getActiveCount() > 0);
    }

    private int generateRandomElevatorId() {
        Random r = new Random();
        return r.nextInt(config.getNumberOfElevators() - 1) + 1;
    }

    private int generateRandomFloor() {
        Random r = new Random();
        return r.nextInt(config.getNumberOfFloors());
    }
}
