package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ops")
public class OpsOverviewController {

    private final OpsOverviewService opsOverviewService;

    private final OpsEvidenceService opsEvidenceService;

    public OpsOverviewController(OpsOverviewService opsOverviewService, OpsEvidenceService opsEvidenceService) {
        this.opsOverviewService = opsOverviewService;
        this.opsEvidenceService = opsEvidenceService;
    }

    @GetMapping("/overview")
    public OpsOverviewResponse overview() {
        return opsOverviewService.overview();
    }

    @GetMapping("/evidence")
    public OpsEvidenceResponse evidence() {
        return opsEvidenceService.evidence();
    }
}
