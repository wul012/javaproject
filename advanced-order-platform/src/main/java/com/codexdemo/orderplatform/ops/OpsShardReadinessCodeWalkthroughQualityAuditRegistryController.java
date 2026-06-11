package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessCodeWalkthroughQualityAuditRegistryController {

    private final OpsShardReadinessCodeWalkthroughQualityAuditRegistryService service;

    public OpsShardReadinessCodeWalkthroughQualityAuditRegistryController(
            OpsShardReadinessCodeWalkthroughQualityAuditRegistryService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_QUALITY_AUDIT_REGISTRY)
    public OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse registry() {
        return service.registry();
    }
}
