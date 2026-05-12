package com.codexdemo.orderplatform.notification;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/failed-events")
public class FailedEventReplayEvidenceIndexController {

    private final FailedEventReplayEvidenceIndexService failedEventReplayEvidenceIndexService;

    public FailedEventReplayEvidenceIndexController(
            FailedEventReplayEvidenceIndexService failedEventReplayEvidenceIndexService
    ) {
        this.failedEventReplayEvidenceIndexService = failedEventReplayEvidenceIndexService;
    }

    @GetMapping("/replay-evidence-index")
    public FailedEventReplayEvidenceIndexResponse index() {
        return failedEventReplayEvidenceIndexService.index();
    }
}
