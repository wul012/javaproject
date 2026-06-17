package com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightCloseoutService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
              .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CLOSEOUT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-import-preflight-closeout.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse closeout() {
    return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
        "Java v607",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "foundation-complete",
                "import-preflight-maintainer",
                "foundation routes cover catalog through target scope mapping",
                OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "assurance-complete",
                "release-reviewer",
                "assurance routes cover digest through closeout",
                OpsShardReadinessOperatorEvidenceImportPreflightCiBudgetService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "locks-held",
                "runtime-boundary-reviewer",
                "evidence import, manual entry, live execution, and production execution remain locked",
                OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService
                    .ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "node-v886-alignment",
                "release-reviewer",
                "Java closeout aligns to Node v886 controlled read-only import preflight",
                OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "ci-gate-ready",
                "ci-reviewer",
                "focused gates and full Maven gate are ready for release verification",
                OpsShardReadinessOperatorEvidenceImportPreflightCiBudgetService.ENDPOINT)),
        List.of(
            "import-preflight-closeout-versions-v584-v608",
            "import-preflight-closeout-foundation-and-assurance-split",
            "import-preflight-closeout-import-remains-locked"));
  }
}
