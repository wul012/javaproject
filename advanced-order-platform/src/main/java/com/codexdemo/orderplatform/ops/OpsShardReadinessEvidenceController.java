package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ops/shard-readiness")
public class OpsShardReadinessEvidenceController {

    private final OpsShardReadinessEvidenceIndexService evidenceIndexService;

    private final OpsShardReadinessEvidenceVerificationService evidenceVerificationService;

    private final OpsShardReadinessEvidenceHandoffService evidenceHandoffService;

    private final OpsShardReadinessReadOnlyEvidenceCatalogService readOnlyEvidenceCatalogService;

    public OpsShardReadinessEvidenceController(
            OpsShardReadinessEvidenceIndexService evidenceIndexService,
            OpsShardReadinessEvidenceVerificationService evidenceVerificationService,
            OpsShardReadinessEvidenceHandoffService evidenceHandoffService,
            OpsShardReadinessReadOnlyEvidenceCatalogService readOnlyEvidenceCatalogService
    ) {
        this.evidenceIndexService = evidenceIndexService;
        this.evidenceVerificationService = evidenceVerificationService;
        this.evidenceHandoffService = evidenceHandoffService;
        this.readOnlyEvidenceCatalogService = readOnlyEvidenceCatalogService;
    }

    @GetMapping("/read-only-evidence-catalog")
    public OpsShardReadinessReadOnlyEvidenceCatalogResponse readOnlyEvidenceCatalog() {
        return readOnlyEvidenceCatalogService.catalog();
    }

    @GetMapping("/evidence-index")
    public OpsShardReadinessEvidenceIndexResponse evidenceIndex() {
        return evidenceIndexService.evidenceIndex();
    }

    @GetMapping("/evidence-verification")
    public OpsShardReadinessEvidenceVerificationResponse evidenceVerification() {
        return evidenceVerificationService.verification();
    }

    @GetMapping("/evidence-handoff")
    public OpsShardReadinessEvidenceHandoffResponse evidenceHandoff() {
        return evidenceHandoffService.handoff();
    }
}
