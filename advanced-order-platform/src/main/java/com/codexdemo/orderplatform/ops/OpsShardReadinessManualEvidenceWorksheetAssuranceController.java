package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessManualEvidenceWorksheetAssuranceController {

    private final OpsShardReadinessManualEvidenceWorksheetImporterPreflightService importerPreflightService;

    public OpsShardReadinessManualEvidenceWorksheetAssuranceController(
            OpsShardReadinessManualEvidenceWorksheetImporterPreflightService importerPreflightService
    ) {
        this.importerPreflightService = importerPreflightService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_IMPORTER_PREFLIGHT)
    public OpsShardReadinessManualEvidenceWorksheetResponse importerPreflight() {
        return importerPreflightService.preflight();
    }
}
