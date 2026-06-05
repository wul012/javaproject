package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceImportPreflightAssuranceController {

    private final OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService digestBlueprintService;

    public OpsShardReadinessOperatorEvidenceImportPreflightAssuranceController(
            OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService digestBlueprintService
    ) {
        this.digestBlueprintService = digestBlueprintService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_DIGEST_BLUEPRINT)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse digestBlueprint() {
        return digestBlueprintService.blueprint();
    }
}
