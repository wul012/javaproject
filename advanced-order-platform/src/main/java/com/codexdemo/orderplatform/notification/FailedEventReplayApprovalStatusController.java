package com.codexdemo.orderplatform.notification;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/failed-events")
public class FailedEventReplayApprovalStatusController {

    private final FailedEventReplayApprovalStatusService failedEventReplayApprovalStatusService;

    public FailedEventReplayApprovalStatusController(
            FailedEventReplayApprovalStatusService failedEventReplayApprovalStatusService
    ) {
        this.failedEventReplayApprovalStatusService = failedEventReplayApprovalStatusService;
    }

    @GetMapping("/{id}/approval-status")
    public FailedEventReplayApprovalStatusResponse approvalStatus(@PathVariable Long id) {
        return failedEventReplayApprovalStatusService.approvalStatus(id);
    }
}
