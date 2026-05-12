package com.codexdemo.orderplatform.notification;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/failed-events")
public class FailedEventReplayExecutionContractController {

    private final FailedEventReplayExecutionContractService executionContractService;

    public FailedEventReplayExecutionContractController(
            FailedEventReplayExecutionContractService executionContractService
    ) {
        this.executionContractService = executionContractService;
    }

    @GetMapping("/{id}/replay-execution-contract")
    public FailedEventReplayExecutionContractResponse replayExecutionContract(@PathVariable Long id) {
        return executionContractService.executionContract(id);
    }
}
