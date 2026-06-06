package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueDraftFoundationController {

    private final OpsShardReadinessOperatorEvidenceValueDraftCatalogService catalogService;

    public OpsShardReadinessOperatorEvidenceValueDraftFoundationController(
            OpsShardReadinessOperatorEvidenceValueDraftCatalogService catalogService
    ) {
        this.catalogService = catalogService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CATALOG)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse catalog() {
        return catalogService.catalog();
    }
}
