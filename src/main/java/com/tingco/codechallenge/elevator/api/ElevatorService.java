package com.tingco.codechallenge.elevator.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

@Service
public class ElevatorService implements ElevatorController {

    private static final int RETRY_TIME_TILL_AVAILABLE = 100;
    private final List<Elevator> elevators = Collections.synchronizedList(new ArrayList<>());
    private final int numberOfFloors;

    private static final Logger logger = LoggerFactory.getLogger(ElevatorService.class);

    public ElevatorService(@Value("${com.tingco.elevator.numberofelevators}") int numberOfElevators,
                           @Value("${com.tingco.elevator.numberoffloors}") int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
        logger.info("Boot up {} elevators", numberOfElevators);
        IntStream.rangeClosed(1, numberOfElevators).forEach(id -> elevators.add(new SimpleElevator(id, this.numberOfFloors)));
    }

    @Override
    public Elevator requestElevator(int toFloor) {
        logger.info("Elevator requested for floor {}", toFloor);
        Elevator nearest = checkElevatorAlreadyAddressed(toFloor);

        if(nearest == null) {
            List<Elevator> freeElevators = getElevators()
                    .stream()
                    .filter(e -> !e.isBusy()).collect(Collectors.toList());
            while(freeElevators.isEmpty()) {
                logger.debug("None of the elevators are available, Request to Floor {} has to wait.", toFloor);
                waitWhenAllBusy();
                freeElevators = elevators.stream().filter(elevator -> !elevator.isBusy()).collect(Collectors.toList());
            }

            int offset = this.numberOfFloors;
            // find the nearest elevator
            for (Elevator e : freeElevators) {
                int diff = abs(e.currentFloor() - toFloor);
                if (diff < offset) {
                    offset = diff;
                    nearest = e;
                }
            }

            //all elevators are available
            if (nearest == null) {
                nearest = freeElevators.get(0);
            }
            logger.info("Elevator available: {}", nearest.toString());
            return nearest;
        } else {
            logger.info("Elevator already addressed or present: {}", nearest.toString());
            return null;
        }
    }

    private Elevator checkElevatorAlreadyAddressed(int toFloor) {
        return getElevators().stream()
                .filter(elevator -> (!elevator.isBusy() && elevator.currentFloor() == toFloor)
                        || (elevator.isBusy() && elevator.getAddressedFloor() == toFloor))
                .findFirst()
                .orElse(null);
    }

    private void waitWhenAllBusy() {
        try {
            Thread.sleep(RETRY_TIME_TILL_AVAILABLE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Elevator> getElevators() {
        return elevators;
    }

    @Override
    public void releaseElevator(Elevator elevator) {
        elevator.moveElevator(elevator.currentFloor());
        logger.info("Released Elevator: {}", elevator.toString());
    }

    @Override
    public Integer getElevatorOnFloor(int floor) {
        return getElevators()
                .stream()
                .filter(elevator -> !elevator.isBusy())
                .filter(elevator -> elevator.currentFloor() == floor)
                .map(Elevator::getId)
                .findAny()
                .orElse(null);
    }

    @Override
    public Elevator getElevatorById(int elevatorId) {
        return getElevators()
                .stream()
                .filter(elevator -> !elevator.isBusy())
                .filter(elevator -> elevator.getId() == elevatorId)
                .findFirst()
                .orElse(null);
    }
}
