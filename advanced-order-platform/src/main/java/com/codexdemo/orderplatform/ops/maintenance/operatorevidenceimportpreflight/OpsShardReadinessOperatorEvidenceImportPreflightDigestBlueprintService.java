package com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight;

import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetCloseoutService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService {
  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
              .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_DIGEST_BLUEPRINT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-import-preflight-digest-blueprint.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse blueprint() {
    return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
        "Java v597",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "slot-count-digest",
                "import-preflight-maintainer",
                "digest records only the expected twenty-five slot count",
                OpsShardReadinessOperatorEvidenceImportPreflightCatalogService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "blocker-count-digest",
                "runtime-boundary-reviewer",
                "digest records blocker categories without runtime payloads",
                OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService
                    .ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "lock-flag-digest",
                "release-reviewer",
                "digest records import and execution locks as booleans",
                OpsShardReadinessManualEvidenceWorksheetCloseoutService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "source-plan-digest",
                "release-reviewer",
                "digest pins Node v886 as source plan without fresh Node evidence",
                OpsShardReadinessManualEvidenceWorksheetCloseoutService.ENDPOINT)),
        List.of(
            "digest-blueprint-does-not-hash-values",
            "digest-blueprint-covers-lock-flags",
            "digest-blueprint-ready-for-route"));
  }
}
