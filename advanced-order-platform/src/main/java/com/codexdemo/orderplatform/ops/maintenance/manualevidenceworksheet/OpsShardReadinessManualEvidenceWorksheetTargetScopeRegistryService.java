package com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionArtifactCandidateService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionLiveReadGateService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService {
  public static final String ENDPOINT =
      OpsShardReadinessManualEvidenceWorksheetRoutePaths.BASE_PATH
          + OpsShardReadinessManualEvidenceWorksheetRoutePaths
              .MANUAL_EVIDENCE_WORKSHEET_TARGET_SCOPE_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-manual-evidence-worksheet-target-scope-registry.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessManualEvidenceWorksheetResponse registry() {
    return OpsShardReadinessManualEvidenceWorksheetSupport.response(
        "Java v570",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "order-read-model",
                "target-scope-maintainer",
                "scope may describe read model evidence, not write commands",
                OpsShardReadinessRuntimeExecutionArtifactCandidateService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "shard-preview-window",
                "runtime-boundary-reviewer",
                "scope stays preview-only and does not activate routing",
                OpsShardReadinessRuntimeExecutionLiveReadGateService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "evidence-review-package",
                "operator-worksheet-maintainer",
                "scope can reference review package sections without importing values",
                OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "operator-entry-slot",
                "operator-worksheet-maintainer",
                "scope maps a blank slot to a future manual-entry field",
                OpsShardReadinessManualEvidenceWorksheetSlotTemplateService.ENDPOINT)),
        List.of(
            "target-scope-registry-scope-count-4",
            "target-scope-registry-no-write-routing",
            "target-scope-registry-no-active-shard-router"));
  }
}
