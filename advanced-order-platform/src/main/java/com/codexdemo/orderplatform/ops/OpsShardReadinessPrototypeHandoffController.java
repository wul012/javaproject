package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessPrototypeHandoffController {

    private final OpsShardReadinessPrototypeHandoffService handoffService;

    public OpsShardReadinessPrototypeHandoffController(
            OpsShardReadinessPrototypeHandoffService handoffService
    ) {
        this.handoffService = handoffService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_CATALOG)
    public OpsShardReadinessPrototypeHandoffCatalogResponse catalog() {
        return handoffService.catalog();
    }
}
