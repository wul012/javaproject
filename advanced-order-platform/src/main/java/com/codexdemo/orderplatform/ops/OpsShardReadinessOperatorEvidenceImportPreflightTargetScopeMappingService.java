package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_TARGET_SCOPE_MAPPING;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-import-preflight-target-scope-mapping.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse mapping() {
    return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
        "Java v595",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "read-model-scope",
                "target-scope-reviewer",
                "order read model scope is preview-only",
                OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "preview-window-scope",
                "runtime-boundary-reviewer",
                "preview windows do not open live execution",
                OpsShardReadinessRuntimeExecutionLiveReadGateService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "review-package-scope",
                "release-reviewer",
                "review package scope stays documentation-only",
                OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "operator-slot-scope",
                "operator-reviewer",
                "operator slot scope accepts no submitted value",
                OpsShardReadinessManualEvidenceWorksheetSlotTemplateService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "unmapped-scope-rejection",
                "target-scope-reviewer",
                "unmapped scopes are rejected before import",
                OpsShardReadinessManualEvidenceWorksheetValidationRulesService.ENDPOINT)),
        List.of(
            "target-scope-mapping-scope-count-5",
            "target-scope-mapping-no-write-routing",
            "target-scope-mapping-no-active-router"));
  }
}
