package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
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

    @GetMapping(OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG)
    public OpsShardReadinessReadOnlyEvidenceCatalogResponse readOnlyEvidenceCatalog() {
        return readOnlyEvidenceCatalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG_HANDOFF)
    public OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse readOnlyEvidenceCatalogHandoff() {
        return readOnlyEvidenceCatalogHandoffService.handoff();
    }

    @GetMapping(OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG_HANDOFF_VERIFICATION)
    public OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationResponse
            readOnlyEvidenceCatalogHandoffVerification() {
        return readOnlyEvidenceCatalogHandoffVerificationService.verification();
    }
}
