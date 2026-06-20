package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightDigestSignatureService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightEvidenceValuePolicyService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightExecutionCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightIdentityRequestService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCheckpointCatalog {

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCheckpointCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
              .AcceptanceCheckpoint>
      allCheckpoints() {
    return List.of(
        checkpoint(
            "comparison-acceptance-precheck-source-readiness",
            "v1312",
            "source comparison preflight readiness checkpoint",
            "Is Java v1004 comparison preflight available as read-only evidence?",
            "reject-missing-comparison-preflight-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogService
                .ENDPOINT),
        checkpoint(
            "comparison-acceptance-precheck-identity-request",
            "v1313",
            "identity and request metadata acceptance checkpoint",
            "Are identity and request comparison lanes present without accepting material?",
            "reject-missing-identity-request-comparison",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightIdentityRequestService
                .ENDPOINT),
        checkpoint(
            "comparison-acceptance-precheck-digest-binding",
            "v1314",
            "digest binding and digest recheck acceptance checkpoint",
            "Are digest comparison lanes present without hashing submitted text?",
            "reject-missing-digest-binding-comparison",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightDigestSignatureService
                .ENDPOINT),
        checkpoint(
            "comparison-acceptance-precheck-detached-signature",
            "v1315",
            "detached signature envelope acceptance checkpoint",
            "Is detached signature metadata comparable without parsing signature payload?",
            "reject-missing-detached-signature-comparison",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightDigestSignatureService
                .ENDPOINT),
        checkpoint(
            "comparison-acceptance-precheck-source-evidence",
            "v1316",
            "source evidence handle acceptance checkpoint",
            "Are source evidence handles comparable without importing evidence?",
            "reject-missing-source-evidence-comparison",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightEvidenceValuePolicyService
                .ENDPOINT),
        checkpoint(
            "comparison-acceptance-precheck-operator-value",
            "v1317",
            "operator value handle acceptance checkpoint",
            "Are operator value handles comparable without capturing credentials or values?",
            "reject-missing-operator-value-comparison",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightEvidenceValuePolicyService
                .ENDPOINT),
        checkpoint(
            "comparison-acceptance-precheck-policy-review",
            "v1318",
            "policy and review-state acceptance checkpoint",
            "Are policy and review-state lanes present without emitting approval grant?",
            "reject-missing-policy-review-comparison",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightEvidenceValuePolicyService
                .ENDPOINT),
        checkpoint(
            "comparison-acceptance-precheck-execution-lock",
            "v1319",
            "execution lock acceptance checkpoint",
            "Are execution locks still closed before any compared package acceptance?",
            "reject-missing-execution-lock-comparison",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightExecutionCloseoutService
                .ENDPOINT),
        checkpoint(
            "comparison-acceptance-precheck-approval-separation",
            "v1320",
            "approval grant review separation checkpoint",
            "Is approval-grant review still separated from comparison precheck?",
            "reject-missing-approval-separation",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightExecutionCloseoutService
                .ENDPOINT),
        checkpoint(
            "comparison-acceptance-precheck-archive-closeout",
            "v1321",
            "archive closeout acceptance checkpoint",
            "Is archive closeout represented without writing files or accepting the package?",
            "reject-missing-archive-closeout-comparison",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightExecutionCloseoutService
                .ENDPOINT));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
              .MissingEvidenceGuard>
      allGuards() {
    return List.of(
        guard(
            "comparison-acceptance-guard-source-readiness",
            "source",
            "Reject when Java v1004 comparison preflight evidence is absent",
            "reject-missing-comparison-preflight-readiness"),
        guard(
            "comparison-acceptance-guard-identity-request",
            "identity",
            "Reject when identity or request comparison evidence is missing",
            "reject-missing-identity-request-comparison"),
        guard(
            "comparison-acceptance-guard-digest-binding",
            "digest",
            "Reject when digest binding or digest recheck comparison evidence is missing",
            "reject-missing-digest-binding-comparison"),
        guard(
            "comparison-acceptance-guard-detached-signature",
            "signature",
            "Reject when detached signature envelope comparison evidence is missing or parsed",
            "reject-missing-detached-signature-comparison"),
        guard(
            "comparison-acceptance-guard-source-evidence",
            "source-evidence",
            "Reject when source evidence handle comparison evidence is missing",
            "reject-missing-source-evidence-comparison"),
        guard(
            "comparison-acceptance-guard-operator-value",
            "value",
            "Reject when operator value handle comparison evidence is missing",
            "reject-missing-operator-value-comparison"),
        guard(
            "comparison-acceptance-guard-policy-review",
            "policy",
            "Reject when policy or review-state comparison evidence is missing",
            "reject-missing-policy-review-comparison"),
        guard(
            "comparison-acceptance-guard-execution-lock",
            "execution",
            "Reject when execution lock, no-runtime, or no-write evidence is missing",
            "reject-missing-execution-lock-comparison"),
        guard(
            "comparison-acceptance-guard-approval-separation",
            "approval",
            "Reject when comparison evidence implies approval grant review",
            "reject-missing-approval-separation"),
        guard(
            "comparison-acceptance-guard-archive-closeout",
            "archive",
            "Reject when archive closeout comparison evidence is missing",
            "reject-missing-archive-closeout-comparison"));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
          .AcceptanceCheckpoint
      checkpoint(
          String code,
          String sourceVersion,
          String checkpoint,
          String acceptanceQuestion,
          String missingEvidenceGuard,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSupport
        .checkpoint(
            code,
            sourceVersion,
            checkpoint,
            acceptanceQuestion,
            missingEvidenceGuard,
            sourceEndpoint);
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
          .MissingEvidenceGuard
      guard(String code, String category, String guard, String rejectionCode) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSupport
        .guard(code, category, guard, rejectionCode);
  }
}
