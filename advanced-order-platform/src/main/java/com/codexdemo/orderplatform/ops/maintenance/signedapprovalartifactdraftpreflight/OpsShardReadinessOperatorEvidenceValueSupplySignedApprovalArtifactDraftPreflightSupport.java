package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1111";
  static final String SOURCE_ARTIFACT_PREFLIGHT_VERSION = "Node v1086";
  static final String SOURCE_JAVA_DRAFT_READINESS_VERSION = "Java v784";
  static final String SOURCE_CAPTURE_PREFLIGHT_VERSION = "Node v1061";
  static final String DRAFT_PREFLIGHT_STATE = "field-map-only";
  static final String MANUAL_DRAFT_STATE = "not-created";
  static final String DRAFT_MATERIALIZATION_STATE = "not-materialized";
  static final String SIGNATURE_CAPTURE_STATE = "not-captured";
  static final String APPROVAL_GRANT_STATE = "not-emitted";
  static final String VALUE_IMPORT_STATE = "locked";
  static final String RUNTIME_STATE = "locked";
  static final String SIBLING_MUTATION_STATE = "locked";

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport() {}

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      response(
          String version,
          String endpoint,
          String profile,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
                      .DraftField>
              fields,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
                      .DraftGuard>
              guards,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
                      .DraftPreflightGate>
              gates,
          List<String> additionalChecks) {
    var fieldCopy = List.copyOf(fields);
    var guardCopy = List.copyOf(guards);
    var gateCopy = List.copyOf(gates);
    int passedFieldCount =
        (int) fieldCopy.stream().filter(field -> "passed".equals(field.status())).count();
    int passedGuardCount =
        (int) guardCopy.stream().filter(guard -> "passed".equals(guard.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("signed-approval-artifact-draft-preflight-field-count-" + fieldCopy.size());
    checks.add("signed-approval-artifact-draft-preflight-passed-field-count-" + passedFieldCount);
    checks.add("signed-approval-artifact-draft-preflight-guard-count-" + guardCopy.size());
    checks.add("signed-approval-artifact-draft-preflight-passed-guard-count-" + passedGuardCount);
    checks.add("signed-approval-artifact-draft-preflight-gate-count-" + gateCopy.size());
    checks.add("signed-approval-artifact-draft-preflight-source-plan-" + SOURCE_PLAN);
    checks.add(
        "signed-approval-artifact-draft-preflight-source-artifact-preflight-"
            + SOURCE_ARTIFACT_PREFLIGHT_VERSION);
    checks.add(
        "signed-approval-artifact-draft-preflight-source-java-readiness-"
            + SOURCE_JAVA_DRAFT_READINESS_VERSION);
    checks.add("signed-approval-artifact-draft-preflight-no-real-manual-draft");
    checks.add("signed-approval-artifact-draft-preflight-no-draft-materialization");
    checks.add("signed-approval-artifact-draft-preflight-no-signature-capture");
    checks.add("signed-approval-artifact-draft-preflight-no-approval-grant");
    checks.add("signed-approval-artifact-draft-preflight-no-value-import");
    checks.add("signed-approval-artifact-draft-preflight-no-runtime-or-sibling-mutation");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_ARTIFACT_PREFLIGHT_VERSION,
        SOURCE_JAVA_DRAFT_READINESS_VERSION,
        SOURCE_CAPTURE_PREFLIGHT_VERSION,
        DRAFT_PREFLIGHT_STATE,
        MANUAL_DRAFT_STATE,
        DRAFT_MATERIALIZATION_STATE,
        SIGNATURE_CAPTURE_STATE,
        APPROVAL_GRANT_STATE,
        VALUE_IMPORT_STATE,
        RUNTIME_STATE,
        SIBLING_MUTATION_STATE,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        fieldCopy.size(),
        passedFieldCount,
        guardCopy.size(),
        passedGuardCount,
        gateCopy.size(),
        fieldCopy,
        guardCopy,
        gateCopy,
        List.copyOf(checks),
        passedFieldCount == fieldCopy.size() && passedGuardCount == guardCopy.size()
            ? "passed"
            : "blocked");
  }

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
          .DraftField
      field(
          String code,
          String sourceReadinessItem,
          String draftStage,
          String fieldRequirement,
          String materializationBlocker,
          String guardCode,
          String sourceEndpoint) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
        .DraftField(
        code,
        sourceReadinessItem,
        draftStage,
        fieldRequirement,
        materializationBlocker,
        guardCode,
        sourceEndpoint,
        "passed");
  }

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
          .DraftGuard
      guard(
          String code,
          String category,
          String guardRequirement,
          String rejectionCode,
          String enforcement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
        .DraftGuard(code, category, guardRequirement, rejectionCode, enforcement, "passed");
  }

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
          .DraftPreflightGate
      gate(String code, String category, String gate, String enforcement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
        .DraftPreflightGate(code, category, gate, enforcement);
  }
}
