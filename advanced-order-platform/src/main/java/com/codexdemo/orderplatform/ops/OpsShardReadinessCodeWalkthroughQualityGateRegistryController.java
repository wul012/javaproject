package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessCodeWalkthroughQualityGateRegistryController {

    private final OpsShardReadinessCodeWalkthroughQualityGateRegistryService service;

    public OpsShardReadinessCodeWalkthroughQualityGateRegistryController(
            OpsShardReadinessCodeWalkthroughQualityGateRegistryService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_QUALITY_GATE_REGISTRY)
    public OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse registry() {
        return service.registry();
    }
}
