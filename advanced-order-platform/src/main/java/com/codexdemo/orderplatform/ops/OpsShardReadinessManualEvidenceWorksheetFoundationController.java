package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessManualEvidenceWorksheetFoundationController {

    private final OpsShardReadinessManualEvidenceWorksheetCatalogService catalogService;

    public OpsShardReadinessManualEvidenceWorksheetFoundationController(
            OpsShardReadinessManualEvidenceWorksheetCatalogService catalogService
    ) {
        this.catalogService = catalogService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CATALOG)
    public OpsShardReadinessManualEvidenceWorksheetResponse catalog() {
        return catalogService.catalog();
    }
}
