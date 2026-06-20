package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffLedgerService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightIdentityService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightFoundationLaneCatalog {

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightFoundationLaneCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
              .ComparisonLane>
      foundationLanes() {
    return List.of(
        lane(
            "comparison-lane-identity-subject",
            "v1287",
            "compare submitted package operator identity against identity submission slot",
            "Does the submitted package identify the same operator context without opening acceptance?",
            "reject-package-identity-subject-mismatch",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightIdentityService
                .ENDPOINT),
        lane(
            "comparison-lane-identity-envelope",
            "v1288",
            "compare identity envelope lineage against closeout handoff evidence",
            "Is the submitted identity envelope traceable to the read-only submission closeout?",
            "reject-package-identity-envelope-untraceable",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffLedgerService
                .ENDPOINT),
        lane(
            "comparison-lane-request-id",
            "v1289",
            "compare request id field against request submission slot",
            "Does the submitted package preserve the request id as offline comparison material?",
            "reject-package-request-id-missing",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightIdentityService
                .ENDPOINT),
        lane(
            "comparison-lane-correlation-window",
            "v1290",
            "compare correlation window against expected submission slot",
            "Is the correlation window comparable without binding to runtime request state?",
            "reject-package-correlation-window-uncomparable",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightIdentityService
                .ENDPOINT));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
          .ComparisonLane
      lane(
          String code,
          String versionRange,
          String comparisonLane,
          String comparisonQuestion,
          String acceptanceControl,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport
        .lane(
            code,
            versionRange,
            comparisonLane,
            comparisonQuestion,
            acceptanceControl,
            sourceEndpoint);
  }
}
