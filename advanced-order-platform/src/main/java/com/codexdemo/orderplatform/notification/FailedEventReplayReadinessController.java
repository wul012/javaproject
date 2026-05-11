package com.codexdemo.orderplatform.notification;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/failed-events")
public class FailedEventReplayReadinessController {

    private final FailedEventReplayReadinessService failedEventReplayReadinessService;

    public FailedEventReplayReadinessController(
            FailedEventReplayReadinessService failedEventReplayReadinessService
    ) {
        this.failedEventReplayReadinessService = failedEventReplayReadinessService;
    }

    @GetMapping("/{id}/replay-readiness")
    public FailedEventReplayReadinessResponse replayReadiness(@PathVariable Long id) {
        return failedEventReplayReadinessService.readiness(id);
    }
}
