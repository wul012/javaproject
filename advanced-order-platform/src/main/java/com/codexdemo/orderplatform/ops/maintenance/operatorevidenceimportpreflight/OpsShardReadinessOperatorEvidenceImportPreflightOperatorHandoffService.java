package com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight;

import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightOperatorHandoffService {
  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
              .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_OPERATOR_HANDOFF;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-import-preflight-operator-handoff.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse handoff() {
    return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
        "Java v603",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "preflight-owner",
                "import-preflight-maintainer",
                "owns the import preflight catalog and source plan alignment",
                OpsShardReadinessOperatorEvidenceImportPreflightCatalogService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "normalization-owner",
                "operator-reviewer",
                "owns blank slot normalization without submitted values",
                OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "blocker-owner",
                "runtime-boundary-reviewer",
                "owns fail-closed blocker matrix and runtime payload rejection",
                OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService
                    .ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "archive-owner",
                "archive-reviewer",
                "owns external evidence capture without service-side writes",
                OpsShardReadinessOperatorEvidenceImportPreflightArchivePlanService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "runtime-boundary-owner",
                "release-reviewer",
                "owns final assurance that execution approvals remain locked",
                OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService.ENDPOINT)),
        List.of(
            "import-preflight-operator-handoff-owner-count-5",
            "import-preflight-operator-handoff-no-values",
            "import-preflight-operator-handoff-no-execution-approval"));
  }
}
