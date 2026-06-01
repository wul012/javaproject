package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ops/shard-readiness")
public class OpsShardReadinessReadOnlyEvidenceController {

    private final OpsShardReadinessReadOnlyEvidenceCatalogService readOnlyEvidenceCatalogService;

    private final OpsShardReadinessReadOnlyEvidenceCatalogHandoffService readOnlyEvidenceCatalogHandoffService;

    private final OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService
            readOnlyEvidenceCatalogHandoffVerificationService;

    public OpsShardReadinessReadOnlyEvidenceController(
            OpsShardReadinessReadOnlyEvidenceCatalogService readOnlyEvidenceCatalogService,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffService readOnlyEvidenceCatalogHandoffService,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService
                    readOnlyEvidenceCatalogHandoffVerificationService
    ) {
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
}
