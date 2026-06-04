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

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_ENDPOINT_INVENTORY)
    public OpsShardReadinessPrototypeHandoffEvidenceResponse endpointInventory() {
        return handoffService.endpointInventory();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_BOUNDARY_MATRIX)
    public OpsShardReadinessPrototypeHandoffEvidenceResponse boundaryMatrix() {
        return handoffService.boundaryMatrix();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_CONSUMER_VERIFICATION_CHECKLIST)
    public OpsShardReadinessPrototypeHandoffEvidenceResponse consumerVerificationChecklist() {
        return handoffService.consumerVerificationChecklist();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_READ_WINDOW_CHECKLIST)
    public OpsShardReadinessPrototypeHandoffEvidenceResponse readWindowChecklist() {
        return handoffService.readWindowChecklist();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_DIGEST_MANIFEST)
    public OpsShardReadinessPrototypeHandoffEvidenceResponse digestManifest() {
        return handoffService.digestManifest();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_CI_MANIFEST)
    public OpsShardReadinessPrototypeHandoffEvidenceResponse ciManifest() {
        return handoffService.ciManifest();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_ARCHIVE_MANIFEST)
    public OpsShardReadinessPrototypeHandoffEvidenceResponse archiveManifest() {
        return handoffService.archiveManifest();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_OPERATOR_SIGNOFF_PACKET)
    public OpsShardReadinessPrototypeHandoffEvidenceResponse operatorSignoffPacket() {
        return handoffService.operatorSignoffPacket();
    }

    @GetMapping(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_CLOSEOUT)
    public OpsShardReadinessPrototypeHandoffEvidenceResponse closeout() {
        return handoffService.closeout();
    }
}
