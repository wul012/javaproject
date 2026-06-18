package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1086";
  static final String SOURCE_ARTIFACT_PREFLIGHT_VERSION = "Java v759";
  static final String SOURCE_CAPTURE_PREFLIGHT_VERSION = "Node v1061";
  static final String SOURCE_TEMPLATE_VERSION = "Node v1036";
  static final String SOURCE_APPROVAL_PACKET_REVIEW_VERSION = "Node v1011";
  static final String READINESS_STATE = "boundary-ready";
  static final String ARTIFACT_DRAFT_STATE = "not-created";
  static final String ARTIFACT_MATERIALIZATION_STATE = "not-materialized";
  static final String SIGNED_APPROVAL_CAPTURE_STATE = "not-captured";
  static final String APPROVAL_GRANT_STATE = "not-emitted";
  static final String VALUE_IMPORT_STATE = "locked";
  static final String RUNTIME_STATE = "locked";
  static final String SIBLING_MUTATION_STATE = "locked";

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport() {}

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
      response(
          String version,
          String endpoint,
          String profile,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
                      .ReadinessItem>
              readinessItems,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
                      .OwnershipRule>
              ownershipRules,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
                      .DraftGate>
              gates,
          List<String> additionalChecks) {
    List<
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
                .ReadinessItem>
        itemCopy = List.copyOf(readinessItems);
    List<
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
                .OwnershipRule>
        ownershipCopy = List.copyOf(ownershipRules);
    List<
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
                .DraftGate>
        gateCopy = List.copyOf(gates);
    int passedItemCount =
        (int) itemCopy.stream().filter(item -> "passed".equals(item.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("signed-approval-artifact-draft-readiness-item-count-" + itemCopy.size());
    checks.add("signed-approval-artifact-draft-readiness-passed-item-count-" + passedItemCount);
    checks.add("signed-approval-artifact-draft-readiness-ownership-count-" + ownershipCopy.size());
    checks.add("signed-approval-artifact-draft-readiness-gate-count-" + gateCopy.size());
    checks.add("signed-approval-artifact-draft-readiness-source-plan-" + SOURCE_PLAN);
    checks.add(
        "signed-approval-artifact-draft-readiness-source-artifact-preflight-"
            + SOURCE_ARTIFACT_PREFLIGHT_VERSION);
    checks.add(
        "signed-approval-artifact-draft-readiness-source-capture-"
            + SOURCE_CAPTURE_PREFLIGHT_VERSION);
    checks.add("signed-approval-artifact-draft-readiness-no-manual-artifact-draft");
    checks.add("signed-approval-artifact-draft-readiness-no-artifact-materialization");
    checks.add("signed-approval-artifact-draft-readiness-no-signed-approval-capture");
    checks.add("signed-approval-artifact-draft-readiness-no-approval-grant");
    checks.add("signed-approval-artifact-draft-readiness-no-value-import");
    checks.add("signed-approval-artifact-draft-readiness-no-runtime-or-sibling-mutation");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_ARTIFACT_PREFLIGHT_VERSION,
        SOURCE_CAPTURE_PREFLIGHT_VERSION,
        SOURCE_TEMPLATE_VERSION,
        SOURCE_APPROVAL_PACKET_REVIEW_VERSION,
        READINESS_STATE,
        ARTIFACT_DRAFT_STATE,
        ARTIFACT_MATERIALIZATION_STATE,
        SIGNED_APPROVAL_CAPTURE_STATE,
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
        false,
        endpoint,
        profile,
        itemCopy.size(),
        passedItemCount,
        ownershipCopy.size(),
        gateCopy.size(),
        itemCopy,
        ownershipCopy,
        gateCopy,
        List.copyOf(checks),
        passedItemCount == itemCopy.size() ? "passed" : "blocked");
  }

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
          .ReadinessItem
      item(
          String code,
          String sourceArtifactFragment,
          String readinessStage,
          String readinessRequirement,
          String blockedReason,
          String ownershipCode,
          String evidenceFileId,
          String evidenceSnippetId,
          String sourceEndpoint) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
        .ReadinessItem(
        code,
        sourceArtifactFragment,
        readinessStage,
        readinessRequirement,
        blockedReason,
        ownershipCode,
        evidenceFileId,
        evidenceSnippetId,
        sourceEndpoint,
        "passed");
  }

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
          .OwnershipRule
      ownership(
          String code, String category, String owner, String responsibility, String enforcement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
        .OwnershipRule(code, category, owner, responsibility, enforcement);
  }

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
          .DraftGate
      gate(String code, String category, String gate, String enforcement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
        .DraftGate(code, category, gate, enforcement);
  }
}
