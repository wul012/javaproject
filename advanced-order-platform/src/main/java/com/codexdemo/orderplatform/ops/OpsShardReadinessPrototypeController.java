package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessPrototypeController {

    private final OpsShardReadinessPrototypeEvidenceService prototypeEvidenceService;

    public OpsShardReadinessPrototypeController(
            OpsShardReadinessPrototypeEvidenceService prototypeEvidenceService
    ) {
        this.prototypeEvidenceService = prototypeEvidenceService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CATALOG)
    public OpsShardReadinessPrototypeCatalogResponse catalog() {
        return prototypeEvidenceService.catalog();
    }
}
