package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightArchivePlanService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ARCHIVE_PLAN;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-import-preflight-archive-plan.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse plan() {
        return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
                "Java v601",
                ENDPOINT,
                PROFILE,
                List.of(
                        OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                                "json-capture-plan",
                                "archive-reviewer",
                                "operators may capture route JSON externally",
                                OpsShardReadinessOperatorEvidenceImportPreflightRouteProfileSummaryService.ENDPOINT
                        ),
                        OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                                "digest-blueprint-plan",
                                "archive-reviewer",
                                "digest blueprint is archived without evidence values",
                                OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService.ENDPOINT
                        ),
                        OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                                "route-output-plan",
                                "consumer-contract-reviewer",
                                "route output remains the source of archive evidence",
                                OpsShardReadinessOperatorEvidenceImportPreflightCatalogService.ENDPOINT
                        ),
                        OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                                "no-file-write",
                                "runtime-boundary-reviewer",
                                "service performs no file writes and starts no process",
                                OpsShardReadinessManualEvidenceWorksheetArchivePlanService.ENDPOINT
                        )
                ),
                List.of(
                        "import-preflight-archive-plan-external-capture",
                        "import-preflight-archive-plan-no-file-write",
                        "import-preflight-archive-plan-ready"
                )
        );
    }
}
