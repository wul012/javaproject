package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceImportPreflightFoundationController {

    private final OpsShardReadinessOperatorEvidenceImportPreflightCatalogService catalogService;

    public OpsShardReadinessOperatorEvidenceImportPreflightFoundationController(
            OpsShardReadinessOperatorEvidenceImportPreflightCatalogService catalogService
    ) {
        this.catalogService = catalogService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CATALOG)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse catalog() {
        return catalogService.catalog();
    }
}
