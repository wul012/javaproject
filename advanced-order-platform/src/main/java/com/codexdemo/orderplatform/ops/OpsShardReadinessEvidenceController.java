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

    private final OpsShardReadinessReadOnlyEvidenceCatalogHandoffService readOnlyEvidenceCatalogHandoffService;

    private final OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService
            readOnlyEvidenceCatalogHandoffVerificationService;

    public OpsShardReadinessEvidenceController(
            OpsShardReadinessEvidenceIndexService evidenceIndexService,
            OpsShardReadinessEvidenceVerificationService evidenceVerificationService,
            OpsShardReadinessEvidenceHandoffService evidenceHandoffService,
            OpsShardReadinessReadOnlyEvidenceCatalogService readOnlyEvidenceCatalogService,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffService readOnlyEvidenceCatalogHandoffService,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService
                    readOnlyEvidenceCatalogHandoffVerificationService
    ) {
        this.evidenceIndexService = evidenceIndexService;
        this.evidenceVerificationService = evidenceVerificationService;
        this.evidenceHandoffService = evidenceHandoffService;
        this.readOnlyEvidenceCatalogService = readOnlyEvidenceCatalogService;
        this.readOnlyEvidenceCatalogHandoffService = readOnlyEvidenceCatalogHandoffService;
        this.readOnlyEvidenceCatalogHandoffVerificationService = readOnlyEvidenceCatalogHandoffVerificationService;
    }

    @GetMapping("/read-only-evidence-catalog")
    public OpsShardReadinessReadOnlyEvidenceCatalogResponse readOnlyEvidenceCatalog() {
        return readOnlyEvidenceCatalogService.catalog();
    }

    @GetMapping("/read-only-evidence-catalog-handoff")
    public OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse readOnlyEvidenceCatalogHandoff() {
        return readOnlyEvidenceCatalogHandoffService.handoff();
    }

    @GetMapping("/read-only-evidence-catalog-handoff-verification")
    public OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationResponse
            readOnlyEvidenceCatalogHandoffVerification() {
        return readOnlyEvidenceCatalogHandoffVerificationService.verification();
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
