package com.tingco.codechallenge.elevator.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorServiceTest {

    private ElevatorService service;

    @BeforeEach
    void setUp() {
        service = new ElevatorService(2,10);
    }

    @Test
    void requestElevatorWhenAllAvailable() {
        Elevator elevator = service.requestElevator(2);

        assertNotNull(elevator);
    }

    @Test
    void requestElevatorWhenAllAddressed() {
        service.getElevators().forEach(e -> e.moveElevator(5));

        Elevator elevator = service.requestElevator(5);

        assertNull(elevator);
    }

    @Test
    void requestElevatorFromNearestFloor() {
        Elevator elevator1 = service.getElevators().get(0);
        Elevator elevator2 = service.getElevators().get(1);

        elevator1.moveElevator(1);
        elevator2.moveElevator(2);

        elevator1.moveUp(1);
        elevator2.moveUp(2);

        Elevator elevator = service.requestElevator(6);

        assertNotNull(elevator);
    }

    @Test
    void getCountOfElevators() {
        assertEquals(2, service.getElevators().size());
    }

    @Test
    void shouldReleaseElevatorAfterMovingUp() {
        Elevator elevator = service.getElevators().get(0);
        elevator.moveUp(4);
        elevator.moveElevator(4);

        elevator.moveUp(3);

        service.releaseElevator(elevator);
        assertEquals(7, elevator.currentFloor());
    }

    @Test
    void shouldReleaseElevatorAfterMovingDown() {
        Elevator elevator = service.getElevators().get(0);
        elevator.moveUp(6);
        elevator.moveElevator(6);

        elevator.moveDown(2);

        service.releaseElevator(elevator);
        assertEquals(4, elevator.currentFloor());
    }

    @Test
    void getElevatorOnFloor() {
        Elevator elevator = service.getElevators().get(1);
        elevator.moveElevator(6);
        elevator.moveUp(6);
        Integer elevatorOnFloor = service.getElevatorOnFloor(6);
        assertEquals(2, elevatorOnFloor);
    }

    @Test
    void shouldGetElevatorById() {
        Elevator elevator = service.getElevators().get(0);
        elevator.moveElevator(6);
        elevator.moveUp(6);

        Elevator elevatorToVerify = service.getElevatorById(1);
        assertEquals(6, elevatorToVerify.currentFloor());

    }
}