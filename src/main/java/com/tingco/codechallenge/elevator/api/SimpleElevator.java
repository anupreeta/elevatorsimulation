package com.tingco.codechallenge.elevator.api;

import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleElevator implements Elevator, Runnable {

    private final int id;
    private int addressedFloor;
    private int currentFloor;
    private final int topFloor;

    private static final Logger logger = LoggerFactory.getLogger(SimpleElevator.class);

    public SimpleElevator(int id, int topFloor) {
        this.id = id;
        this.addressedFloor = 0;
        this.currentFloor = 0;
        this.topFloor = topFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getTopFloor() {
        return topFloor;
    }

    public void moveUp(int numberOfFloors) {
        if (currentFloor < topFloor) {
            currentFloor += numberOfFloors;
        }
    }

    public void moveDown(int numberOfFloors) {
        if (currentFloor > 0) {
            currentFloor -= numberOfFloors;
        }
    }

    @Override
    public Direction getDirection() {
        if (currentFloor < addressedFloor) {
            return Direction.UP;
        } else if (currentFloor > addressedFloor) {
            return Direction.DOWN;
        } else {
            return Direction.NONE;
        }
    }

    @Override
    public int getAddressedFloor() {
        return addressedFloor;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void moveElevator(int toFloor) {
        addressedFloor = toFloor;
    }

    @Override
    public boolean isBusy() {
        return currentFloor != addressedFloor;
    }

    @Override
    public int currentFloor() {
        return currentFloor;
    }

    @Override
    public void run() {
        logger.info(this.toString());
        while(isBusy()) {
            inTransit();
            switch (getDirection()) {
                case UP:
                    moveUp(1);
                    break;
                case DOWN:
                    moveDown(1);
                    break;
            }
        }
        logger.info(this.toString());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("busy", isBusy())
                .add("direction", getDirection())
                .add("addressedFloor", addressedFloor)
                .add("currentFloor", currentFloor)
                .toString();
    }

    private void inTransit() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
