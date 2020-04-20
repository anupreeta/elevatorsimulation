package com.tingco.codechallenge.elevator.api;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class SimpleElevatorTest {

    private static final int ELEVATOR_ID = 1;
    private static final int TOP_FLOOR = 10;

    @Test
    void shouldMoveUp() {
        Elevator elevator = new SimpleElevator(ELEVATOR_ID, TOP_FLOOR);
        elevator.moveElevator(TOP_FLOOR);

        elevator.moveUp(4);

        //in transit
        assertThat(elevator.currentFloor(), is(4));
        assertThat(elevator.getDirection(), is(Elevator.Direction.UP));
        assertThat(elevator.isBusy(), is(true));

        elevator.moveUp(6);

        //destination
        assertThat(elevator.currentFloor(), is(TOP_FLOOR));
        assertThat(elevator.isBusy(), is(false));
        assertThat(elevator.getDirection(), is(Elevator.Direction.NONE));

    }

    @Test
    void shouldMoveDown() {
        Elevator elevator = new SimpleElevator(ELEVATOR_ID, TOP_FLOOR);

        elevator.moveUp(TOP_FLOOR); // go to top floor
        elevator.moveElevator(1); //set destination

        elevator.moveDown(2);

        //in transit
        assertThat(elevator.currentFloor(), is(8));
        assertThat(elevator.getDirection(), is(Elevator.Direction.DOWN));
        assertThat(elevator.isBusy(), is(true));

        elevator.moveDown(7);

        //destination
        assertThat(elevator.currentFloor(), is(1));
        assertThat(elevator.isBusy(), is(false));
        assertThat(elevator.getDirection(), is(Elevator.Direction.NONE));
    }

    @Test
    public void shouldMoveUpFromGroundFloor(){
        Elevator elevator = new SimpleElevator(ELEVATOR_ID, TOP_FLOOR);
        System.out.println(elevator.currentFloor());
        elevator.moveUp(8);

        System.out.println(elevator.currentFloor());
        assertEquals(8, elevator.currentFloor());
    }

    @Test
    public void shouldMoveDownFromTopFloor(){
        Elevator elevator = new SimpleElevator(ELEVATOR_ID, TOP_FLOOR);
        elevator.moveElevator(TOP_FLOOR);
        elevator.moveDown(8);

        assertEquals(0, elevator.currentFloor());
    }
}