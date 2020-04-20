package com.tingco.codechallenge.elevator.event;

public class ElevatorEvent {

    private final int elevatorId;
    private final int requestedFloorNumber;

    public ElevatorEvent(int elevatorId, int requestedFloorNumber) {
        this.elevatorId = elevatorId;
        this.requestedFloorNumber = requestedFloorNumber;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getRequestedFloorNumber() {
        return requestedFloorNumber;
    }

}
