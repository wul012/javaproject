package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutRouteEvidenceCatalog {

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutRouteEvidenceCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
              .RouteEvidence>
      allRoutes() {
    return List.of(
        route(
            "submission-preflight-route-catalog",
            OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_CATALOG,
            "source submission slot and comparison-control catalog"),
        route(
            "submission-preflight-route-identity",
            OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_IDENTITY,
            "identity and correlation submission slots"),
        route(
            "submission-preflight-route-digest-signature",
            OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_DIGEST_SIGNATURE,
            "digest and detached-signature comparison slots"),
        route(
            "submission-preflight-route-evidence-value",
            OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_EVIDENCE_VALUE,
            "source evidence and operator value handle slots"),
        route(
            "submission-preflight-route-policy-execution-closeout",
            OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_POLICY_EXECUTION_CLOSEOUT,
            "policy, review-state, and execution lock slots"),
        route(
            "submission-preflight-closeout-route-catalog",
            OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_CLOSEOUT_CATALOG,
            "closeout evidence catalog"),
        route(
            "submission-preflight-closeout-route-handoff-ledger",
            OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_CLOSEOUT_HANDOFF_LEDGER,
            "manual handoff ledger"),
        route(
            "submission-preflight-closeout-route-route-evidence",
            OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_CLOSEOUT_ROUTE_EVIDENCE,
            "read-only route evidence"),
        route(
            "submission-preflight-closeout-route-archive-manifest",
            OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_CLOSEOUT_ARCHIVE_MANIFEST,
            "archive manifest summary"),
        route(
            "submission-preflight-closeout-route-runtime-boundary",
            OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_CLOSEOUT_RUNTIME_BOUNDARY,
            "runtime and sibling boundary evidence"),
        route(
            "submission-preflight-closeout-route-integrity-summary",
            OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_CLOSEOUT_INTEGRITY_SUMMARY,
            "final integrity summary"));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
          .RouteEvidence
      route(String code, String route, String purpose) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutSupport
        .route(code, route, purpose);
  }
}
