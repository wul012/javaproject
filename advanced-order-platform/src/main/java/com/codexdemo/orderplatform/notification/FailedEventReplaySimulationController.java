package com.codexdemo.orderplatform.notification;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/failed-events")
public class FailedEventReplaySimulationController {

    private final FailedEventReplaySimulationService failedEventReplaySimulationService;

    public FailedEventReplaySimulationController(
            FailedEventReplaySimulationService failedEventReplaySimulationService
    ) {
        this.failedEventReplaySimulationService = failedEventReplaySimulationService;
    }

    @GetMapping("/{id}/replay-simulation")
    public FailedEventReplaySimulationResponse replaySimulation(@PathVariable Long id) {
        return failedEventReplaySimulationService.simulation(id);
    }
}
