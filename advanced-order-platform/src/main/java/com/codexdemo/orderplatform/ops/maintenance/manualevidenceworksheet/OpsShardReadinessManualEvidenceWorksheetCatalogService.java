package com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionLiveReadGateService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetCatalogService {
  public static final String ENDPOINT =
      OpsShardReadinessManualEvidenceWorksheetRoutePaths.BASE_PATH
          + OpsShardReadinessManualEvidenceWorksheetRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CATALOG;
  static final String PROFILE = "java-shard-readiness-manual-evidence-worksheet-catalog.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessManualEvidenceWorksheetResponse catalog() {
    return OpsShardReadinessManualEvidenceWorksheetSupport.response(
        "Java v560",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "source-review-package",
                "worksheet-maintainer",
                "Node v861 consumes the v836 review package, Java mirrors only the readiness boundary",
                OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "blank-slot-count",
                "operator-worksheet-maintainer",
                "twenty-five blank operator-entry slots are expected",
                OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService
                    .ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "gate-count",
                "runtime-boundary-reviewer",
                "twenty-one gates remain worksheet-only",
                OpsShardReadinessRuntimeExecutionLiveReadGateService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "fail-closed-flags",
                "release-reviewer",
                "manual evidence, live execution, and production execution stay locked",
                OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT)),
        List.of(
            "worksheet-catalog-slot-count-25",
            "worksheet-catalog-gate-count-21",
            "worksheet-catalog-ready-for-operator-entry-only"));
  }
}
