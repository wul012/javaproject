package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessPrototypeConsumerGateController {

    private final OpsShardReadinessPrototypeConsumerGateService consumerGateService;

    public OpsShardReadinessPrototypeConsumerGateController(
            OpsShardReadinessPrototypeConsumerGateService consumerGateService
    ) {
        this.consumerGateService = consumerGateService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_CATALOG)
    public OpsShardReadinessPrototypeConsumerGateCatalogResponse catalog() {
        return consumerGateService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_SOURCE_INVENTORY)
    public OpsShardReadinessPrototypeConsumerGateEvidenceResponse sourceInventory() {
        return consumerGateService.sourceInventory();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_MINIMAL_FIELD_CHECKLIST)
    public OpsShardReadinessPrototypeConsumerGateEvidenceResponse minimalFieldChecklist() {
        return consumerGateService.minimalFieldChecklist();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_ROUTE_TOPOLOGY_PREVIEW)
    public OpsShardReadinessPrototypeConsumerGateEvidenceResponse routeTopologyPreview() {
        return consumerGateService.routeTopologyPreview();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_BOUNDARY_MATRIX)
    public OpsShardReadinessPrototypeConsumerGateEvidenceResponse boundaryMatrix() {
        return consumerGateService.boundaryMatrix();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_DIGEST_ACCEPTANCE)
    public OpsShardReadinessPrototypeConsumerGateEvidenceResponse digestAcceptance() {
        return consumerGateService.digestAcceptance();
    }
}
