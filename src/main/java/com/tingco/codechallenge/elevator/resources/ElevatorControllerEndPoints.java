package com.tingco.codechallenge.elevator.resources;

import com.google.common.eventbus.EventBus;
import com.tingco.codechallenge.elevator.api.ElevatorService;
import com.tingco.codechallenge.elevator.config.Config;
import com.tingco.codechallenge.elevator.event.ElevatorEvent;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

/**
 * Rest Resource.
 *
 * @author Sven Wesley
 *
 */
@RestController
@RequestMapping("/rest/v1")
public final class ElevatorControllerEndPoints {

    private final EventBus requests;

    private final ElevatorService service;

    private final Config config;

    public ElevatorControllerEndPoints(Config config, ElevatorService service, EventBus requests) {
        this.requests = requests;
        this.service = service;
        this.config = config;
    }
    /**
     * Ping service to test if we are alive.
     *
     * @return String pong
     */
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {

        return "pong";
    }

    @GetMapping(value = "/requestElevator/floor/{floorNumber}")
    public Integer findElevatorAvailableOnFloor(@NotNull @PathVariable Integer floorNumber) {
        validateFloorNumber(floorNumber);
        return service.getElevatorOnFloor(floorNumber);
    }

    @PutMapping(value = "/requestElevator/elevator/{elevatorId}/floor/{floorNumber}")
    public void requestElevator(@NotNull @PathVariable Integer elevatorId,
                                @NotNull @PathVariable Integer floorNumber) {
        validateFloorNumber(floorNumber);
        validateElevatorId(elevatorId);
        requests.post(new ElevatorEvent(elevatorId, floorNumber));
    }

    private void validateFloorNumber(@PathVariable @NotNull Integer floorNumber) {
        if(floorNumber < 0 || floorNumber > config.getNumberOfFloors()) {
            throw new ValidationException("Invalid floor number: " + floorNumber);
        }
    }

    private void validateElevatorId(@PathVariable @NotNull Integer elevatorId) {
        if(elevatorId < 0 || elevatorId > config.getNumberOfElevators()) {
            throw new ValidationException("Invalid elevator id: " + elevatorId);
        }
    }
}
