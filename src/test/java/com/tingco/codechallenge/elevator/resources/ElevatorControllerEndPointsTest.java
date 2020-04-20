package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.config.Config;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tingco.codechallenge.elevator.config.ElevatorApplication;

import javax.validation.ValidationException;

import static org.junit.Assert.assertNull;

/**
 * Boiler plate test class to get up and running with a test faster.
 *
 * @author Sven Wesley
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElevatorApplication.class)
public class ElevatorControllerEndPointsTest {

    @Autowired
    private ElevatorControllerEndPoints endPoints;

    @Autowired
    private Config config;

    @Test
    public void ping() {
        Assert.assertEquals("pong", endPoints.ping());
    }

    @Test
    public void sendValidRequestToElevator() throws InterruptedException {
        endPoints.requestElevator(1, 8);
        Thread.sleep(900);
        Integer id = endPoints.findElevatorAvailableOnFloor(8);
        Assert.assertNotNull(id);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenNegativeFloor() throws InterruptedException {
        endPoints.findElevatorAvailableOnFloor(-1);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenExceedsTopFloor() {
        endPoints.findElevatorAvailableOnFloor(config.getNumberOfFloors() + 1);
    }

    @Test
    public void testValidationMaxFloor() {
        Integer id = endPoints.findElevatorAvailableOnFloor(config.getNumberOfFloors());
        assertNull(id);
    }
}
