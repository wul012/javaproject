package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ops")
public class OpsShardReadinessController {

    private final OpsShardReadinessService opsShardReadinessService;

    private final OpsShardReadinessHardeningService opsShardReadinessHardeningService;

    private final OpsShardReadinessEvidenceIndexService opsShardReadinessEvidenceIndexService;

    private final OpsShardReadinessEvidenceVerificationService opsShardReadinessEvidenceVerificationService;

    private final OpsShardReadinessEvidenceHandoffService opsShardReadinessEvidenceHandoffService;

    public OpsShardReadinessController(
            OpsShardReadinessService opsShardReadinessService,
            OpsShardReadinessHardeningService opsShardReadinessHardeningService,
            OpsShardReadinessEvidenceIndexService opsShardReadinessEvidenceIndexService,
            OpsShardReadinessEvidenceVerificationService opsShardReadinessEvidenceVerificationService,
            OpsShardReadinessEvidenceHandoffService opsShardReadinessEvidenceHandoffService
    ) {
        this.opsShardReadinessService = opsShardReadinessService;
        this.opsShardReadinessHardeningService = opsShardReadinessHardeningService;
        this.opsShardReadinessEvidenceIndexService = opsShardReadinessEvidenceIndexService;
        this.opsShardReadinessEvidenceVerificationService = opsShardReadinessEvidenceVerificationService;
        this.opsShardReadinessEvidenceHandoffService = opsShardReadinessEvidenceHandoffService;
    }

    @GetMapping("/shard-readiness")
    public OpsShardReadinessResponse shardReadiness() {
        return opsShardReadinessService.readiness();
    }

    @GetMapping("/shard-readiness/hardening")
    public OpsShardReadinessHardeningResponse shardReadinessHardening() {
        return opsShardReadinessHardeningService.hardening();
    }

    @GetMapping("/shard-readiness/evidence-index")
    public OpsShardReadinessEvidenceIndexResponse shardReadinessEvidenceIndex() {
        return opsShardReadinessEvidenceIndexService.evidenceIndex();
    }

    @GetMapping("/shard-readiness/evidence-verification")
    public OpsShardReadinessEvidenceVerificationResponse shardReadinessEvidenceVerification() {
        return opsShardReadinessEvidenceVerificationService.verification();
    }

    @GetMapping("/shard-readiness/evidence-handoff")
    public OpsShardReadinessEvidenceHandoffResponse shardReadinessEvidenceHandoff() {
        return opsShardReadinessEvidenceHandoffService.handoff();
    }
}
