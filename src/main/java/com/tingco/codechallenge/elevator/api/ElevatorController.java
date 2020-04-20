package com.tingco.codechallenge.elevator.api;

import java.util.List;


/**
 * Interface for the Elevator Controller.
 *
 * @author Sven Wesley
 *
 */
interface ElevatorController {

    /**
     * Request an elevator to the specified floor.
     *
     * @param toFloor
     *            addressed floor as integer.
     * @return The Elevator that is going to the floor, if there is one to move.
     */
    Elevator requestElevator(int toFloor);

    /**
     * A snapshot list of all elevators in the system.
     *
     * @return A List with all {@link Elevator} objects.
     */
    List<Elevator> getElevators();

    /**
     * Telling the controller that the given elevator is free for new
     * operations.
     *
     * @param elevator
     *            the elevator that shall be released.
     */
    void releaseElevator(Elevator elevator);

    /**
     * Return the Id of the elevator available at requested floor
     *
     * @param floor
     * @return
     */
    Integer getElevatorOnFloor(int floor);

    /**
     * Return the elevator by id if not busy
     *
     * @param elevatorId
     * @return
     */
    Elevator getElevatorById(int elevatorId);
}
