package com.tingco.codechallenge.elevator.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.tingco.codechallenge.elevator.api.ElevatorService;
import com.tingco.codechallenge.elevator.api.SimpleElevator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ElevatorEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ElevatorEventListener.class);

    private final ElevatorService service;

    private final ThreadPoolExecutor executor;

    public ElevatorEventListener(ElevatorService service, ThreadPoolExecutor executor) {
        this.service = service;
        this.executor = executor;
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEvent(ElevatorEvent event) {
        logger.info("Request from Elevator {} for floor {}", event.getElevatorId(), event.getRequestedFloorNumber());
        SimpleElevator elevator = (SimpleElevator) service.getElevatorById(event.getElevatorId());
        if (elevator != null && !elevator.isBusy()) {
            logger.info("Invoking elevator {}, passenger will get off at floor {} ", elevator.getId(), event.getRequestedFloorNumber());
            elevator.moveElevator(event.getRequestedFloorNumber());
            executor.execute(elevator);
        }

    }
}
