package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintResponse;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse;
import java.util.List;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionSourceCatalog {

  private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionSourceCatalog() {}

  static List<
          OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
              .TextPackageSectionSource>
      sources(
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
              intake,
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
              reviewPreflight,
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
              submissionPreflight,
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
              comparisonPreflight,
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
              acceptancePrecheck,
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
              comparedPackageEvidenceIntake,
          OpsShardReadinessComparedEvidenceEvaluationPreflightResponse evaluationPreflight,
          OpsShardReadinessComparedEvidenceCandidateBlueprintResponse candidateBlueprint,
          OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse
              candidateIntakePreflight) {
    return List.of(
        source(
            1,
            "signed-approval-artifact-draft-text-package-intake",
            intake.version(),
            "Node v1236",
            "submission",
            intake.endpoint(),
            intake.profile(),
            intake.status()),
        source(
            2,
            "signed-approval-artifact-draft-text-package-review-preflight",
            reviewPreflight.version(),
            "Node v1261",
            "submission",
            reviewPreflight.endpoint(),
            reviewPreflight.profile(),
            reviewPreflight.status()),
        source(
            3,
            "signed-approval-artifact-draft-text-package-submission-preflight",
            submissionPreflight.version(),
            "Node v1286",
            "submission",
            submissionPreflight.endpoint(),
            submissionPreflight.profile(),
            submissionPreflight.status()),
        source(
            4,
            "signed-approval-artifact-draft-text-package-comparison-preflight",
            comparisonPreflight.version(),
            "Node v1311",
            "submission",
            comparisonPreflight.endpoint(),
            comparisonPreflight.profile(),
            comparisonPreflight.status()),
        source(
            5,
            "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck",
            acceptancePrecheck.version(),
            "Node v1321",
            "submission",
            acceptancePrecheck.endpoint(),
            acceptancePrecheck.profile(),
            acceptancePrecheck.status()),
        source(
            6,
            "signed-approval-artifact-draft-text-package-compared-package-evidence-intake",
            comparedPackageEvidenceIntake.version(),
            "Node v1331",
            "compared-evidence",
            comparedPackageEvidenceIntake.endpoint(),
            comparedPackageEvidenceIntake.profile(),
            comparedPackageEvidenceIntake.status()),
        source(
            7,
            "signed-approval-artifact-draft-text-package-compared-evidence-evaluation-preflight",
            evaluationPreflight.version(),
            "Node v1351",
            "compared-evidence",
            evaluationPreflight.endpoint(),
            evaluationPreflight.profile(),
            evaluationPreflight.status()),
        source(
            8,
            "signed-approval-artifact-draft-text-package-compared-evidence-candidate",
            candidateBlueprint.version(),
            "Node v1361",
            "compared-evidence",
            candidateBlueprint.endpoint(),
            candidateBlueprint.profile(),
            candidateBlueprint.status()),
        source(
            9,
            "signed-approval-artifact-draft-text-package-compared-evidence-candidate-intake",
            candidateIntakePreflight.version(),
            "Node v1371",
            "compared-evidence",
            candidateIntakePreflight.endpoint(),
            candidateIntakePreflight.profile(),
            candidateIntakePreflight.status()));
  }

  private static OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
          .TextPackageSectionSource
      source(
          int order,
          String code,
          String javaVersion,
          String nodeVersionMarker,
          String rendererGroup,
          String endpoint,
          String profile,
          String sourceStatus) {
    return new OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
        .TextPackageSectionSource(
        order,
        code,
        javaVersion,
        nodeVersionMarker,
        rendererGroup,
        endpoint,
        profile,
        sourceStatus,
        "passed");
  }
}
