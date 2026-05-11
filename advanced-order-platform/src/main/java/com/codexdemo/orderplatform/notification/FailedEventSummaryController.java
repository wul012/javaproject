package com.codexdemo.orderplatform.notification;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/failed-events")
public class FailedEventSummaryController {

    private final FailedEventSummaryService failedEventSummaryService;

    public FailedEventSummaryController(FailedEventSummaryService failedEventSummaryService) {
        this.failedEventSummaryService = failedEventSummaryService;
    }

    @GetMapping("/summary")
    public FailedEventSummaryResponse summary() {
        return failedEventSummaryService.summary();
    }
}
