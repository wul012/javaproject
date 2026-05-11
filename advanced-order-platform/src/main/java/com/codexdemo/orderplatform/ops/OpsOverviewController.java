package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ops")
public class OpsOverviewController {

    private final OpsOverviewService opsOverviewService;

    public OpsOverviewController(OpsOverviewService opsOverviewService) {
        this.opsOverviewService = opsOverviewService;
    }

    @GetMapping("/overview")
    public OpsOverviewResponse overview() {
        return opsOverviewService.overview();
    }
}
